package de.gravitex.banking.client.tester;

import de.gravitex.banking.client.tester.executor.ManualWebTesterExecutor;
import de.gravitex.banking.client.tester.instance.CreateSomeEntitiesManualWebTester;

public class WebTesterMain {

	public static void main(String[] args) {
		new ManualWebTesterExecutor().runForInstance(CreateSomeEntitiesManualWebTester.class);
	}
}