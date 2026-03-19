package de.gravitex.banking.client.tester.validation;

import de.gravitex.banking.client.tester.exception.ManualWebTesterValidationException;

public class ValueValidator {
	
	public void validate(int aExpectedValue, int actualValue) {
		validate(aExpectedValue, actualValue, null);
	}

	public void validate(int aExpectedValue, int actualValue, String aHint) {
		if (!(aExpectedValue == actualValue)) {
			throw new ManualWebTesterValidationException("numeric validation failed {expected value=" + aExpectedValue
					+ ", actual value= " + actualValue + "}!!!", aHint);
		}
	}

	public void notNull(Object aValue) {
		if (aValue == null) {
			throw new ManualWebTesterValidationException("given object must NOT be NULL!!!", null);
		}
	}
}