package de.gravitex.banking.client.tester.matcher.exception.base;

public abstract class ExceptionMatcher {

	public abstract boolean matchesException(String errorMessage);

	public abstract String getExceptionPart();
}