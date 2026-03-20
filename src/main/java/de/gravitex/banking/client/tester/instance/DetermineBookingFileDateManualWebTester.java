package de.gravitex.banking.client.tester.instance;

import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.util.WebTestWatcher;

public class DetermineBookingFileDateManualWebTester extends BankingLogicManualWebTester {

	public DetermineBookingFileDateManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
		super(aWebTestWatcher, isActive);
	}

	@Override
	public ManualWebTester runTests() {
		
		makeGiroPrivateAccount();
		
		return this;
	}
}