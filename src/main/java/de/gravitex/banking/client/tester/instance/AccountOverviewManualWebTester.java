package de.gravitex.banking.client.tester.instance;

import java.time.LocalDate;

import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking_core.dto.BookingOverview;

public class AccountOverviewManualWebTester extends BankingLogicManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";
	
	private static final String ACCOUNT = "ACCOUNT";

	private static final String OVERVIEW_UNMERGED = "OVERVIEW_UNMERGED";

	public AccountOverviewManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
		super(aWebTestWatcher, isActive);
	}

	@Override
	public ManualWebTester runTests() {
		
		CreditInstitute creditInstitute = new CreditInstitute();
		creditInstitute.setImportType(ImportType.CSV_VB);
		creditInstitute.setBic("GENODEF1WBU");
		creditInstitute.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putCreditInstitute(creditInstitute), CREDIT_INSTITUTE, null);
		
		Account account = new Account();
		account.setCreditInstitute((CreditInstitute) getObjectCache().getEntity(CREDIT_INSTITUTE));
		account.setIdentifier("GIRO_PRIVAT");
		account.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putAccount(account), ACCOUNT, null);
		
		expectSuccess(getBankingAccessor().readCreditInstitutes(), null, null);

		Account cachedAccount = (Account) getObjectCache().getEntity(ACCOUNT);
		
		importBookings(cachedAccount, "UmsaetzeGiroVb20260319.csv", 228);
		
		expectSuccess(getBankingAccessor().createBookingOverview(cachedAccount, LocalDate.of(2000, 1, 1),
				LocalDate.of(2030, 12, 31)), OVERVIEW_UNMERGED, null);
		
		createDefaultPurposeCategories();
		
		BookingOverview cachedOverview = (BookingOverview) getObjectCache().getObject(OVERVIEW_UNMERGED);
		
		return this;
	}
}