package de.gravitex.banking.client.tester.validation;

import de.gravitex.banking.client.tester.exception.ManualWebTesterValidationException;

public class ValueValidator {

	public void validate(int aExpectedValue, int actualValue) {
		if (!(aExpectedValue == actualValue)) {
			throw new ManualWebTesterValidationException("numeric validation failed {expected value=" + aExpectedValue
					+ ", actual value= " + actualValue + "}!!!");
		}
	}
}