package de.gravitex.banking.client.gui.test;

import java.awt.*;
import java.util.List;

import javax.swing.*;
/*
 * Created by JFormDesigner on Thu Mar 05 23:17:30 CET 2026
 */



/**
 * @author sschu
 */
public class FPS4Rows extends FPS {
	
	private static final long serialVersionUID = 7035655556060658686L;
	
	public FPS4Rows(List<Moo> aMoos) {
		super(aMoos);
		initComponents();
	}

	private void initComponents() {

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		layout.columnWidths = columnWidths();
		layout.columnWeights = columnWeights();
		layout.rowHeights = makeRowHeights(getLayoutRowCount());
		layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0E-4};

		//---- label1 ----
		add(new JLabel("Ass"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(new JTextField(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- label2 ----
		add(new JLabel("Wumpsi"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(new JTextField(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- label3 ----
		add(new JLabel("Kannoe"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(new JTextField(), new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- label4 ----
		add(new JLabel("DHL"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		add(new JTextField(), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//---- button1 ----
		add(new JButton("OK"), new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
	}
}