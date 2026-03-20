package de.gravitex.banking.client.tester.validation;

import java.math.BigDecimal;

import de.gravitex.banking.client.tester.exception.ManualWebTesterValidationException;

public class ValueValidator {
	
	public void validate(int aExpectedValue, int actualValue) {
		validate(aExpectedValue, actualValue, null);
	}

	public void validate(int aExpectedValue, int actualValue, String aHint) {
		if (!(aExpectedValue == actualValue)) {
			throw new ManualWebTesterValidationException("validation failed for type [int] {expected value=" + aExpectedValue
					+ ", actual value= " + actualValue + "}!!!", aHint);
		}
	}
	
	public void validate(BigDecimal aExpectedValue, BigDecimal actualValue) {
		if (!(aExpectedValue.compareTo(actualValue) == 0)) {
			throw new ManualWebTesterValidationException("validation failed for type [big decimal] {expected value=" + aExpectedValue
					+ ", actual value= " + actualValue + "}!!!", null);
		}	
	}

	public void notNull(Object aValue) {
		if (aValue == null) {
			throw new ManualWebTesterValidationException("given object must NOT be NULL!!!", null);
		}
	}
}