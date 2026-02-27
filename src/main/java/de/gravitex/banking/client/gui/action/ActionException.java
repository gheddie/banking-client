package de.gravitex.banking.client.gui.action;

public class ActionException extends Exception {
	
	private static final long serialVersionUID = 512745450067271366L;

	public ActionException(String aMessage, Throwable aThrowable) {
		super(aMessage, aThrowable);
	}
}