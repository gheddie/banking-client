package de.gravitex.banking.client.gui.action.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.gravitex.banking.client.gui.action.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.registry.ApplicationRegistry;

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
		try {
			checkContextObject(actionProvider.getContextObject());
			executeAction((T) actionProvider.getContextObject());	
		} catch (ActionException e2) {
			ApplicationRegistry.getInstance().getInteractionHandler().showError(e2.getMessage(), null);
		}
	}

	protected abstract void checkContextObject(Object aContextObject) throws ActionException;

	protected abstract void executeAction(T contextObject);
	
	public ActionProvider getActionProvider() {
		return actionProvider;
	}
}