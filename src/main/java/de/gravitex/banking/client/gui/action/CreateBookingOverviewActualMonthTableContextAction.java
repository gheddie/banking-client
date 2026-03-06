package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.BookingOverviewDialog;
import de.gravitex.banking.client.gui.logic.bookingoverview.BookingOverviewActualMonthModel;
import de.gravitex.banking_core.entity.view.BookingView;

public class CreateBookingOverviewActualMonthTableContextAction extends TableContextAction<BookingView> {

	private BookingView referenceBooking;

	public CreateBookingOverviewActualMonthTableContextAction(ActionProvider tActionProvider) {
		super("Übersicht erstellen (aktueller Monat)", tActionProvider);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void executeAction(BookingView contextObject) {
		referenceBooking = contextObject;
		createOverview();
	}

	private void createOverview() {
		new BookingOverviewDialog(new BookingOverviewActualMonthModel(referenceBooking)).setVisible(true);		
	}
}