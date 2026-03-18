package de.gravitex.banking.client.tester.instance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.matcher.ResponseLengthValidator;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking_core.controller.bookingimport.UnprocessedBookingImport;
import de.gravitex.banking_core.dto.BookingImportSummary;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public class ImportBookingFilesManualWebTester extends ManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	private static final String ACCOUNT = "ACCOUNT";

	private static final String IMPORTED_BOOKINGS = "IMPORTED_BOOKINGS";

	private static final String READ_BOOKINGS = "READ_BOOKINGS";

	private static final String CREDIT_INSTITUTE_DELETABLE = "CREDIT_INSTITUTE_DELETABLE";

	private static final String UNPROCESSED_BOOKING_IMPORTS = "UNPROCESSED_BOOKING_IMPORTS";

	private static final String BOOKING_SUMMARY = "BOOKING_SUMMARY";

	@Override
	public ManualWebTester runTests() {
		
		// removeEntities();

		CreditInstitute creditInstitute = new CreditInstitute();
		creditInstitute.setImportType(ImportType.CSV_VB);
		creditInstitute.setBic("GENODEF1WBU");
		creditInstitute.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putCreditInstitute(creditInstitute), CREDIT_INSTITUTE, null);
		
		CreditInstitute creditInstituteDeletable = new CreditInstitute();
		creditInstituteDeletable.setImportType(ImportType.CSV_VB);
		creditInstituteDeletable.setBic("GENODEF2WBU");
		creditInstituteDeletable.setName("Giro-Konto 2");
		expectSuccess(getBankingAccessor().putCreditInstitute(creditInstituteDeletable), CREDIT_INSTITUTE_DELETABLE, null);

		Account account = new Account();
		account.setCreditInstitute((CreditInstitute) getObjectCache().getEntity(CREDIT_INSTITUTE));
		account.setIdentifier("GIRO_PRIVAT");
		account.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putAccount(account), ACCOUNT, null);
		
		expectSuccess(getBankingAccessor().readCreditInstitutes(), null, null);

		Account cachedAccount = (Account) getObjectCache().getEntity(ACCOUNT);
		
		importBookings(cachedAccount, "TestUmsaetze1.csv", 6);
		
		// 3 new bookings expected...
		importBookings(cachedAccount, "TestUmsaetze2.csv", 3);
		
		// (again) 3 new bookings expected...
		importBookings(cachedAccount, "TestUmsaetze3.csv", 3);
		
		// 3 booking import must be present...
		expectSuccess(getBankingAccessor().readBookingImports(), null,
				ResponseLengthValidator.forExpectedResponseSize(3));

		return this;
	}

	@SuppressWarnings("unchecked")
	private void importBookings(Account account, String aBookingFileName, int aExpectedImportCount) {			
		
		expectSuccess(getBankingAccessor().readUnprocessedBookingImports(account), UNPROCESSED_BOOKING_IMPORTS,
				ResponseLengthValidator.forExpectedResponseSize(3));
		List<UnprocessedBookingImport> unprocessedBookingImports = (List<UnprocessedBookingImport>) getObjectCache()
				.getObject(UNPROCESSED_BOOKING_IMPORTS);
		Set<String> unprocessedFileNames = new HashSet<>();
		for (UnprocessedBookingImport aUnprocessedBookingImport : unprocessedBookingImports) {
			unprocessedFileNames.add(aUnprocessedBookingImport.getBookingFileName());
		}
		if (!unprocessedFileNames.contains(aBookingFileName)) {
			throw new ManualWebTesterException("unporcessed booking file {" + aBookingFileName
					+ "} not present for account {" + account.getName() + "}!!!");
		}
		HttpGetResult importBookingFile = getBankingAccessor().importBookingFile(account, aBookingFileName);		
		BookingImportSummary summary = (BookingImportSummary) importBookingFile.getEntity();
		getValidator().validate(aExpectedImportCount, summary.getImportedBookings().size());
		expectSuccess(importBookingFile, null, null);
	}

	private void mergeTradingPartners(String aTradingKeySnippet, String newTradingKey) {
		
		MergeTradingPartners mergeTradingPartners = new MergeTradingPartners();
		mergeTradingPartners.setNewTradingKey(newTradingKey);
		mergeTradingPartners.setPartnersToMerge(getPartnersToConcat(aTradingKeySnippet));
		expectSuccess(getBankingAccessor().mergeTradingPartners(mergeTradingPartners), null, null);
	}

	@SuppressWarnings("unchecked")
	private List<TradingPartner> getPartnersToConcat(String aTradingKeySnippet) {
		List<TradingPartner> partners = (List<TradingPartner>) getBankingAccessor().readTradingPartners()
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