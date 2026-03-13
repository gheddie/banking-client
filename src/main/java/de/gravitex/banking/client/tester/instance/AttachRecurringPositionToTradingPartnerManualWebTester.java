package de.gravitex.banking.client.tester.instance;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.RecurringPosition;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.enumerated.ImportType;

public class AttachRecurringPositionToTradingPartnerManualWebTester extends ManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	private static final String ACCOUNT = "ACCOUNT";

	private static final String IMPORTED_BOOKINGS = "IMPORTED_BOOKINGS";

	@Override
	public ManualWebTester runTests() {

		// removeEntities();

		CreditInstitute creditInstitute = new CreditInstitute();
		creditInstitute.setImportType(ImportType.CSV_VB);
		creditInstitute.setBic("GENODEF1WBU");
		creditInstitute.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putCreditInstitute(creditInstitute), CREDIT_INSTITUTE);

		Account account = new Account();
		account.setCreditInstitute((CreditInstitute) getObjectCache().getEntity(CREDIT_INSTITUTE));
		account.setIdentifier("GIRO_PRIVAT");
		account.setName("Giro-Konto");
		expectSuccess(getBankingAccessor().putAccount(account), ACCOUNT);

		expectSuccess(getBankingAccessor().readCreditInstitutes());

		Account cachedAccount = (Account) getObjectCache().getEntity(ACCOUNT);

		expectSuccess(getBankingAccessor().importBookings(cachedAccount), IMPORTED_BOOKINGS);

		attachRecurringPosition("STERNICO GMBH", true);
		// attachRecurringPosition("Reno F120 Vechelde", false);
		
		List<TradingPartner> tradingPartners = new ArrayList<>();
		tradingPartners.add(getTradingPartner("STERNICO GMBH"));
		tradingPartners.add(getTradingPartner("KARL-HEINZ SCHULZ"));
		tradingPartners.add(getTradingPartner("ILSE SCHULZ"));
		expectSuccess(getBankingAccessor().createBookingProgress(null, null, tradingPartners));

		return this;
	}
	
	@SuppressWarnings("unchecked")
	private void attachRecurringPosition(String aTradingKey, boolean aExpectSuccess) {

		List<RecurringPosition> recurringPositions = (List<RecurringPosition>) getBankingAccessor()
				.readRecurringPositions().getEntityList();
		TradingPartner aTradingPartner = getTradingPartner("STERNICO GMBH");
		RecurringPosition rpos = recurringPositions.get(0);
		aTradingPartner.setRecurringPosition(rpos);
		if (aExpectSuccess) {
			expectSuccess(getBankingAccessor().patchTradingPartner(aTradingPartner));	
		} else {
			expectFailure(getBankingAccessor().patchTradingPartner(aTradingPartner));
		}
	}
}