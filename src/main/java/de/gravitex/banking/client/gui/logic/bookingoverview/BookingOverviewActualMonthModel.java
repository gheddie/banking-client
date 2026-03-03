package de.gravitex.banking.client.gui.logic.bookingoverview;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.view.BookingView;

public class BookingOverviewActualMonthModel extends BookingOverviewModel {

	private LocalDate startDay;
	
	private LocalDate endDay;

	public BookingOverviewActualMonthModel(BookingView aReferenceBooking) {
		super(aReferenceBooking);
	}

	@Override
	protected List<BookingView> filterBookings() {		
		List<BookingView> bookingViews = ApplicationRegistry.getInstance().getBankingAccessor()
				.readBookingViewsByAccount(getAccount());
		makeRange();
		List<BookingView> result = new ArrayList<>();
		for (BookingView aBookingView : bookingViews) {
			if (contained(aBookingView)) {
				result.add(aBookingView);
			}
		}
		return result;
	}

	private boolean contained(BookingView aBookingView) {
		LocalDate bookingDate = aBookingView.getBookingDate();
		boolean afterStart = bookingDate.isAfter(startDay) || bookingDate.equals(startDay);
		boolean beforeEnd = bookingDate.isBefore(endDay) || bookingDate.equals(endDay);
		return (afterStart && beforeEnd);
	}

	private void makeRange() {
		
		LocalDate reference = getReferenceBooking().getBookingDate();
		
		startDay = reference.with(TemporalAdjusters.firstDayOfMonth());				
		endDay = reference.with(TemporalAdjusters.lastDayOfMonth());
	}

	@Override
	public String buildTitle() {
		String range = formatRange();
		return "Übersicht Konto " + getAccount().getName() + " für Zeitraum "
				+ range + ", Summe (total): " + sumTotal()
				+ " Euro";
	}

	private String formatRange() {
		return dateFormatter.format(startDay) + "-" + dateFormatter.format(endDay);
	}
}