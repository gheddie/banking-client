package de.gravitex.banking.client.tester.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.gui.dialog.webtest.ManualWebTestListWrapperDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.executor.ManualWebTesterExecutor;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;

public class ManualWebTestListWrapper implements WebTestWatcher {
	
	private Logger logger = LoggerFactory.getLogger(ManualWebTestListWrapper.class);

	private List<ManualWebTestDefinition> testDefinitions = new ArrayList<>();
	
	private ManualWebTestListWrapperDialog wrapperDialog;

	private Map<Class<? extends ManualWebTester>, List<HttpResultWrapper>> resultMap = new HashMap<>();

	private boolean execute = true;

	private List<Class<? extends ManualWebTester>> testClasses;

	public ManualWebTestListWrapper withTestDefinition(ManualWebTestDefinition aTestDefinition) {
		if (testAlreadyDefined(aTestDefinition)) {
			throw new IllegalArgumentException(
					"test class {" + aTestDefinition.getTestClass().getCanonicalName() + "} was already defined for execution!!!");
		}
		testDefinitions.add(aTestDefinition);
		return this;
	}

	private boolean testAlreadyDefined(ManualWebTestDefinition aTestDefinition) {
		for (ManualWebTestDefinition testDefinition : testDefinitions) {
			if (testDefinition.getTestClass().equals(aTestDefinition.getTestClass())) {
				return true;		
			}
		}
		return false;
	}

	public void runTests() {
		wrapperDialog = new ManualWebTestListWrapperDialog(testDefinitions, null);
		wrapperDialog.setVisible(true);
		testClasses = makeTestClassList();
		for (ManualWebTestDefinition aTestDefinition : testDefinitions) {
			if (execute && aTestDefinition.isActive()) {
				new ManualWebTesterExecutor().runForInstance(aTestDefinition.getTestClass(), this);	
			}			
		}
	}

	@Override
	public void acceptSuccess(HttpResult aHttpResult, boolean aShouldSuceed, boolean aTraceEnabled,
			ManualWebTester aManualWebTester) {
		
		if (!aTraceEnabled) {
			return;
		}
		
		if (resultMap.get(aManualWebTester.getClass()) == null) {
			resultMap.put(aManualWebTester.getClass(), new ArrayList<>());
		}
		resultMap.get(aManualWebTester.getClass()).add(HttpResultWrapper.forData(aHttpResult, aShouldSuceed));
	}

	@Override
	public void onTestSucceed(ManualWebTester aManualWebTester) {
		
		wrapperDialog.publishResult(aManualWebTester, resultMap.get(aManualWebTester.getClass()));
		Class<? extends ManualWebTester> successorClass = getSuccessor(aManualWebTester);
		if (successorClass != null) {
			ApplicationRegistry.getInstance().getInteractionHandler()
					.confirm("Mit Test {" + successorClass.getSimpleName() + "} fortfahren?", true, wrapperDialog);	
		} else {
			ApplicationRegistry.getInstance().getInteractionHandler()
			.confirm("Alle Tests beendet!!!", true, wrapperDialog);
		}	
	}

	private Class<? extends ManualWebTester> getSuccessor(ManualWebTester aManualWebTester) {
				
		int index = testClasses.indexOf(aManualWebTester.getClass());
		try {
			Class<? extends ManualWebTester> successor = testClasses.get(index + 1);
			return successor;			
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	private List<Class<? extends ManualWebTester>> makeTestClassList() {
		List<Class<? extends ManualWebTester>> testClasses = new ArrayList<>();
		for (ManualWebTestDefinition aTestDefinition : testDefinitions) {
			testClasses.add(aTestDefinition.getTestClass());
		}
		return testClasses;
	}

	@Override
	public void handleTestException(ManualWebTesterException aManualWebTesterException,
			ManualWebTester aManualWebTester) {
		
		String message = "Fehler in Test {" + aManualWebTester.getClass().getSimpleName() + "}!!!";
		ApplicationRegistry.getInstance().getInteractionHandler()
				.showError(message, wrapperDialog);
		
		execute = false;
		
		System.err.println(message + " --> " + aManualWebTesterException.getMessage());
		aManualWebTesterException.printStackTrace();
	}
}