package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.ShowBookingCurrentDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking_core.dto.BookingCurrent;

public class ShowBookingCurrentTableContextAction extends TableContextAction<TradingPartner> {

	public ShowBookingCurrentTableContextAction(ActionProvider tActionProvider) {
		super("Zeitlichen Buchungs-Verlauf anzeigen", tActionProvider);
	}

	@Override
	protected void executeAction(TradingPartner aTradingPartner) {
		new ShowBookingCurrentDialog(aTradingPartner,
				(BookingCurrent) ApplicationRegistry.getInstance().getBankingAccessor()
						.createBookingCurrent(aTradingPartner).getResponseObject(),
				ApplicationRegistry.getInstance().getParentView()).setVisible(true);
	}
}