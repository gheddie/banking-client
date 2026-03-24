package de.gravitex.banking.client.tester.validation;

import java.math.BigDecimal;

import de.gravitex.banking.client.tester.exception.ManualWebTesterValidationException;
import de.gravitex.banking.client.util.ObjectUtil;

public class ValueValidator {
	
	public void compare(int aExpectedValue, int actualValue) {
		compare(aExpectedValue, actualValue, null);
	}

	public void compare(int aExpectedValue, int actualValue, String aHint) {
		if (!(aExpectedValue == actualValue)) {
			throw new ManualWebTesterValidationException("validation failed for type [int] {expected value=" + aExpectedValue
					+ ", actual value= " + actualValue + "}!!!", aHint);
		}
	}
	
	public void compare(BigDecimal aExpectedValue, BigDecimal actualValue) {
		if (!(aExpectedValue.compareTo(actualValue) == 0)) {
			throw new ManualWebTesterValidationException("validation failed for type [big decimal] {expected value=" + aExpectedValue
					+ ", actual value= " + actualValue + "}!!!", null);
		}	
	}

	public void assertNotNull(Object aValue) {
		if (aValue == null) {
			throw new ManualWebTesterValidationException("given object must NOT be NULL!!!", null);
		}
	}
	
	public void assertNull(Object aValue) {
		if (aValue != null) {
			throw new ManualWebTesterValidationException("given object MUST be NULL {not:" + aValue + "}!!!", null);
		}
	}

	public void compare(Object aExpectedValue, Object actualValue) {
		boolean aEqual = ObjectUtil.areValuesEqual(aExpectedValue, actualValue);
		if (!aEqual) {
			throw new ManualWebTesterValidationException(
					"given objects are NOT equal [" + aExpectedValue + " --> " + actualValue + "]!!!", null);
		}
	}
}