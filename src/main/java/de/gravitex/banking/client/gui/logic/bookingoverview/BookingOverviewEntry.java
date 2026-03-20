package de.gravitex.banking.client.gui.logic.bookingoverview;

import java.math.BigDecimal;
import java.util.List;

import de.gravitex.banking.entity.Booking;

public class BookingOverviewEntry {

	private String purposeKey;
	
	private List<Booking> bookings;

	private BigDecimal amount;

	public BookingOverviewEntry(String aPurposeKey, List<Booking> aBookings, BigDecimal tAmount) {
		
		super();
		
		this.purposeKey = aPurposeKey;
		this.bookings = aBookings;
		
		this.amount = tAmount;
	}
	
	public String getPurposeKey() {
		return purposeKey;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public List<Booking> getBookings() {
		return bookings;
	}
}