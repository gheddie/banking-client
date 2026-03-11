package de.gravitex.banking.client.gui.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.BookingImportResultDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking_core.controller.bookingimport.ImportBookings;

public class ImportBookingsForAccountAction extends TableContextAction<Account> {
	
	private Logger logger = LoggerFactory.getLogger(ImportBookingsForAccountAction.class);

	public ImportBookingsForAccountAction(ActionProvider tActionProvider) {
		super("Buchungen importieren", tActionProvider);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {

	}

	@Override
	protected void executeAction(Account account) {
		ImportBookings imported = (ImportBookings) ApplicationRegistry.getInstance().getBankingAccessor()
				.importBookings(account).getEntity();
		new BookingImportResultDialog(imported).setVisible(true);
	}
}