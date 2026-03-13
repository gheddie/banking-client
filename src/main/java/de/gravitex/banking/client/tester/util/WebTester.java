package de.gravitex.banking.client.tester.util;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.tester.matcher.base.ExceptionMatcher;

public interface WebTester {

	IBankingAccessor getBankingAccessor();
	
	void expectSuccess(HttpResult aHttpResult);
	
	void expectSuccess(HttpResult aHttpResult, String aVariableName);
	
	void expectFailure(HttpResult aHttpResult);
	
	void expectFailure(HttpResult aHttpResult, ExceptionMatcher aExceptionMatcher);
}