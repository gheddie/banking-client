package de.gravitex.banking.client.gui.dialog;

import java.awt.Window;

import javax.swing.JDialog;

public class BankingDialog extends JDialog {
	
	private static final long serialVersionUID = 4617443160106616072L;
	
	private static final int OFFSET = 25;

	public BankingDialog(Window owner) {
		if (owner == null) {
			throw new IllegalArgumentException("owner must not be null!!!");
		}
		setLocation(owner.getX() + OFFSET, owner.getY() + OFFSET);
	}
}