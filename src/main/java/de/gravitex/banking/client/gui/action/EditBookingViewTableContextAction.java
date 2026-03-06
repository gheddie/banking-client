package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.view.BookingView;

public class EditBookingViewTableContextAction extends TableContextAction<BookingView> {

	public EditBookingViewTableContextAction(ActionProvider tActionProvider) {
		super("Buchung bearbeiten", tActionProvider);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {
		ensureContextObject(aContextObject);
	}

	@Override
	protected void executeAction(BookingView aBookingView) {
		ApplicationRegistry.getInstance().getCrudHandler().editEntity(getBooking(aBookingView), getActionProvider());
	}

	private Booking getBooking(BookingView aBookingView) {
		Booking booking = ApplicationRegistry.getInstance().getBankingAccessor().readBookingById(aBookingView.getId(),
				null);
		return booking;
	}
}