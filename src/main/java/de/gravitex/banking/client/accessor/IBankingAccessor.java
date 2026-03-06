package de.gravitex.banking.client.accessor;

import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.util.EntityRequester;
import de.gravitex.banking_core.controller.admin.BookingAdminData;
import de.gravitex.banking_core.dto.AccountInfo;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.StandingOrder;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.view.BookingView;

public interface IBankingAccessor {

	// read
	Account readAccountById(Long accountId, EntityRequester entityRequester);
	Booking readBookingById(Long bookingId, EntityRequester entityRequester);
	List<CreditInstitute> readCreditInstitutes(EntityRequester entityRequester);	
	List<Account> readAccounts(CreditInstitute creditInstitute, EntityRequester entityRequester);	
	List<TradingPartner> readTradingPartners(EntityRequester entityRequester);	
	List<BookingView> readBookingViewsByAccount(Account account, EntityRequester entityRequester);
	List<PurposeCategory> readPurposeCategorys(EntityRequester entityRequester);
	List<BookingView> readBookingViewsByTradingPartner(TradingPartner aTradingPartner, EntityRequester entityRequester);
	List<StandingOrder> readStandingOrders(EntityRequester entityRequester);
	List<Booking> readBookings(EntityRequester entityRequester);
	BookingAdminData readAdminData(EntityRequester entityRequester);
	List<Account> readAccounts(EntityRequester entityRequester);
	List<AccountInfo> readAccountInfos(EntityRequester entityRequester);

	// patch
	HttpPatchResult patchBooking(Booking aBooking);
	HttpPatchResult patchCreditInstitute(CreditInstitute aCreditInstitute);
	HttpPatchResult patchTradingPartner(TradingPartner aTradingPartner);
	HttpPatchResult patchAccount(Account account);

	// put
	HttpPutResult putTradingPartner(TradingPartner aTradingPartner);
	HttpPutResult putPurposeCategory(PurposeCategory aPurposeCategory);
	HttpPutResult putCreditInstitute(CreditInstitute aCreditInstitute);
	HttpPutResult putAccount(Account entity);

	// delete
	HttpDeleteResult deleteEntity(IdEntity aEntity);	
}