package de.gravitex.banking.client.tester.executor;

import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;

public class ManualWebTesterExecutor {

	public void runForInstance(Class<? extends ManualWebTester> executorClass) {
		makeExecutorInstance(executorClass).connect().runTests().proclaimSuccess();
	}

	private ManualWebTester makeExecutorInstance(Class<? extends ManualWebTester> executorClass) {
		try {
			return executorClass.getConstructor(new Class[] {}).newInstance(new Object[] {});
		} catch (Exception e) {
			throw new ManualWebTesterException("unable to construct executor instance for class {"
					+ executorClass.getClass().getSimpleName() + "}!!!", e);
		}
	}
}