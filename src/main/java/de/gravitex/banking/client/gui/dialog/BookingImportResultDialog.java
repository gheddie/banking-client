package de.gravitex.banking.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.base.NoIdEntity;

public class BookingImportResultDialog extends JDialog implements EntityTablePanelListener {
	
	private static final long serialVersionUID = -1195569757991743006L;
	
	private static final int OFFSET = 20;

	private List<Booking> importedBookings;

	private EntityTablePanel bookingTable;

	private JButton ok;

	public BookingImportResultDialog(List<Booking> aImportedBookings) {
		super(ApplicationRegistry.getInstance().getParentView());
		this.importedBookings = aImportedBookings;
		setModal(true);
		setLayout(new BorderLayout());
		setLocation(ApplicationRegistry.getInstance().getParentView().getX() + OFFSET,
				ApplicationRegistry.getInstance().getParentView().getY() + OFFSET);
		setSize(900, 600);
		bookingTable = new EntityTablePanel("Importierte Buchungen", this, true, Booking.class);
		add(bookingTable, BorderLayout.CENTER);
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(ok, BorderLayout.SOUTH);
		fillData();
	}

	private void fillData() {
		bookingTable.displayEntities(importedBookings);		
	}

	@Override
	public void onEntitySelected(Object aEntity) {

	}

	@Override
	public void onEntityDoubeClicked(Object aEntity) {

	}

	@Override
	public Object getSelectedObject() {
		return null;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		return null;
	}

	@Override
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		return null;
	}

	@Override
	public ActionFilter getActionFilter() {
		return null;
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass) {
		return null;
	}
}