package de.gravitex.banking.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.GuiUtil;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewActualMonthTableContextAction;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewFromBookingTableContextAction;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.gui.logic.bookingoverview.BookingOverviewEntry;
import de.gravitex.banking.client.gui.logic.bookingoverview.BookingOverviewModel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.base.NoIdEntity;
import de.gravitex.banking_core.entity.view.BookingView;
import de.gravitex.banking_core.util.StringHelper;

public class BookingOverviewDialog extends JDialog implements ListSelectionListener, EntityTablePanelListener {

	private static final long serialVersionUID = 7336519059141276271L;

	private static final int OFFSET = 20;

	private JList<BookingOverviewEntry> overviewEntryList;

	private BookingOverviewModel bookingOverviewModel;

	private EntityTablePanel bookingTable;

	private BookingView selectedBooking;

	public BookingOverviewDialog(BookingOverviewModel aBookingOverviewModel) {
		super(ApplicationRegistry.getInstance().getParentView());
		this.bookingOverviewModel = aBookingOverviewModel;
		setModal(true);		
		setLocation(ApplicationRegistry.getInstance().getParentView().getX() + OFFSET,
				ApplicationRegistry.getInstance().getParentView().getY() + OFFSET);
		setSize(900, 600);
		setLayout(new BorderLayout());
		overviewEntryList = new JList<BookingOverviewEntry>();
		overviewEntryList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		overviewEntryList.addListSelectionListener(this);
		overviewEntryList.setCellRenderer(new BookingOverviewEntryCellRenderer());
		JPanel nestedOverviewEntryList = GuiUtil.nestComponent(overviewEntryList, "Verwendungszwecke");
		bookingTable = new EntityTablePanel("Buchungen zum Verwendungszweck", this, true, BookingView.class);
		add(GuiUtil.getSplitPane(nestedOverviewEntryList, bookingTable, false), BorderLayout.CENTER);
		fillEntries();
		setTitle(bookingOverviewModel.buildTitle() + " (" + bookingOverviewModel.getFilteredBookings().size()
				+ " Buchungen)");
	}

	private void fillEntries() {
		DefaultListModel<BookingOverviewEntry> model = new DefaultListModel<>();
		for (BookingOverviewEntry aBookingOverviewEntry : bookingOverviewModel.generateEntries()) {
			model.addElement(aBookingOverviewEntry);
		}
		overviewEntryList.setModel(model);
	}

	private class BookingOverviewEntryCellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = -9131118492173494788L;

		private static final String DEFAULT_PURPOSE_KEY = "Nicht zugeordnet";

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object aObject, int index,
				boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, aObject, index, isSelected, cellHasFocus);
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			BookingOverviewEntry bookingOverviewEntry = (BookingOverviewEntry) aObject;
			String purposeKey = bookingOverviewEntry.getPurposeKey();
			if (StringHelper.isBlank(purposeKey)) {
				purposeKey = DEFAULT_PURPOSE_KEY;
			}
			panel.add(new JLabel(bookingOverviewEntry.getBookingViews().size() + " Buchungen, Summe: "
					+ bookingOverviewEntry.sumAmount() + " Euro"), BorderLayout.CENTER);
			panel.setOpaque(true);
			panel.setBackground(getPanelColor(isSelected, bookingOverviewEntry.sumAmount()));
			return GuiUtil.nestComponent(panel, purposeKey);
		}

		private Color getPanelColor(boolean aSelected, BigDecimal aSumAmount) {
			if (aSelected) {
				return Color.LIGHT_GRAY;
			} else {
				if (aSumAmount.equals(BigDecimal.ZERO)) {
					return Color.YELLOW;		
				} else {
					if (aSumAmount.compareTo(BigDecimal.ZERO) > 0) {
						return Color.GREEN;	
					} else {
						return Color.RED;
					}
				}
			}			
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		fillBookings(overviewEntryList.getSelectedValue());
	}

	private void fillBookings(BookingOverviewEntry aBookingOverviewEntry) {
		bookingTable.displayEntities(aBookingOverviewEntry.getBookingViews());
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		selectedBooking = (BookingView) aEntity;
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
		return ApplicationRegistry.getInstance().getBankingAccessor().patchBooking((Booking) aEntity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionFilter getActionFilter() {
		return ActionFilter.forActions(CreateBookingOverviewFromBookingTableContextAction.class,
				CreateBookingOverviewActualMonthTableContextAction.class);
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<? extends NoIdEntity> aEntityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}
}