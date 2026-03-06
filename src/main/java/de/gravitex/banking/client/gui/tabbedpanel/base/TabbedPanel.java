package de.gravitex.banking.client.gui.tabbedpanel.base;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class TabbedPanel extends JPanel {

	private static final long serialVersionUID = 2852774593191083214L;
	
	public TabbedPanel() {
		super();
		LayoutManager panelLayout = getPanelLayout();
		if (panelLayout == null) {
			throw new IllegalArgumentException("tabbed panel of class {" + getClass().getSimpleName()
					+ "} must provide a panel layout manager!!!");
		} 
		setLayout(panelLayout);
		init();
		putListeners();
	}

	protected abstract void putListeners();

	protected abstract LayoutManager getPanelLayout();

	protected abstract void init();

	public abstract void onPanelActivated(Object aContextEntity);
	
	protected void extendTitle(String aTitle) {

	}
	
	public abstract void reload();
}