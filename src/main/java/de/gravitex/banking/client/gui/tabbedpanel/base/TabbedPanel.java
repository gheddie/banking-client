package de.gravitex.banking.client.gui.tabbedpanel.base;

import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class TabbedPanel extends JPanel {

	private static final long serialVersionUID = 2852774593191083214L;
	
	public TabbedPanel() {
		super();
		setLayout(getPanelLayout());
		init();
		putListeners();
	}

	protected abstract void putListeners();

	protected abstract LayoutManager getPanelLayout();

	protected abstract void init();

	public abstract void onPanelActivated(Object aContextEntity);
	
	protected void extendTitle(String aTitle) {
		Container parent = getParent();
		int werner = 5;
	}
	
	public abstract void reload();
}