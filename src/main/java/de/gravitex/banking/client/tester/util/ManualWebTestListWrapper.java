package de.gravitex.banking.client.tester.util;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.tester.executor.ManualWebTesterExecutor;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;

public class ManualWebTestListWrapper {

	private List<Class<? extends ManualWebTester>> testClasses = new ArrayList<>();

	public ManualWebTestListWrapper withTestClass(Class<? extends ManualWebTester> aTestClass) {
		if (testClasses.contains(aTestClass)) {
			throw new IllegalArgumentException(
					"test class {" + aTestClass.getCanonicalName() + "} was already defined for execution!!!");
		}
		testClasses.add(aTestClass);
		return this;
	}

	public void runTests() {
		for (Class<? extends ManualWebTester> aTestClass : testClasses) {
			new ManualWebTesterExecutor().runForInstance(aTestClass);
		}
	}
}