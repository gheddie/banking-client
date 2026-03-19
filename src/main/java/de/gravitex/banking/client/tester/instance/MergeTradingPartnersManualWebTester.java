package de.gravitex.banking.client.tester.instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.matcher.ResponseLengthValidator;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.TradingPartnerBookingHistory;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public class MergeTradingPartnersManualWebTester extends BankingLogicManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	private static final String ACCOUNT = "ACCOUNT";

	@SuppressWarnings("unchecked")
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

		importBookings(cachedAccount, "Gehaltsbuchungen.csv", 4, 5);
		importBookings(cachedAccount, "Einkaufsbuchungen.csv", 6, 5);

		MergeTradingPartners merge = new MergeTradingPartners();
		merge.setNewTradingKey("123");
		merge.setPartnersToMerge(getTradingPartners("Sternico GmbH", "Edeka Wendeburg"));
		expectSuccess(getBankingAccessor().mergeTradingPartners(merge), null, null);

		HttpGetResult readTradingPartnerBookingHistoriesResult = getBankingAccessor()
				.readTradingPartnerBookingHistories();
		expectSuccess(readTradingPartnerBookingHistoriesResult, null,
				ResponseLengthValidator.forExpectedResponseSize(10));

		List<TradingPartnerBookingHistory> histories = (List<TradingPartnerBookingHistory>) readTradingPartnerBookingHistoriesResult
				.getEntityList();

		Map<String, List<TradingPartnerBookingHistory>> mapped = mapTradingPartnerBookingHistoriesByTradingKey(
				histories);
				
		List<TradingPartnerBookingHistory> edeka = mapped.get("Edeka Wendeburg");
		getValidator().notNull(edeka);
		getValidator().validate(6, edeka.size());
		
		List<TradingPartnerBookingHistory> sternico = mapped.get("Sternico GmbH");
		getValidator().notNull(sternico);
		getValidator().validate(4, sternico.size());

		return this;
	}
}