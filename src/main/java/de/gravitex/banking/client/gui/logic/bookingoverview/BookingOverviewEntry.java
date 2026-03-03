package de.gravitex.banking.client.gui.logic.bookingoverview;

import java.math.BigDecimal;
import java.util.List;

import de.gravitex.banking_core.entity.view.BookingView;

public class BookingOverviewEntry {

	private String purposeKey;
	
	private List<BookingView> bookingViews;

	public BookingOverviewEntry(String aPurposeKey, List<BookingView> aBookingViews) {
		super();
		this.purposeKey = aPurposeKey;
		this.bookingViews = aBookingViews;
	}
	
	public String getPurposeKey() {
		return purposeKey;
	}
	
	public List<BookingView> getBookingViews() {
		return bookingViews;
	}

	public BigDecimal sumAmount() {
		BigDecimal sum = BigDecimal.ZERO;
		for (BookingView aBookingView : bookingViews) {
			sum = sum.add(aBookingView.getAmount());
		}
		return sum;
	}

	public boolean hasPositiveAmout() {
		return sumAmount().compareTo(BigDecimal.ZERO) >= 0;
	}
}