package de.gravitex.banking.client.gui.dialog.selectentity;

import java.awt.Window;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.exception.BankingException;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.view.BookingView;

public class SelectBookingDialog extends BrowseEntitiesDialog<Account, BookingView> {
	
	private static final long serialVersionUID = -7228634929714047125L;

	public SelectBookingDialog(Account account, Window owner) {
		super(account, owner);
	}

	public void onEntityDoubeClicked(Object aEntity) {
		dispose();
	}

	public void onEntitySelected(Object aEntity) {
		setSelectedEntity((BookingView) aEntity);
	}

	@Override
	protected List<BookingView> readSelectableEntities() throws BankingException {
		return ApplicationRegistry.getInstance().getBankingAccessor().readBookingViewsByAccount(getReferenceObject());
	}
	
	@Override
	protected String getTitleDialog() {
		return "Buchungen des Kontos";
	}
	
	@Override
	public Object getSelectedObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		return null;
	}
}