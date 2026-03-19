package de.gravitex.banking.client.tester.util;

import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;

public interface WebTestWatcher {

	void acceptSuccess(HttpResult aHttpResult, boolean aShouldSuceed, boolean aTraceEnabled, ManualWebTester aManualWebTester);

	void onTestSucceed(ManualWebTester aManualWebTester);

	void handleTestException(ManualWebTesterException aManualWebTesterException, ManualWebTester aManualWebTester);
}