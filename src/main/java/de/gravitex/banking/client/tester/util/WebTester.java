package de.gravitex.banking.client.tester.util;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.tester.matcher.ResponseLengthValidator;
import de.gravitex.banking.client.tester.matcher.exception.base.ExceptionMatcher;

public interface WebTester {

	IBankingAccessor getBankingAccessor();
	
	void expectSuccess(HttpResult aHttpResult, String aVariableName, ResponseLengthValidator aResponseLengthValidator);
	
	void expectFailure(HttpResult aHttpResult);
	
	void expectFailure(HttpResult aHttpResult, ExceptionMatcher aExceptionMatcher);
}