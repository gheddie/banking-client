package de.gravitex.banking.client.accessor;

import java.time.LocalDate;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.Booking;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.RecurringPosition;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public interface IBankingAccessor {

	// read
	HttpGetResult readAccountById(Long accountId);
	HttpGetResult readBookingById(Long bookingId);
	HttpGetResult readCreditInstitutes();	
	HttpGetResult readAccounts(CreditInstitute creditInstitute);	
	HttpGetResult readTradingPartners();	
	HttpGetResult readBookingViewsByAccount(Account account);
	HttpGetResult readPurposeCategorys();
	HttpGetResult readBookingViewsByTradingPartner(TradingPartner aTradingPartner);
	HttpGetResult readStandingOrders();
	HttpGetResult readBookings();
	HttpGetResult readAdminData();
	HttpGetResult readAccounts();
	HttpGetResult readAccountInfos();
	HttpGetResult readRecurringPositions();
	HttpGetResult readBudgetPlannings();

	// patch
	HttpPatchResult patchBooking(Booking aBooking);
	HttpPatchResult patchCreditInstitute(CreditInstitute aCreditInstitute);
	HttpPatchResult patchTradingPartner(TradingPartner aTradingPartner);
	HttpPatchResult patchAccount(Account account);
	HttpPatchResult patchPurposeCategory(PurposeCategory aPurposeCategory);

	// put
	HttpPutResult putTradingPartner(TradingPartner aTradingPartner);
	HttpPutResult putPurposeCategory(PurposeCategory aPurposeCategory);
	HttpPutResult putCreditInstitute(CreditInstitute aCreditInstitute);
	HttpPutResult putAccount(Account entity);
	HttpPutResult putRecurringPosition(RecurringPosition aRecurringPosition);

	// misc
	HttpGetResult importBookings(Account account);
	HttpPostResult mergeTradingPartners(MergeTradingPartners aMergeTradingPartners);
	HttpPostResult createBookingProgress(LocalDate from, LocalDate to, List<TradingPartner> aTradingPartners);
	
	// generic crud
	HttpGetResult findAllEntities(Class<? extends IdEntity> aEntityClass);
	HttpDeleteResult deleteEntity(IdEntity aEntity);
}