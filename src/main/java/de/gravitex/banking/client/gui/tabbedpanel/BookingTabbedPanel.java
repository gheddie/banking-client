package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;

public class BookingTabbedPanel extends TabbedPanel {

	private static final long serialVersionUID = 7075418130291162532L;
	
	@Override
	protected void init() {
		int werner = 5;
	}

	@Override
	protected LayoutManager getPanelLayout() {
		return new BorderLayout();
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		
	}
	
	@Override
	protected void putListeners() {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}
}