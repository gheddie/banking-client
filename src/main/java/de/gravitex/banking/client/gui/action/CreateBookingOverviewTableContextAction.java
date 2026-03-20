package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.dto.BookingOverviewAdapter;
import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.BookingOverviewDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking_core.dto.BookingOverview;
import de.gravitex.banking_core.entity.view.BookingView;
import de.gravitex.banking_core.service.util.LocalDateRange;

public abstract class CreateBookingOverviewTableContextAction extends TableContextAction<BookingView> {

	public CreateBookingOverviewTableContextAction(String actionText, ActionProvider tActionProvider) {
		super(actionText, tActionProvider);
	}

	@Override
	protected void executeAction(BookingView aBookingView) {

		Account account = (Account) ApplicationRegistry.getInstance().getBankingAccessor()
				.readEntityById(aBookingView.getAccountId(), Account.class).getEntity();
		LocalDateRange range = buildTimeRange(aBookingView);
		BookingOverview overview = (BookingOverview) ApplicationRegistry.getInstance().getBankingAccessor()
				.createBookingOverview(account, range.getFromDate(), range.getUntilDate()).getResponseObject();
		
		new BookingOverviewDialog(new BookingOverviewAdapter(overview)).setVisible(true);
	}

	protected abstract LocalDateRange buildTimeRange(BookingView aBookingView);
}