package com.supermarket.view.caja;

import javax.swing.*;
import java.awt.*;

public class CajaView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CajaView() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Vista de Caja - En Desarrollo");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        add(label, BorderLayout.CENTER);
    }
}