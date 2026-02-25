package de.gravitex.banking.client.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class ValueFilterItem extends JPanel {

	private static final long serialVersionUID = 5526607050672090512L;
	
	public ValueFilterItem() {
		super();
		setLayout(new BorderLayout());
		add(new JTextField(), BorderLayout.CENTER);
	}
}