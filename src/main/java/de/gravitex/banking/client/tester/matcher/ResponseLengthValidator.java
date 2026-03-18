package de.gravitex.banking.client.tester.matcher;

import de.gravitex.banking.client.tester.exception.ManualWebTesterException;

public class ResponseLengthValidator {
	
	private int expectedResponseLength;

	private ResponseLengthValidator(int aExpectedResponseLength) {
		super();
		this.expectedResponseLength = aExpectedResponseLength;
	}

	public static ResponseLengthValidator forExpectedResponseSize(int aExpectedResponseLength) {
		return new ResponseLengthValidator(aExpectedResponseLength);
	}

	public void acceptResponseLength(int aResponseLength) {
		if (!(aResponseLength == expectedResponseLength)) {
			throw new ManualWebTesterException("expected response length {" + expectedResponseLength
					+ "} differs from actual {" + aResponseLength + "}!!!");
		}
	}
}