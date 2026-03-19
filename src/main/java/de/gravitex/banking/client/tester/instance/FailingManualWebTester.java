package de.gravitex.banking.client.tester.instance;

import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.matcher.ResponseLengthValidator;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.enumerated.ImportType;

public class FailingManualWebTester extends BankingLogicManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	public FailingManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
		super(aWebTestWatcher, isActive);
	}

	@Override
	public ManualWebTester runTests() {
		
		CreditInstitute creditInstitute = new CreditInstitute();
		creditInstitute.setImportType(ImportType.CSV_VB);
		creditInstitute.setBic("GENODEF1WBU");
		creditInstitute.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putEntity(creditInstitute), CREDIT_INSTITUTE, null);
		
		expectSuccess(getBankingAccessor().readEntityList(CreditInstitute.class), null,
				ResponseLengthValidator.forExpectedResponseSize(99));
		
		return this;
	}
}