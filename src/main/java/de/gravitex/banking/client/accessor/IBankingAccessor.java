package de.gravitex.banking.client.accessor;

import java.util.List;

import de.gravitex.banking_core.controller.admin.BookingAdminData;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.StandingOrder;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.view.BookingView;

public interface IBankingAccessor {

	List<CreditInstitute> readCreditInstitutes();
	
	List<Account> readAccounts(CreditInstitute creditInstitute);
	
	List<TradingPartner> readTradingPartners();
	
	List<BookingView> readBookingViewsByAccount(Account account);

	List<PurposeCategory> readPurposeCategorys();

	void updateTradingPartner(Long aTradingPartnerId, Long aPurposeCategoryId);

	List<BookingView> readBookingViewsByTradingPartner(TradingPartner aTradingPartner);

	List<StandingOrder> readStandingOrders();

	List<Booking> readBookings();

	void saveBooking(Booking aBooking);

	void saveCreditInstitute(CreditInstitute aCreditInstitute);

	void saveTradingPartner(TradingPartner aTradingPartner);

	BookingAdminData readAdminData();
}