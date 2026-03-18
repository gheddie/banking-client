package de.gravitex.banking.client.gui.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.selectentity.GenericEntitySelectionDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking_core.controller.bookingimport.UnprocessedBookingImport;
import de.gravitex.banking_core.dto.BookingImportSummary;

public class ImportBookingsForAccountAction extends TableContextAction<Account> {

	private Logger logger = LoggerFactory.getLogger(ImportBookingsForAccountAction.class);

	public ImportBookingsForAccountAction(ActionProvider tActionProvider) {
		super("Buchungen importieren", tActionProvider);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeAction(Account account) {

		String title = "Buchungs-Import für Konto " + account.getName() + " (" + account.getCreditInstitute().getBic()
				+ ")";
		GenericEntitySelectionDialog<UnprocessedBookingImport> dialog = new GenericEntitySelectionDialog<UnprocessedBookingImport>(
				(List<UnprocessedBookingImport>) ApplicationRegistry.getInstance().getBankingAccessor()
						.readUnprocessedBookingImports(account).getEntityList(),
				ApplicationRegistry.getInstance().getParentView(), true, title);
		dialog.setVisible(true);
		BookingImportSummary summary = (BookingImportSummary) ApplicationRegistry.getInstance().getBankingAccessor()
				.importBookingFile(account,
						((UnprocessedBookingImport) dialog.getSelectedEntity()).getBookingFileName())
				.getEntity();
		String message = summary.getImportedBookings().size() + " Buchungen importiert.";
		ApplicationRegistry.getInstance().getInteractionHandler().showMessage(message,
				ApplicationRegistry.getInstance().getParentView());
	}
}