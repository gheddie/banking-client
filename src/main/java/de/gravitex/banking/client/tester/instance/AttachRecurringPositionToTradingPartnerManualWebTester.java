package de.gravitex.banking.client.tester.instance;

import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking.enumerated.RecurringInterval;

public class AttachRecurringPositionToTradingPartnerManualWebTester extends BankingLogicManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	private static final String ACCOUNT = "ACCOUNT";
	
	public AttachRecurringPositionToTradingPartnerManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
		super(aWebTestWatcher, isActive);
	}

	@Override
	public ManualWebTester runTests() {

		CreditInstitute creditInstitute = new CreditInstitute();
		creditInstitute.setImportType(ImportType.CSV_VB);
		creditInstitute.setBic("GENODEF1WBU");
		creditInstitute.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putEntity(creditInstitute), CREDIT_INSTITUTE, null);

		Account account = new Account();
		account.setCreditInstitute((CreditInstitute) getObjectCache().getEntity(CREDIT_INSTITUTE));
		account.setIdentifier("GIRO_PRIVAT");
		account.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putEntity(account), ACCOUNT, null);

		expectSuccess(getBankingAccessor().readEntityList(CreditInstitute.class), null, null);

		Account cachedAccount = (Account) getObjectCache().getEntity(ACCOUNT);
		
		importBookings(cachedAccount, "Gehaltsbuchungen.csv", 4);
		importBookings(cachedAccount, "Einkaufsbuchungen.csv", 6);
		
		attachRecurringPosition("Sternico GmbH", RecurringInterval.MONTHLY, true, true, null);
		attachRecurringPosition("Edeka Wendeburg", RecurringInterval.MONTHLY, false, false, errorMessageContains("cannot attach recurring position"));		

		return this;
	}
}