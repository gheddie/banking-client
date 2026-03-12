package de.gravitex.banking.client.gui.logic.bookingoverview;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.view.BookingView;

public class BookingOverviewFromSelectedModel extends BookingOverviewModel {

	public BookingOverviewFromSelectedModel(BookingView aReferenceBooking) {
		super(aReferenceBooking);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<BookingView> filterBookings() {
		List<BookingView> aBookingViewsForAccount = (List<BookingView>) ApplicationRegistry.getInstance()
				.getBankingAccessor().readBookingViewsByAccount(getAccount()).getEntityList();
		List<BookingView> result = new ArrayList<>();
		for (BookingView aBookingView : aBookingViewsForAccount) {
			if (aBookingView.getBookingDate().isAfter(getReferenceBooking().getBookingDate())
					|| aBookingView.getBookingDate().equals(getReferenceBooking().getBookingDate())) {
				result.add(aBookingView);
			}
		}
		return result;
	}

	@Override
	public String buildTitle() {
		return "Übersicht Konto " + getAccount().getName() + " ab dem "
				+ dateFormatter.format(getReferenceBooking().getBookingDate()) + ", Summe (total): " + sumTotal()
				+ " Euro";
	}
}