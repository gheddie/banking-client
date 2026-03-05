package de.gravitex.banking.client.gui.test;

import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Thu Mar 05 23:17:30 CET 2026
 */



/**
 * @author sschu
 */
public class FPS4Rows extends FPS {
	
	private static final long serialVersionUID = 7035655556060658686L;
	
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JTextField textField2;
	private JLabel label3;
	private JTextField textField3;
	private JLabel label4;
	private JTextField textField4;
	private JButton button1;
	
	public FPS4Rows() {
		initComponents();
	}

	private void initComponents() {

		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		label3 = new JLabel();
		textField3 = new JTextField();
		label4 = new JLabel();
		textField4 = new JTextField();
		button1 = new JButton();

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		layout.columnWidths = columnWidths();
		layout.columnWeights = columnWeights();
		
		layout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

		//---- label1 ----
		label1.setText("Wurst");
		add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- label2 ----
		label2.setText("Ei");
		add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(textField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- label3 ----
		label3.setText("Kaesebrot");
		add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(textField3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- label4 ----
		label4.setText("Pelle");
		add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(textField4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- button1 ----
		button1.setText("text");
		add(button1, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
	}
}