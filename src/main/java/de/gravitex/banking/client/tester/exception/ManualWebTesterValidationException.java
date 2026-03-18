package de.gravitex.banking.client.tester.exception;

import de.gravitex.banking_core.util.StringHelper;

public class ManualWebTesterValidationException extends ManualWebTesterException {

	private static final long serialVersionUID = 8942919075655516120L;

	public ManualWebTesterValidationException(String message, String aHint) {
		super(buildMessage(message, aHint));
	}

	private static String buildMessage(String message, String aHint) {
		if (StringHelper.isBlank(aHint)) {
			return message;
		}
		return message + " (Hint:" + aHint + ")";
	}
}