package de.gravitex.banking.client.tester.instance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.matcher.ResponseLengthValidator;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.BookingImport;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking_core.dto.BookingImportSummary;
import de.gravitex.banking_core.dto.MergeTradingPartners;
import de.gravitex.banking_core.dto.UnprocessedBookingImport;

public class ImportBookingFilesManualWebTester extends BankingLogicManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	private static final String ACCOUNT = "ACCOUNT";

	private static final String IMPORTED_BOOKINGS = "IMPORTED_BOOKINGS";

	private static final String READ_BOOKINGS = "READ_BOOKINGS";

	private static final String CREDIT_INSTITUTE_DELETABLE = "CREDIT_INSTITUTE_DELETABLE";

	private static final String UNPROCESSED_BOOKING_IMPORTS = "UNPROCESSED_BOOKING_IMPORTS";

	private static final String BOOKING_SUMMARY = "BOOKING_SUMMARY";
	
	public ImportBookingFilesManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
		super(aWebTestWatcher, isActive);
	}

	@Override
	public ManualWebTester runTests() {
		
		CreditInstitute creditInstitute = new CreditInstitute();
		creditInstitute.setImportType(ImportType.CSV_VB);
		creditInstitute.setBic("GENODEF1WBU");
		creditInstitute.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putEntity(creditInstitute), CREDIT_INSTITUTE, null);
		
		CreditInstitute creditInstituteDeletable = new CreditInstitute();
		creditInstituteDeletable.setImportType(ImportType.CSV_VB);
		creditInstituteDeletable.setBic("GENODEF2WBU");
		creditInstituteDeletable.setName("Giro-Konto 2");
		expectSuccess(getBankingAccessor().putEntity(creditInstituteDeletable), CREDIT_INSTITUTE_DELETABLE, null);

		Account account = new Account();
		account.setCreditInstitute((CreditInstitute) getObjectCache().getEntity(CREDIT_INSTITUTE));
		account.setIdentifier("GIRO_PRIVAT");
		account.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putEntity(account), ACCOUNT, null);
		
		expectSuccess(getBankingAccessor().readEntityList(CreditInstitute.class), null, null);

		Account cachedAccount = (Account) getObjectCache().getEntity(ACCOUNT);
		
		importBookings(cachedAccount, "TestUmsaetze1.csv", 6);
		
		// 3 new bookings expected...
		importBookings(cachedAccount, "TestUmsaetze2.csv", 3);
		
		// (again) 3 new bookings expected...
		importBookings(cachedAccount, "TestUmsaetze3.csv", 3);
		
		// 3 booking import must be present...
		expectSuccess(getBankingAccessor().readEntityList(BookingImport.class), null,
				ResponseLengthValidator.forExpectedResponseSize(3));

		return this;
	}

	private void mergeTradingPartners(String aTradingKeySnippet, String newTradingKey) {
		
		MergeTradingPartners mergeTradingPartners = new MergeTradingPartners();
		mergeTradingPartners.setNewTradingKey(newTradingKey);
		mergeTradingPartners.setPartnersToMerge(getPartnersToConcat(aTradingKeySnippet));
		expectSuccess(getBankingAccessor().mergeTradingPartners(mergeTradingPartners), null, null);
	}

	@SuppressWarnings("unchecked")
	private List<TradingPartner> getPartnersToConcat(String aTradingKeySnippet) {
		List<TradingPartner> partners = (List<TradingPartner>) getBankingAccessor().readEntityList(TradingPartner.class)
				.getEntityList();
		List<TradingPartner> result = new ArrayList<>();
		for (TradingPartner aTradingPartner : partners) {
			if (aTradingPartner.getTradingKey().contains(aTradingKeySnippet)) {
				result.add(aTradingPartner);
			}
		}
		return result;
	}
}