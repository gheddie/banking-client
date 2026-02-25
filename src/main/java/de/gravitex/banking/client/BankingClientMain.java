package de.gravitex.banking.client;

import java.util.List;

import de.gravitex.banking.client.accessor.BankingAccessor;
import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.TradingPartner;

public class BankingClientMain {
	
	public static void main(String[] args) {
		
		runClient();
		
		// testSaveCreditInstitute();
		
		// testSaveBooingWithPurposeCategory();
		
		// testSaveTradingPartnerWithPurposeCategory();
	}
	
	private static void runClient() {
		BankingClient bankingClient = new BankingClient();
		ApplicationRegistry.getInstance().setParentView(bankingClient);
		bankingClient.onStartUp().setVisible(true);
	}
	
	private static void testSaveTradingPartnerWithPurposeCategory() {
		
		IBankingAccessor bankingAccessor = new BankingAccessor();
		
		List<TradingPartner> tp = bankingAccessor.readTradingPartners();
		System.out.println(tp.size());
		
		List<PurposeCategory> pc = bankingAccessor.readPurposeCategorys();
		System.out.println(pc.size());
		
		TradingPartner tradingPartner = tp.get(0);
		PurposeCategory purposeCategory = pc.get(0);
		
		tradingPartner.setPurposeCategory(purposeCategory);		
		bankingAccessor.saveTradingPartner(tradingPartner);
	}

	// fails to date format shit...
	private static void testSaveBooingWithPurposeCategory() {
		
		IBankingAccessor bankingAccessor = new BankingAccessor();
		
		List<Booking> b = bankingAccessor.readBookings();
		System.out.println(b.size());
		
		List<PurposeCategory> pc = bankingAccessor.readPurposeCategorys();
		System.out.println(pc.size());
		
		Booking booking = b.get(0);
		booking.setPurposeCategory(pc.get(0));
		
		bankingAccessor.saveBooking(booking);
	}

	private static void testSaveCreditInstitute() {
		
		IBankingAccessor bankingAccessor = new BankingAccessor();
		List<CreditInstitute> creditInstitutes = bankingAccessor.readCreditInstitutes();
		System.out.println(creditInstitutes.size());
		CreditInstitute c = creditInstitutes.get(0);
		// c.setName(c.getName() + "_MOO");
		bankingAccessor.saveCreditInstitute(c);
	}
}