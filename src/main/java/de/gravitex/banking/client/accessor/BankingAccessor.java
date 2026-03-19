package de.gravitex.banking.client.accessor;

import java.time.LocalDate;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.Booking;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.StandingOrder;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking_core.dto.BookingImportSummary;
import de.gravitex.banking_core.dto.BookingOverview;
import de.gravitex.banking_core.dto.BookingProgress;
import de.gravitex.banking_core.dto.ImportBookings;
import de.gravitex.banking_core.dto.ImportFileBookings;
import de.gravitex.banking_core.dto.MergeTradingPartners;
import de.gravitex.banking_core.dto.TradingPartnersMergeResult;
import de.gravitex.banking_core.dto.UnprocessedBookingImport;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingAccessor implements IBankingAccessor {

	private IHttpRemoteHandler remoteHandler;

	public BankingAccessor() {
		super();
		remoteHandler = new HttpRemoteHandler();
	}

	public HttpGetResult readTradingPartners() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(TradingPartner.class));
	}

	public HttpGetResult readBookingViewsByAccount(Account account) {
		return remoteHandler.readEntityList(
				HttpRequestBuilder.forEntityList(BookingView.class).byAttribute("account").identified(account.getId()));
	}

	public HttpGetResult readBookingViewsByTradingPartner(TradingPartner aTradingPartner) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(BookingView.class)
				.byAttribute("tradingpartner").identified(aTradingPartner.getId()));
	}

	public HttpGetResult readAccounts(CreditInstitute creditInstitute) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Account.class)
				.byAttribute("creditInstitute").identified(creditInstitute.getId()));
	}

	public HttpGetResult readStandingOrders() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(StandingOrder.class));
	}

	public HttpGetResult readBookings() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Booking.class));
	}

	@Override
	public HttpDeleteResult deleteEntity(IdEntity aEntity) {
		return remoteHandler.deleteEntity(HttpRequestBuilder.forEntity(aEntity.getClass()).identified(aEntity.getId()),
				aEntity);
	}

	@Override
	public HttpGetResult readAccountById(Long accountId) {
		return remoteHandler.readById(HttpRequestBuilder.forEntity(Account.class).identified(accountId));
	}

	@Override
	public HttpGetResult readBookingById(Long bookingId) {
		return remoteHandler.readById(HttpRequestBuilder.forEntity(Booking.class).identified(bookingId));
	}

	@Override
	public HttpPutResult putEntity(IdEntity aEntity) {
		return remoteHandler.putEntity(HttpRequestBuilder.forEntity(aEntity.getClass()), aEntity);
	}

	@Override
	public HttpGetResult importBookings(Account account) {
		return remoteHandler.readEntity(
				HttpRequestBuilder.forEntity(ImportBookings.class).identified(account.getId(), "accountId"),
				ImportBookings.class);
	}

	@Override
	public HttpGetResult findAllEntities(Class<? extends IdEntity> aEntityClass) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(aEntityClass));
	}
	
	@Override
	public HttpPostResult mergeTradingPartners(MergeTradingPartners aMergeTradingPartners) {
		return remoteHandler.post(HttpRequestBuilder.forEntity(MergeTradingPartners.class), aMergeTradingPartners,
				TradingPartnersMergeResult.class);
	}

	@Override
	public HttpPostResult createBookingProgress(LocalDate from, LocalDate to, List<TradingPartner> aTradingPartners) {
		BookingProgress aBookingProgressRequestBody = new BookingProgress();
		aBookingProgressRequestBody.setStartDate(from);
		aBookingProgressRequestBody.setEndDate(to);
		aBookingProgressRequestBody.setTradingPartners(aTradingPartners);
		return remoteHandler.post(HttpRequestBuilder.forEntity(BookingProgress.class), aBookingProgressRequestBody,
				BookingProgress.class);
	}

	@Override
	public HttpGetResult readUnprocessedBookingImports(Account account) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forDtoList(UnprocessedBookingImport.class).identified(account.getId(), "accountId"));
	}

	@Override
	public HttpGetResult importBookingFile(Account account, String aBookingFileName) {
		return remoteHandler.readEntity(HttpRequestBuilder.forDto(ImportFileBookings.class).identified(account.getId(), "accountId").withPathVariable("fileName", aBookingFileName), BookingImportSummary.class);
	}

	public HttpGetResult readCreditInstitutes() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(CreditInstitute.class));
	}

	@Override
	public HttpPostResult createBookingOverview(Account account, LocalDate from, LocalDate to) {
		BookingOverview requestBody = new BookingOverview();
		requestBody.setAccount(account);
		requestBody.setFromDate(from);
		requestBody.setUntilDate(to);
		return remoteHandler.post(HttpRequestBuilder.forEntity(BookingOverview.class), requestBody,
				BookingOverview.class);
	}

	@Override
	public HttpPatchResult patchEntity(IdEntity aEntity) {
		return remoteHandler.patchEntity(HttpRequestBuilder.forEntity(aEntity.getClass()), aEntity);
	}
	
	@Override
	public HttpGetResult readEntityList(Class<?> aEntityClass) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(aEntityClass));
	}

	@Override
	public HttpGetResult readEntity(Class<?> aEntityClass) {
		return remoteHandler.readEntity(HttpRequestBuilder.forEntity(aEntityClass), aEntityClass);
	}
}