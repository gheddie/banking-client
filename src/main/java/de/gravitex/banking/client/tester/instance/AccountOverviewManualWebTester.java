package de.gravitex.banking.client.tester.instance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.dto.BookingOverviewAdapter;
import de.gravitex.banking.client.tester.instance.base.BankingLogicManualWebTester;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.enumerated.ImportType;
import de.gravitex.banking_core.dto.BookingOverview;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public class AccountOverviewManualWebTester extends BankingLogicManualWebTester {

	private static final String CREDIT_INSTITUTE = "CREDIT_INSTITUTE";
	
	private static final String ACCOUNT = "ACCOUNT";

	private static final String OVERVIEW_UNMERGED = "OVERVIEW_UNMERGED";

	private static final String OVERVIEW_MERGED = "OVERVIEW_MERGED";

	public AccountOverviewManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
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
		
		importBookings(cachedAccount, "UmsaetzeGiroVb20260319.csv", 228);
		
		createDefaultPurposeCategories();			
		
		PurposeCategory purposeCategoryFastFood = findPurposeCategory("Fast Food");
		
		attachPurposeCategory(findTradingPartner("00436 MCDONALDS"), purposeCategoryFastFood);
		attachPurposeCategory(findTradingPartner("01647 MCDONALDS"), purposeCategoryFastFood);
		attachPurposeCategory(findTradingPartner("01649 MCDONALDS"), purposeCategoryFastFood);
		attachPurposeCategory(findTradingPartner("01799 MCDONALDS"), purposeCategoryFastFood);
		
		PurposeCategory purposeCategoryEinkauf = findPurposeCategory("Einkauf");
		
		attachPurposeCategory(findTradingPartner("REWE Kevin Braeutigam oHG"), purposeCategoryEinkauf);
		attachPurposeCategory(findTradingPartner("ALDI SE + Co. KG SIEVERSHAUSEN"), purposeCategoryEinkauf);		
		attachPurposeCategory(findTradingPartner("E-Ankermann Wendeburg sagt Danke"), purposeCategoryEinkauf);
		attachPurposeCategory(findTradingPartner("REWE Markt GmbH"), purposeCategoryEinkauf);	
		attachPurposeCategory(findTradingPartner("NAH + GUT 4845, PEINE"), purposeCategoryEinkauf);
		
		expectSuccess(getBankingAccessor().createBookingOverview(cachedAccount, LocalDate.of(2000, 1, 1),
				LocalDate.of(2030, 12, 31)), OVERVIEW_UNMERGED, null);
		
		BookingOverview cachedOverview = (BookingOverview) getObjectCache().getObject(OVERVIEW_UNMERGED);
		
		BookingOverviewAdapter adapter = new BookingOverviewAdapter(cachedOverview);
		
		BigDecimal puröposeSumFastfood = adapter.getSum("Fast Food");
		System.out.println("[Fast Food]: " + puröposeSumFastfood);
		
		BigDecimal puröposeSumEinkauf = adapter.getSum("Einkauf");
		System.out.println("[Einkauf]: " + puröposeSumEinkauf);
		
		BigDecimal purposeSumUnassigned = adapter.getUnassignedSum();
		System.out.println("[unassigned]: " + purposeSumUnassigned);
		
		// Zusammenfassen der TradingPartner...	
		
		mergeTradingPartners("McdConcatted", "00436 MCDONALDS", "01647 MCDONALDS", "01649 MCDONALDS",
				"01799 MCDONALDS");
		mergeTradingPartners("EinkaufConcatted", "REWE Kevin Braeutigam oHG", "ALDI SE + Co. KG SIEVERSHAUSEN",
				"E-Ankermann Wendeburg sagt Danke", "REWE Markt GmbH", "NAH + GUT 4845, PEINE");
		
		expectSuccess(getBankingAccessor().createBookingOverview(cachedAccount, LocalDate.of(2000, 1, 1),
				LocalDate.of(2030, 12, 31)), OVERVIEW_MERGED, null);
		
		BookingOverview mergedOverview = (BookingOverview) getObjectCache().getObject(OVERVIEW_MERGED);
		
		BookingOverviewAdapter adapterMerged = new BookingOverviewAdapter(mergedOverview);
		
		// Gleiche Summen?
		
		getValidator().compare(puröposeSumFastfood, adapterMerged.getSum("Fast Food"));
		getValidator().compare(puröposeSumEinkauf, adapterMerged.getSum("Einkauf"));
		
		return this;
	}

	private void mergeTradingPartners(String aNewTradingKey, String... aTradingKeys) {
		
		List<TradingPartner> partnersToMerge = new ArrayList<>();
		for (String aKey : aTradingKeys) {
			partnersToMerge.add(findTradingPartner(aKey));	
		}
		MergeTradingPartners merge = new MergeTradingPartners();
		merge.setNewTradingKey(aNewTradingKey);		
		merge.setPartnersToMerge(partnersToMerge);
		
		expectSuccess(getBankingAccessor().mergeTradingPartners(merge), null, null);
	}
}