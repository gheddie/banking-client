package de.gravitex.banking.client;

import java.util.List;

import de.gravitex.banking.client.accessor.BankingAccessor;
import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.PurposeCategory;

public class BankingClientMain {
	
	public static void main(String[] args) {
		
		// runClient();
		testSaveBooingWithPurposeCategoryWithHttpClient();
	}
	
	private static void testSaveBooingWithPurposeCategoryWithHttpClient() {
		
		IBankingAccessor bankingAccessor = new BankingAccessor();
		
		List<Booking> b = bankingAccessor.readBookings();
		System.out.println(b.size());
		
		List<PurposeCategory> pc = bankingAccessor.readPurposeCategorys();
		System.out.println(pc.size());
		
		Booking booking = b.get(0);
		booking.setPurposeCategory(pc.get(12));
		
		bankingAccessor.saveBooking(booking);		
	}

	private static void runClient() {
		BankingClient bankingClient = new BankingClient();
		ApplicationRegistry.getInstance().setParentView(bankingClient);
		bankingClient.onStartUp().setVisible(true);
	}
	
}