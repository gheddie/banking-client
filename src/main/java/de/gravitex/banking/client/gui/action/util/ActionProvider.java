package de.gravitex.banking.client.gui.action.util;

import java.awt.Window;

public interface ActionProvider {

	Object getContextObject();

	Window getWindow();
	
	Object getInvoker();
}