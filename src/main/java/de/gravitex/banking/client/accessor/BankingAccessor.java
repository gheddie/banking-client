package de.gravitex.banking.client.accessor;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.util.EntityRequester;
import de.gravitex.banking.client.exception.EntityRequestException;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.Booking;
import de.gravitex.banking.entity.BudgetPlanning;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.RecurringPosition;
import de.gravitex.banking.entity.StandingOrder;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking_core.controller.admin.BookingAdminData;
import de.gravitex.banking_core.controller.bookingimport.ImportBookings;
import de.gravitex.banking_core.dto.AccountInfo;
import de.gravitex.banking_core.dto.MergeTradingPartners;
import de.gravitex.banking_core.dto.TradingPartnersMergeResult;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingAccessor implements IBankingAccessor {

	private IHttpRemoteHandler remoteHandler;

	public BankingAccessor() {
		super();
		remoteHandler = new HttpRemoteHandler();
	}

	public HttpGetResult readCreditInstitutes(EntityRequester entityRequester) {
		checkEntityRequester(entityRequester);
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(CreditInstitute.class));
	}

	@SuppressWarnings("unchecked")
	public HttpGetResult readTradingPartners(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(TradingPartner.class));
	}

	public HttpGetResult readBookingViewsByAccount(Account account, EntityRequester entityRequester) {
		return remoteHandler.readEntityList(
				HttpRequestBuilder.forEntityList(BookingView.class).byAttribute("account").identified(account.getId()));
	}

	public HttpGetResult readBookingViewsByTradingPartner(TradingPartner aTradingPartner, EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(BookingView.class)
				.byAttribute("tradingpartner").identified(aTradingPartner.getId()));
	}

	public HttpGetResult readAccounts(CreditInstitute creditInstitute, EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Account.class)
				.byAttribute("creditInstitute").identified(creditInstitute.getId()));
	}

	public HttpGetResult readStandingOrders(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(StandingOrder.class));
	}

	public HttpGetResult readBookings(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Booking.class));
	}

	public HttpPatchResult patchCreditInstitute(CreditInstitute aCreditInstitute) {
		String url = HttpRequestBuilder.forEntity(CreditInstitute.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, aCreditInstitute);
	}

	public HttpPatchResult patchTradingPartner(TradingPartner aTradingPartner) {
		String url = HttpRequestBuilder.forEntity(TradingPartner.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, aTradingPartner);
	}

	public HttpGetResult readAdminData(EntityRequester entityRequester) {
		return remoteHandler.readEntity(HttpRequestBuilder.forEntity(BookingAdminData.class), BookingAdminData.class);
	}

	@Override
	public HttpGetResult readAccounts(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Account.class));
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
	public HttpGetResult readAccountById(Long accountId, EntityRequester entityRequester) {
		return remoteHandler.readById(HttpRequestBuilder.forEntity(Account.class).identified(accountId));
	}

	@Override
	public HttpGetResult readBookingById(Long bookingId, EntityRequester entityRequester) {
		return remoteHandler.readById(HttpRequestBuilder.forEntity(Booking.class).identified(bookingId));
	}

	@Override
	public HttpPutResult putTradingPartner(TradingPartner aTradingPartner) {
		String url = HttpRequestBuilder.forEntity(TradingPartner.class).buildRequestUrl();
		return remoteHandler.putEntity(url, aTradingPartner);
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
	
	@Override
	public HttpPutResult putPurposeCategory(PurposeCategory aPurposeCategory) {
		String url = HttpRequestBuilder.forEntity(PurposeCategory.class).buildRequestUrl();
		return remoteHandler.putEntity(url, aPurposeCategory);		
	}

	@Override
	public HttpPutResult putAccount(Account account) {
		String url = HttpRequestBuilder.forEntity(Account.class).buildRequestUrl();
		return remoteHandler.putEntity(url, account);
	}
	
	@Override
	public HttpGetResult readAccountInfos(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forDtoList(AccountInfo.class));
	}
	
	public HttpPatchResult patchBooking(Booking aBooking) {
		String url = HttpRequestBuilder.forEntity(Booking.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, aBooking);
	}

	@Override
	public HttpPatchResult patchPurposeCategory(PurposeCategory aPurposeCategory) {
		String url = HttpRequestBuilder.forEntity(PurposeCategory.class).buildRequestUrl();
		return remoteHandler.patchEntity(url, aPurposeCategory);
	}

	@Override
	public HttpGetResult importBookings(Account account) {
		try {
			HttpRequestBuilder requestBuilder = HttpRequestBuilder.forEntity(ImportBookings.class)
					.identified(account.getId(), "accountId");
			return remoteHandler.readEntity(requestBuilder, ImportBookings.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HttpGetResult readBudgetPlannings(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(BudgetPlanning.class));
	}

	@Override
	public HttpPostResult mergeTradingPartners(MergeTradingPartners aMergeTradingPartners) {
		return remoteHandler.post(HttpRequestBuilder.forEntity(MergeTradingPartners.class), aMergeTradingPartners,
				TradingPartnersMergeResult.class);
	}
	
	@Override
	public HttpGetResult readPurposeCategorys(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(PurposeCategory.class));
	}

	@Override
	public HttpGetResult readRecurringPositions(EntityRequester entityRequester) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(RecurringPosition.class));
	}
}