package de.gravitex.banking.client;

import de.gravitex.banking.client.registry.ApplicationRegistry;

public class BankingClientMain {
	
	public static void main(String[] args) {
		runClient();
	}
	
	private static void runClient() {
		BankingClient bankingClient = new BankingClient();
		ApplicationRegistry.getInstance().setParentView(bankingClient);
		bankingClient.onStartUp().setVisible(true);
	}
	
	/*
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
	*/
}