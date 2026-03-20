package de.gravitex.banking.client.gui.action;

import java.time.LocalDate;

import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking_core.entity.view.BookingView;
import de.gravitex.banking_core.service.util.LocalDateRange;

public class CreateBookingOverviewFromBookingTableContextAction extends CreateBookingOverviewTableContextAction {
	
	public CreateBookingOverviewFromBookingTableContextAction(ActionProvider tActionProvider) {
		super("Übersicht erstellen (ab Buchung)", tActionProvider);
	}

	@Override
	protected LocalDateRange buildTimeRange(BookingView aBookingView) {
		return LocalDateRange.forDates(aBookingView.getBookingDate(), LocalDate.now());
	}
}