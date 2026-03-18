package de.gravitex.banking.client.tester.matcher.exception;

import de.gravitex.banking.client.tester.matcher.exception.base.ExceptionMatcher;

public class ContainedStringExceptionMatcher extends ExceptionMatcher {

	private String containedString;

	public ContainedStringExceptionMatcher(String aContainedString) {
		super();
		this.containedString = aContainedString;
	}

	@Override
	public boolean matchesException(String errorMessage) {
		return (errorMessage.contains(containedString));
	}

	@Override
	public String getExceptionPart() {
		return containedString;
	}
}