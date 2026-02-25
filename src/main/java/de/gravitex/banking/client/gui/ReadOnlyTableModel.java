package de.gravitex.banking.client.gui;

import javax.swing.table.DefaultTableModel;

public class ReadOnlyTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 6411635640741508367L;
	
	public ReadOnlyTableModel(Object[][] aData, Object[] aColumnNames) {
		super(aData, aColumnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}