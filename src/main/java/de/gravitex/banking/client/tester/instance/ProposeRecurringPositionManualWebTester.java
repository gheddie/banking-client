package de.gravitex.banking.client.tester.instance;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.RecurringPosition;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking.enumerated.RecurringInterval;
import de.gravitex.banking_core.dto.RecurringPositionProposal;
import de.gravitex.banking_core.dto.TradingPartnerRecurringPositionProposal;

public class ProposeRecurringPositionManualWebTester extends BankingLogicManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";

	private static final String ACCOUNT = "ACCOUNT";

	public ProposeRecurringPositionManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
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

		importBookings(cachedAccount, "365_Tage_2026.03.20.csv", 1072);
		
		// Monatlich eingehend
		expectMonthlyIncoming("STERNICO GMBH");
				
		// Monatlich ausgehend
		expectMonthlyOutgoing("Plan International Deutschland e.V.");
		expectMonthlyOutgoing("Diana Schulz");
		expectMonthlyOutgoing("VOLKSWOHL BUND Lebensversicherung a.G.");
		expectMonthlyOutgoing("R + V LEBENSVERSICHERUNG AKTIENGESELLSCHAFT");
		expectMonthlyOutgoing("Michael Stolze");		
		expectMonthlyOutgoing("Tim Schulz");
				
		// Kein
		expectNone("E-Ankermann Wendeburg sagt Danke");
		expectNone("First Data GmbH");
		expectNone("01649 MCDONALDS");
		expectNone("01799 MCDONALDS");
		expectNone("01647 MCDONALDS");
		expectNone("00436 MCDONALDS");
		expectNone("01139 MCDONALDS");
		expectNone("01563 MCDONALDS");
		expectNone("01712 MCDONALDS");
		expectNone("01821 MCDONALDS");
		expectNone("01828 MCDONALDS");
		expectNone("00328 MCDONALDS");
		expectNone("00668 MCDONALDS");
		expectNone("01465 MCDONALDS");
		expectNone("01868 MCDONALDS");
		expectNone("KAUFLAND");
		expectNone("REWE Kevin Braeutigam oHG");
		expectNone("REWE Markt GmbH");
		expectNone("REWE Martin Bornemann oHG");
		expectNone("REWE Patrick Ney o");
		expectNone("REWE Markt Thomas");
		
		// Kein (zu wenige Buchungen)
		expectNone("Sascha Ehlert");
		
		return this;
	}

	private void expectNone(String aTradingKey) {
		List<TradingPartner> list = new ArrayList<>();
		list.add(findTradingPartner(aTradingKey));
		HttpPostResult result = getBankingAccessor().getRecurringPositionProposals(list);
		expectSuccess(result, null, null);
		RecurringPositionProposal proposal = (RecurringPositionProposal) result.getResponseObject();
		getValidator().assertNull(proposal.findByTradingKey(aTradingKey));
	}

	private void expectMonthlyOutgoing(String aTradingKey) {
		RecurringPosition expected = makeExpectedRecurringPosition(RecurringInterval.MONTHLY, false);
		List<TradingPartner> list = new ArrayList<>();
		list.add(findTradingPartner(aTradingKey));
		HttpPostResult result = getBankingAccessor().getRecurringPositionProposals(list);
		expectSuccess(result, null, null);
		RecurringPositionProposal proposal = (RecurringPositionProposal) result.getResponseObject();
		TradingPartnerRecurringPositionProposal found = proposal.findByTradingKey(aTradingKey);
		getValidator().assertNotNull(found);
		getValidator().compare(expected, found.getRecurringPosition());
	}

	private void expectMonthlyIncoming(String aTradingKey) {
		RecurringPosition expected = makeExpectedRecurringPosition(RecurringInterval.MONTHLY, true);
		List<TradingPartner> list = new ArrayList<>();
		list.add(findTradingPartner(aTradingKey));
		HttpPostResult result = getBankingAccessor().getRecurringPositionProposals(list);
		expectSuccess(result, null, null);
		RecurringPositionProposal proposal = (RecurringPositionProposal) result.getResponseObject();
		TradingPartnerRecurringPositionProposal found = proposal.findByTradingKey(aTradingKey);
		getValidator().assertNotNull(found);
		getValidator().compare(expected, found.getRecurringPosition());
	}

	private RecurringPosition makeExpectedRecurringPosition(RecurringInterval aRecurringInterval, boolean aIncoming) {
		
		RecurringPosition result = new RecurringPosition();
		result.setIncoming(aIncoming);
		result.setRecurringInterval(aRecurringInterval);
		return result;
	}
}