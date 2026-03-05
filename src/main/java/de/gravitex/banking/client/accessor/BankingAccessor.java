package de.gravitex.banking.client.accessor;

import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.util.EntityRequester;
import de.gravitex.banking.client.exception.EntityRequestException;
import de.gravitex.banking_core.controller.admin.BookingAdminData;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.StandingOrder;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingAccessor implements IBankingAccessor {

	private HttpRemoteHandler remoteHandler;

	public BankingAccessor() {
		super();
		remoteHandler = new HttpRemoteHandler();
	}

	public List<CreditInstitute> readCreditInstitutes(EntityRequester entityRequester) {
		checkEntityRequester(entityRequester);
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(CreditInstitute.class));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public List<TradingPartner> readTradingPartners(EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(TradingPartner.class));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public List<PurposeCategory> readPurposeCategorys(EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(PurposeCategory.class));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public List<BookingView> readBookingViewsByAccount(Account account, EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(
					HttpRequestBuilder.forEntityList(BookingView.class).byAttribute("account").identified(account.getId()));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public List<BookingView> readBookingViewsByTradingPartner(TradingPartner aTradingPartner, EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(BookingView.class)
					.byAttribute("tradingpartner").identified(aTradingPartner.getId()));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public List<Account> readAccounts(CreditInstitute creditInstitute, EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Account.class)
					.byAttribute("creditInstitute").identified(creditInstitute.getId()));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public List<StandingOrder> readStandingOrders(EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(StandingOrder.class));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public List<Booking> readBookings(EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Booking.class));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	public HttpPatchResult patchBooking(Booking aBooking) {
		String url = HttpRequestBuilder.forEntity(Booking.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, aBooking);
	}

	public HttpPatchResult patchCreditInstitute(CreditInstitute aCreditInstitute) {
		String url = HttpRequestBuilder.forEntity(CreditInstitute.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, aCreditInstitute);
	}

	public HttpPatchResult patchTradingPartner(TradingPartner aTradingPartner) {
		String url = HttpRequestBuilder.forEntity(TradingPartner.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, aTradingPartner);
	}

	public BookingAdminData readAdminData(EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntity(HttpRequestBuilder.forEntity(BookingAdminData.class), BookingAdminData.class);
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	@Override
	public List<Account> readAccounts(EntityRequester entityRequester) {
		try {
			return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Account.class));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	@Override
	public HttpPatchResult patchAccount(Account account) {
		String url = HttpRequestBuilder.forEntity(Account.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, account);
	}

	@Override
	public HttpDeleteResult deleteEntity(IdEntity aEntity) {
		String aUrl = HttpRequestBuilder.forEntity(aEntity.getClass()).identified(aEntity.getId()).buildRequestUrl();
		return remoteHandler.deleteEntity(aUrl, aEntity);
	}

	@Override
	public Account readAccountById(Long accountId, EntityRequester entityRequester) {
		try {
			return remoteHandler.readById(HttpRequestBuilder.forEntity(Account.class).identified(accountId));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	@Override
	public Booking readBookingById(Long bookingId, EntityRequester entityRequester) {
		try {
			return remoteHandler.readById(HttpRequestBuilder.forEntity(Booking.class).identified(bookingId));
		} catch (EntityRequestException e) {
			entityRequester.handleRequestException(e);
			return null;
		}
	}

	@Override
	public HttpPutResult putTradingPartner(TradingPartner aTradingPartner) {
		String url = HttpRequestBuilder.forEntity(TradingPartner.class).buildRequestUrl();
		return remoteHandler.putEntity(url, aTradingPartner);
	}

	@Override
	public HttpPutResult putPurposeCategory(PurposeCategory aPurposeCategory) {
		String url = HttpRequestBuilder.forEntity(PurposeCategory.class).buildRequestUrl();
		return remoteHandler.putEntity(url, aPurposeCategory);		
	}

	@Override
	public HttpPutResult putCreditInstitute(CreditInstitute aCreditInstitute) {
		String url = HttpRequestBuilder.forEntity(CreditInstitute.class).buildRequestUrl();
		return remoteHandler.putEntity(url, aCreditInstitute);		
	}
	
	private void checkEntityRequester(EntityRequester entityRequester) {
		if (entityRequester == null) {
			throw new IllegalArgumentException("entity requester must not be NULL!!!");
		}
	}
}