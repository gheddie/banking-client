package de.gravitex.banking.client.accessor;

import java.time.LocalDate;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public interface IBankingAccessor {
	
	// read (special)
	HttpGetResult readAccountById(Long accountId);
	HttpGetResult readBookingById(Long bookingId);
	HttpGetResult readAccounts(CreditInstitute creditInstitute);
	HttpGetResult readBookingViewsByAccount(Account account);
	HttpGetResult readBookingViewsByTradingPartner(TradingPartner aTradingPartner);

	// read (simple crud)
	HttpGetResult readCreditInstitutes();			
	HttpGetResult readTradingPartners();		
	HttpGetResult readPurposeCategorys();	
	HttpGetResult readStandingOrders();
	HttpGetResult readBookings();
	HttpGetResult readAdminData();
	HttpGetResult readAccounts();
	HttpGetResult readAccountInfos();
	HttpGetResult readRecurringPositions();
	HttpGetResult readBudgetPlannings();
	HttpGetResult readBookingImports();
	HttpGetResult readTradingPartnerBookingHistories();

	// generic crud
	HttpGetResult findAllEntities(Class<? extends IdEntity> aEntityClass);
	HttpDeleteResult deleteEntity(IdEntity aEntity);
	HttpPutResult putEntity(IdEntity aEntity);
	HttpPatchResult patchEntity(IdEntity aEntity);

	// misc
	HttpGetResult importBookings(Account account);
	HttpPostResult mergeTradingPartners(MergeTradingPartners aMergeTradingPartners);
	HttpPostResult createBookingProgress(LocalDate from, LocalDate to, List<TradingPartner> aTradingPartners);
	HttpGetResult readUnprocessedBookingImports(Account account);
	HttpGetResult importBookingFile(Account account, String aBookingFileName);
	HttpPostResult createBookingOverview(Account account, LocalDate from, LocalDate to);	
}