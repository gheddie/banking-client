package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking_core.entity.view.BookingView;
import de.gravitex.banking_core.service.util.LocalDateRange;

public class CreateBookingOverviewActualMonthTableContextAction extends CreateBookingOverviewTableContextAction {

	public CreateBookingOverviewActualMonthTableContextAction(ActionProvider tActionProvider) {
		super("Übersicht erstellen (aktueller Monat)", tActionProvider);
	}

	@Override
	protected LocalDateRange buildTimeRange(BookingView aBookingView) {
		return LocalDateRange.forActualMonth();
	}
}