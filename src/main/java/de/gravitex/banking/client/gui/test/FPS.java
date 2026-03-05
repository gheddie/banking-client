package de.gravitex.banking.client.gui.test;

import javax.swing.JPanel;

public abstract class FPS extends JPanel {

	private static final long serialVersionUID = -8300870676510814846L;
	
	protected int[] columnWidths() {
		return new int[] {0, 0, 0};
	}
	
	protected double[] columnWeights() {
		return new double[] {0.0, 1.0, 1.0E-4};
	}
}