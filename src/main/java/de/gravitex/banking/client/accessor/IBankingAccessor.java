package de.gravitex.banking.client.accessor;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.util.EntityRequester;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.Booking;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public interface IBankingAccessor {

	// read
	HttpGetResult readAccountById(Long accountId, EntityRequester entityRequester);
	HttpGetResult readBookingById(Long bookingId, EntityRequester entityRequester);
	HttpGetResult readCreditInstitutes(EntityRequester entityRequester);	
	HttpGetResult readAccounts(CreditInstitute creditInstitute, EntityRequester entityRequester);	
	HttpGetResult readTradingPartners(EntityRequester entityRequester);	
	HttpGetResult readBookingViewsByAccount(Account account, EntityRequester entityRequester);
	HttpGetResult readPurposeCategorys(EntityRequester entityRequester);
	HttpGetResult readBookingViewsByTradingPartner(TradingPartner aTradingPartner, EntityRequester entityRequester);
	HttpGetResult readStandingOrders(EntityRequester entityRequester);
	HttpGetResult readBookings(EntityRequester entityRequester);
	HttpGetResult readAdminData(EntityRequester entityRequester);
	HttpGetResult readAccounts(EntityRequester entityRequester);
	HttpGetResult readAccountInfos(EntityRequester entityRequester);
	HttpGetResult readRecurringPositions(EntityRequester entityRequester);
	HttpGetResult readBudgetPlannings(EntityRequester entityRequester);

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

	// delete
	HttpDeleteResult deleteEntity(IdEntity aEntity);
	
	// misc
	HttpGetResult importBookings(Account account);
	HttpPostResult mergeTradingPartners(MergeTradingPartners aMergeTradingPartners);	
}