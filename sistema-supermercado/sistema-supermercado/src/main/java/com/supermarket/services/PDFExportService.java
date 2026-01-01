package com.supermarket.services;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.supermarket.model.Cliente;
import com.supermarket.model.Factura;
import com.supermarket.model.FacturaDetalle;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDFExportService {

    private static final String PDF_FOLDER = "facturas/";

    static {
        new File(PDF_FOLDER).mkdirs();
    }

    public static String generarFacturaPDF(Factura factura, List<FacturaDetalle> detalles) {
        try {
            String nombreArchivo = "Factura_" + factura.getId() + "_" + System.currentTimeMillis() + ".pdf";
            String rutaCompleta = PDF_FOLDER + nombreArchivo;

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(rutaCompleta));
            Document doc = new Document(pdfDoc, PageSize.LETTER);
            doc.setMargins(50, 50, 50, 50);

            PdfFont bold = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            PdfFont normal = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

            // Título
            doc.add(new Paragraph("SUPERMERCADO")
                    .setFont(bold)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            doc.add(new Paragraph("FACTURA N° " + factura.getId())
                    .setFont(bold)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha = sdf.format(java.sql.Timestamp.valueOf(factura.getFecha()));

            Table headerTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
            headerTable.setWidth(UnitValue.createPercentValue(100));
            headerTable.addCell(new Cell().add(new Paragraph("Fecha: " + fecha)).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph("Usuario: " + factura.getUsuario().getUsername()))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT));
            doc.add(headerTable);

            doc.add(new Paragraph(" "));

            // Cliente
            if (factura.getCliente() != null) {
                Cliente c = factura.getCliente();
                doc.add(new Paragraph("CLIENTE").setFont(bold).setFontSize(12));
                Table clienteTable = new Table(2);
                clienteTable.setWidth(UnitValue.createPercentValue(100));
                agregarFila(clienteTable, "Nombre:", c.getNombre() + " " + c.getApellido());
                agregarFila(clienteTable, "Cédula:", c.getCedula());
                agregarFila(clienteTable, "Teléfono:", c.getTelefono() != null ? c.getTelefono() : "N/A");
                agregarFila(clienteTable, "Email:", c.getEmail() != null ? c.getEmail() : "N/A");
                agregarFila(clienteTable, "Membresía:", c.isMembresia() ? "Sí (Descuento 5%)" : "No");
                doc.add(clienteTable);
                doc.add(new Paragraph(" "));
            }

            // Detalles
            doc.add(new Paragraph("DETALLES DE LA COMPRA").setFont(bold).setFontSize(12));

            float[] columnWidths = {15, 40, 15, 15, 15};
            Table tablaDetalles = new Table(UnitValue.createPercentArray(columnWidths));
            tablaDetalles.setWidth(UnitValue.createPercentValue(100));

            String[] headers = {"SKU", "Producto", "Cant.", "Precio", "Subtotal"};
            for (String h : headers) {
                tablaDetalles.addHeaderCell(new Cell().add(new Paragraph(h))
                        .setBackgroundColor(ColorConstants.DARK_GRAY)
                        .setFontColor(ColorConstants.WHITE)
                        .setFont(bold)
                        .setTextAlignment(TextAlignment.CENTER));
            }

            for (FacturaDetalle d : detalles) {
                tablaDetalles.addCell(d.getProducto().getSku());
                tablaDetalles.addCell(d.getProducto().getNombre());
                tablaDetalles.addCell(String.valueOf(d.getCantidad()));
                tablaDetalles.addCell(String.format("$%.2f", d.getPrecioUnitario()));
                tablaDetalles.addCell(String.format("$%.2f", d.getSubtotal()));
            }

            doc.add(tablaDetalles);
            doc.add(new Paragraph(" "));

            // Totales
            Table totales = new Table(2);
            totales.setWidth(UnitValue.createPercentValue(50));
            totales.setTextAlignment(TextAlignment.RIGHT);

            double subtotal = factura.getTotal() * 0.82;  // aproximado
            double iva = factura.getTotal() * 0.18;

            totales.addCell(new Cell().add(new Paragraph("SUBTOTAL:").setFont(bold)).setTextAlignment(TextAlignment.RIGHT));
            totales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", subtotal))));

            totales.addCell(new Cell().add(new Paragraph("IVA 18%:").setFont(bold)).setTextAlignment(TextAlignment.RIGHT));
            totales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", iva))));

            totales.addCell(new Cell().add(new Paragraph("TOTAL:").setFont(bold).setFontSize(14))
                    .setBackgroundColor(ColorConstants.DARK_GRAY)
                    .setFontColor(ColorConstants.WHITE));
            totales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", factura.getTotal()))
                    .setFont(bold).setFontSize(14))
                    .setBackgroundColor(ColorConstants.DARK_GRAY)
                    .setFontColor(ColorConstants.WHITE));

            doc.add(totales);

            doc.add(new Paragraph("\nMétodo de Pago: " + factura.getMetodoPago())
                    .setTextAlignment(TextAlignment.CENTER));
            doc.add(new Paragraph("\nGracias por su compra")
                    .setFont(normal).setItalic()
                    .setTextAlignment(TextAlignment.CENTER));

            doc.close();
            return rutaCompleta;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
            return null;
        }
    }

    private static void agregarFila(Table table, String etiqueta, String valor) {
        table.addCell(new Cell().add(new Paragraph(etiqueta).setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(valor)));
    }

    public static void abrirPDF(String ruta) {
        // ... mismo método que tenías
        try {
            File file = new File(ruta);
            if (file.exists()) {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ruta);
                } else if (os.contains("mac")) {
                    Runtime.getRuntime().exec("open " + ruta);
                } else {
                    Runtime.getRuntime().exec("xdg-open " + ruta);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir el PDF");
        }
    }
}