package de.gravitex.banking.client.gui.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.BookingImportResultDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.Booking;

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
		List<Booking> imported = ApplicationRegistry.getInstance().getBankingAccessor().importBookings(account);
		new BookingImportResultDialog(imported).setVisible(true);
	}
}