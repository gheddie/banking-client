package de.gravitex.banking.client.tester.executor;

import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.util.WebTestWatcher;

public class ManualWebTesterExecutor {

	public void runForInstance(Class<? extends ManualWebTester> executorClass, WebTestWatcher aWebTestWatcher) {
		
		ManualWebTester executorInstance = null;
		try {
			executorInstance = makeExecutorInstance(executorClass, aWebTestWatcher);
			executorInstance.connect().removeEntities().enableTrace().runTests().proclaimSuccess();	
		} catch (ManualWebTesterException e) {
			aWebTestWatcher.handleTestException(e, executorInstance);
		}		
	}

	private ManualWebTester makeExecutorInstance(Class<? extends ManualWebTester> executorClass, WebTestWatcher aWebTestWatcher) {
		try {
			return executorClass.getConstructor(new Class[] { WebTestWatcher.class, boolean.class })
					.newInstance(new Object[] { aWebTestWatcher, true });
		} catch (Exception e) {
			throw new ManualWebTesterException("unable to construct executor instance for class {"
					+ executorClass.getClass().getSimpleName() + "}!!!", e);
		}
	}
}