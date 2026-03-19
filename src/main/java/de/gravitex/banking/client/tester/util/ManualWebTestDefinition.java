package de.gravitex.banking.client.tester.util;

import de.gravitex.banking.client.tester.instance.base.ManualWebTester;

public class ManualWebTestDefinition {

	private Class<? extends ManualWebTester> testClass;

	private boolean active;

	public ManualWebTestDefinition(Class<? extends ManualWebTester> aTestClass, boolean isActive) {

		super();

		this.testClass = aTestClass;
		this.active = isActive;
	}

	public Class<? extends ManualWebTester> getTestClass() {
		return testClass;
	}

	public boolean isActive() {
		return active;
	}

	public static ManualWebTestDefinition forTestClass(Class<? extends ManualWebTester> aTestClass, boolean isActive) {
		return new ManualWebTestDefinition(aTestClass, isActive);
	}
}