package de.gravitex.banking.client.accessor;

import java.io.IOException;
import java.util.List;

import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.StandingOrder;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingAccessor implements IBankingAccessor {

	private JsonRemoteHandler remoteHandler;

	public BankingAccessor() {
		super();
		remoteHandler = new JsonRemoteHandler();
	}

	public List<CreditInstitute> readCreditInstitutes() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(CreditInstitute.class));
	}

	public List<TradingPartner> readTradingPartners() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(TradingPartner.class));
	}

	public List<PurposeCategory> readPurposeCategorys() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(PurposeCategory.class));
	}

	public List<BookingView> readBookingViewsByAccount(Account account) {
		return remoteHandler.readEntityList(
				HttpRequestBuilder.forEntityList(BookingView.class).byAttribute("account").identified(account.getId()));
	}

	public List<BookingView> readBookingViewsByTradingPartner(TradingPartner aTradingPartner) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(BookingView.class)
				.byAttribute("tradingpartner").identified(aTradingPartner.getId()));
	}
	
	public List<Account> readAccounts(CreditInstitute creditInstitute) {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(Account.class)
				.byAttribute("creditInstitute").identified(creditInstitute.getId()));
	}
	
	public List<StandingOrder> readStandingOrders() {
		return remoteHandler.readEntityList(HttpRequestBuilder.forEntityList(StandingOrder.class));
	}
	
	public void updateTradingPartner(Long aTradingPartnerId, Long aPurposeCategoryId) {
		System.out.println("updateTradingPartner -->" + aTradingPartnerId + " [" + aPurposeCategoryId + "]");
		try {
			remoteHandler.patch("http://localhost:8080/tradingpartners/" + aTradingPartnerId + "?purposeCategoryId="
					+ aPurposeCategoryId);
			ApplicationRegistry.getInstance().getInteractionHandler().showMessage("Kategorie erfolgreich geändert!!!",
					null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}