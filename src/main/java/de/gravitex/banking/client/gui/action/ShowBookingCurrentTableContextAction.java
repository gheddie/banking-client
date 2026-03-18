package de.gravitex.banking.client.gui.action;

import java.util.List;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.ShowBookingCurrentDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking_core.entity.view.BookingView;

public class ShowBookingCurrentTableContextAction extends TableContextAction<TradingPartner> {

	public ShowBookingCurrentTableContextAction(ActionProvider tActionProvider) {
		super("Zeitlichen Buchungs-Verlauf anzeigen", tActionProvider);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeAction(TradingPartner aTradingPartner) {

		List<BookingView> views = (List<BookingView>) ApplicationRegistry.getInstance().getBankingAccessor()
				.readBookingViewsByTradingPartner(aTradingPartner).getEntityList();
		new ShowBookingCurrentDialog(aTradingPartner, views, ApplicationRegistry.getInstance().getParentView())
				.setVisible(true);
	}
}