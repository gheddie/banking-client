package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.base.IdEntity;

public class BookingListTabbedPanel extends TabbedPanel implements EntityTablePanelListener {

	private static final long serialVersionUID = 730717266407102508L;
	
	private EntityTablePanel bookingTable;

	private Booking selectedBooking;

	@Override
	protected void putListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	protected LayoutManager getPanelLayout() {
		return new BorderLayout();
	}

	@Override
	protected void init() {
		bookingTable = new EntityTablePanel("Buchungen", this, true);
		add(bookingTable, BorderLayout.CENTER);
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		fillData();
	}

	private void fillData() {
		List<Booking> bookings = ApplicationRegistry.getInstance().getBankingAccessor()
				.readBookings();
		bookingTable.displayEntities(bookings);		
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		selectedBooking = (Booking) aEntity;
	}

	@Override
	public void onEntityDoubeClicked(Object aEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSelectedObject() {
		return selectedBooking;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		HttpPatchResult patchResult = ApplicationRegistry.getInstance().getBankingAccessor().saveBooking((Booking) aEntity);
		reload();
		return patchResult;
	}
}
