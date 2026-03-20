package de.gravitex.banking.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import de.gravitex.banking.client.dto.BookingOverviewAdapter;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.GuiUtil;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewActualMonthTableContextAction;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewFromBookingTableContextAction;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.gui.logic.bookingoverview.BookingOverviewEntry;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.Booking;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;
import de.gravitex.banking_core.entity.view.BookingView;
import de.gravitex.banking_core.util.StringHelper;

public class BookingOverviewDialog extends JDialog implements ListSelectionListener, EntityTablePanelListener {

	private static final long serialVersionUID = 7336519059141276271L;

	private static final int OFFSET = 20;

	private JList<BookingOverviewEntry> overviewEntryList;

	private BookingOverviewAdapter bookingOverviewAdapter;

	private EntityTablePanel bookingTable;

	private BookingView selectedBooking;

	public BookingOverviewDialog(BookingOverviewAdapter aBookingOverviewAdapter) {
		super(ApplicationRegistry.getInstance().getParentView());
		this.bookingOverviewAdapter = aBookingOverviewAdapter;
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
		bookingTable = new EntityTablePanel("Buchungen zum Verwendungszweck", this, true, Booking.class);
		add(GuiUtil.getSplitPane(nestedOverviewEntryList, bookingTable, false), BorderLayout.CENTER);
		fillEntries();
		
		setTitle(bookingOverviewAdapter.buildTitle() + " (" + bookingOverviewAdapter.countBookings()
				+ " Buchungen), Saldo total: " + bookingOverviewAdapter.buildSumTotal());
	}

	private void fillEntries() {
		
		DefaultListModel<BookingOverviewEntry> model = new DefaultListModel<>();
		List<BookingOverviewEntry> entries = new ArrayList<>();
		
		for (String aPurposeKey : bookingOverviewAdapter.getAssignedPurposeKeys()) {
			entries.add(new BookingOverviewEntry(aPurposeKey, bookingOverviewAdapter.getBookings(aPurposeKey), bookingOverviewAdapter.getSum(aPurposeKey)));
		}
		
		entries.add(new BookingOverviewEntry("Nicht zugeordnet", bookingOverviewAdapter.getBookings(null), bookingOverviewAdapter.getUnassignedSum()));
		
		Collections.sort(entries, new BookingOverviewEntryComparator());
		
		for (BookingOverviewEntry aBookingOverviewEntry : entries) {
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
			panel.add(new JLabel(bookingOverviewEntry.getBookings().size() + " Buchungen, Summe: "
					+ bookingOverviewEntry.getAmount() + " Euro"), BorderLayout.CENTER);
			panel.setOpaque(true);
			panel.setBackground(getPanelColor(isSelected, bookingOverviewEntry.getAmount()));
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
		bookingTable.displayEntities(aBookingOverviewEntry.getBookings());
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		selectedBooking = (BookingView) aEntity;
	}

	@Override
	public void onEntityDoubleClicked(Object aEntity) {
		// TODO Auto-generated method stub		
	}

	@Override
	public Object getSelectedObject() {
		return selectedBooking;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		return ApplicationRegistry.getInstance().getBankingAccessor().patchEntity((Booking) aEntity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionFilter getActionFilter() {
		return ActionFilter.forActions(CreateBookingOverviewFromBookingTableContextAction.class,
				CreateBookingOverviewActualMonthTableContextAction.class);
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class BookingOverviewEntryComparator implements Comparator<BookingOverviewEntry>  {
		@Override
		public int compare(BookingOverviewEntry o1, BookingOverviewEntry o2) {
			return o1.getAmount().compareTo(o2.getAmount()) * (1);
		}
	}
}