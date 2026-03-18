package de.gravitex.banking.client.gui.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.BookingOverviewDialog;
import de.gravitex.banking.client.gui.logic.bookingoverview.BookingOverviewFromSelectedModel;
import de.gravitex.banking_core.entity.view.BookingView;

public class CreateBookingOverviewFromBookingTableContextAction extends TableContextAction<BookingView> {
	
	private Logger logger = LoggerFactory.getLogger(CreateBookingOverviewFromBookingTableContextAction.class);
	
	private BookingView referenceBooking;

	public CreateBookingOverviewFromBookingTableContextAction(ActionProvider tActionProvider) {
		super("Übersicht erstellen (ab Buchung)", tActionProvider);
	}

	@Override
	protected void executeAction(BookingView contextObject) {
		logger.info("creating booking overview for account {" + contextObject.getAccountId() + "} from ["
				+ contextObject.getBookingDate() + "]");
		referenceBooking = contextObject;
		createOverview();
	}

	private void createOverview() {
		new BookingOverviewDialog(new BookingOverviewFromSelectedModel(referenceBooking)).setVisible(true);
	}
}