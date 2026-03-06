package de.gravitex.banking.client.gui.test;

import java.util.List;

import javax.swing.JPanel;

public abstract class FPS extends JPanel {

	private static final long serialVersionUID = -8300870676510814846L;
	
	private List<FpsMoo> moos;
	
	public FPS(List<FpsMoo> aMoos) {
		super();
		this.moos = aMoos;
	}
	
	protected int[] columnWidths() {
		return new int[] {0, 0, 0};
	}
	
	protected double[] columnWeights() {
		return new double[] {0.0, 1.0, 1.0E-4};
	}
	
	public List<FpsMoo> getMoos() {
		return moos;
	}
	
	protected int[] makeRowHeights(int count) {
		int[] tmp = new int[count];
		for (int i=0;i<count;i++) {
			tmp[i] = 0;
		}
		return tmp;
	}
	
	protected int getItemCount() {
		return moos.size();
	}
	
	protected int getLayoutRowCount() {
		return getItemCount() + 3;
	}
}