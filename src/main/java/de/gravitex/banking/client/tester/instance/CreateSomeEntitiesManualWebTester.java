package de.gravitex.banking.client.tester.instance;

import java.util.List;

import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.RecurringPosition;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking.enumerated.RecurringInterval;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public class CreateSomeEntitiesManualWebTester extends ManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	private static final String ACCOUNT = "ACCOUNT";

	private static final String IMPORTED_BOOKINGS = "IMPORTED_BOOKINGS";

	private static final String READ_BOOKINGS = "READ_BOOKINGS";

	private static final String CREDIT_INSTITUTE_DELETABLE = "CREDIT_INSTITUTE_DELETABLE";

	@SuppressWarnings("unchecked")
	@Override
	public ManualWebTester runTests() {

		CreditInstitute creditInstitute = new CreditInstitute();
		creditInstitute.setImportType(ImportType.CSV_VB);
		creditInstitute.setBic("GENODEF1WBU");
		creditInstitute.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putCreditInstitute(creditInstitute), CREDIT_INSTITUTE);
		
		CreditInstitute creditInstituteDeletable = new CreditInstitute();
		creditInstituteDeletable.setImportType(ImportType.CSV_VB);
		creditInstituteDeletable.setBic("GENODEF2WBU");
		creditInstituteDeletable.setName("Giro-Konto 2");
		expectSuccess(getBankingAccessor().putCreditInstitute(creditInstituteDeletable), CREDIT_INSTITUTE_DELETABLE);

		Account account = new Account();
		account.setCreditInstitute((CreditInstitute) getObjectCache().getEntity(CREDIT_INSTITUTE));
		account.setIdentifier("GIRO_PRIVAT");
		account.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putAccount(account), ACCOUNT);
		
		expectSuccess(getBankingAccessor().readCreditInstitutes());

		Account cachedAccount = (Account) getObjectCache().getEntity(ACCOUNT);

		expectSuccess(getBankingAccessor().importBookings(cachedAccount), IMPORTED_BOOKINGS);

		describeCachedObject(IMPORTED_BOOKINGS);

		expectSuccess(getBankingAccessor().readBookingViewsByAccount(cachedAccount), READ_BOOKINGS);

		describeCachedObject(READ_BOOKINGS);

		cachedAccount.setName("Paul");
		expectSuccess(getBankingAccessor().patchAccount(cachedAccount));

		// Kredit-Institut kann nicht gelöscht werden
		expectFailure(getBankingAccessor().deleteEntity(getObjectCache().getEntity(CREDIT_INSTITUTE)),
				errorMessageContains("wird referenziert"));

		PurposeCategory purposeCategory = new PurposeCategory();
		purposeCategory.setPurposeKey("Einkauf");
		expectSuccess(getBankingAccessor().putPurposeCategory(purposeCategory));

		MergeTradingPartners mergeTradingPartners = new MergeTradingPartners();
		mergeTradingPartners.setNewTradingKey("Neu");
		mergeTradingPartners
				.setPartnersToMerge((List<TradingPartner>) getBankingAccessor().readTradingPartners().getEntityList());
		expectSuccess(getBankingAccessor().mergeTradingPartners(mergeTradingPartners));
		
		expectSuccess(getBankingAccessor().deleteEntity(getObjectCache().getEntity(CREDIT_INSTITUTE_DELETABLE)));

		return this;
	}
}