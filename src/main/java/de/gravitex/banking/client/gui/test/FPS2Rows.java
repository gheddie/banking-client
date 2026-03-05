package de.gravitex.banking.client.gui.test;

import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Thu Mar 05 23:16:34 CET 2026
 */



/**
 * @author sschu
 */
public class FPS2Rows extends FPS {
	
	private static final long serialVersionUID = 7035655556060658686L;
	
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JTextField textField2;
	private JButton button1;
	
	public FPS2Rows() {
		initComponents();
	}

	private void initComponents() {

		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		button1 = new JButton();

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		layout.columnWidths = columnWidths();
		layout.columnWeights = columnWeights();
		
		layout.rowHeights = new int[] {0, 0, 0, 0, 0};
		layout.rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};

		//---- label1 ----
		label1.setText("Ballon");
		add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- label2 ----
		label2.setText("Klauspeter");
		add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(textField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- button1 ----
		button1.setText("text");
		add(button1, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
	}
}
