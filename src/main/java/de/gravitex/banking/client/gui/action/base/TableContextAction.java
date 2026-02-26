package de.gravitex.banking.client.gui.action.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.gravitex.banking.client.gui.action.util.ActionProvider;

public abstract class TableContextAction<T> implements ActionListener {

	private String actionText;
	
	private ActionProvider actionProvider;

	public TableContextAction(String actionText, ActionProvider tActionProvider) {
		super();
		this.actionText = actionText;
		this.actionProvider = tActionProvider;
	}
	
	public String getActionText() {
		return actionText;
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		executeAction((T) actionProvider.getContextObject());
	}

	protected abstract void executeAction(T contextObject);
	
	public ActionProvider getActionProvider() {
		return actionProvider;
	}
}