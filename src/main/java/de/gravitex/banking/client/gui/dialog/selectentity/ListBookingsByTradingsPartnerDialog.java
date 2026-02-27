package de.gravitex.banking.client.gui.dialog.selectentity;

import java.awt.Window;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.exception.BankingException;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.view.BookingView;

public class ListBookingsByTradingsPartnerDialog extends BrowseEntitiesDialog<TradingPartner, BookingView> {

	public ListBookingsByTradingsPartnerDialog(TradingPartner aReferenceObject, Window owner) {
		super(aReferenceObject, owner);
	}

	private static final long serialVersionUID = -6244586446953480938L;

	public void onEntitySelected(Object aEntity) {
		
	}

	public void onEntityDoubeClicked(Object aEntity) {
		
	}

	@Override
	protected List<BookingView> readSelectableEntities() throws BankingException {
		return ApplicationRegistry.getInstance().getBankingAccessor()
				.readBookingViewsByTradingPartner(getReferenceObject());
	}

	@Override
	protected String getTitleDialog() {
		return "Buchungen des Partners";
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