package de.gravitex.banking.client.gui.action.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.gravitex.banking.client.gui.action.exception.ActionException;
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

	protected void checkContextObject(Object aContextObject) throws ActionException {
		// nothing to do in base implementation
	}

	protected abstract void executeAction(T contextObject);
	
	public ActionProvider getActionProvider() {
		return actionProvider;
	}
	
	protected void ensureContextObject(Object aContextObject) throws ActionException {
		if (aContextObject == null) {
			throw new ActionException(
					"action of class [" + getClass().getSimpleName() + "] must have a context object set!!!", null);
		}
	}
}