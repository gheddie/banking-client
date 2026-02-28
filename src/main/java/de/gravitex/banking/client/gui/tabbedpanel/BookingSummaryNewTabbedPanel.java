package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.LayoutManager;

import de.gravitex.banking.client.gui.dialog.selectentity.SelectAccountDialog;
import de.gravitex.banking.client.gui.dialog.selectentity.SelectBookingDialog;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.view.BookingView;

public class BookingSummaryNewTabbedPanel extends TabbedPanel {

	private static final long serialVersionUID = 2993676746185254575L;
	
	private CreditInstitute creditInstitute;

	private Account selectedAccount;

	private BookingView selectedStartBooking;

	@Override
	protected void putListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	protected LayoutManager getPanelLayout() {
		return null;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		if (aContextEntity instanceof CreditInstitute) {
			creditInstitute = (CreditInstitute) aContextEntity;
			selectAccount();
			selectBooking();
			int werner = 5;
		} else {
			ApplicationRegistry.getInstance().getInteractionHandler().confirm("Bitte in Kredit-Institut wählen!", true,
					getParent());
		}
	}

	private void selectBooking() {
		SelectBookingDialog selectBooking = new SelectBookingDialog(selectedAccount,
				ApplicationRegistry.getInstance().getParentView());
		selectedStartBooking = selectBooking.getSelectedEntity();
	}

	private void selectAccount() {
		SelectAccountDialog selectAccount = new SelectAccountDialog(creditInstitute,
				ApplicationRegistry.getInstance().getParentView());
		selectedAccount = selectAccount.getSelectedEntity();
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}
}