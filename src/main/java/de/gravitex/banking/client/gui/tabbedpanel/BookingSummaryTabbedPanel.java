package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import de.gravitex.banking.client.exception.BankingException;
import de.gravitex.banking.client.gui.GuiUtil;
import de.gravitex.banking.client.gui.dialog.selectentity.BrowseEntitiesDialog;
import de.gravitex.banking.client.gui.dialog.selectentity.SelectBookingDialog;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.view.BookingView;
import de.gravitex.banking_core.formatter.DateTimeValueFormatter;
import de.gravitex.banking_core.util.DateUtil;

public class BookingSummaryTabbedPanel extends TabbedPanel implements ActionListener {

	private static final long serialVersionUID = 3954043056790661201L;

	private Account account;

	private JTree overviewTree;

	private JButton createOverview;

	private DateTimeValueFormatter dateFormatter = new DateTimeValueFormatter();

	private BookingSummary bookingSummary;

	private JLabel summaryLabel;

	private JButton expandTable;

	private JButton collapseTable;

	@Override
	protected LayoutManager getPanelLayout() {
		return new BorderLayout();
	}

	@Override
	protected void init() {
		add(makeToolbar(), BorderLayout.NORTH);
		add(getMainPanel(), BorderLayout.CENTER);
	}

	private Component makeToolbar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		expandTable = new JButton("Ausklappen");
		toolBar.add(expandTable);
		collapseTable = new JButton("Einklappen");
		toolBar.add(collapseTable);
		return toolBar;
	}

	private JPanel getMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		summaryLabel = new JLabel();
		panel.add(summaryLabel, BorderLayout.NORTH);
		overviewTree = new JTree();
		overviewTree.setCellRenderer(new BookingSummaryTreeCellRenderer());
		/*
		overviewTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("mouseClicked ["+e.getX()+"|"+e.getY()+"]");	
				}				
			}
		});
		*/
		panel.add(GuiUtil.nestComponent(overviewTree, "Buchungen"), BorderLayout.CENTER);
		createOverview = new JButton("Erstellen");
		panel.add(createOverview, BorderLayout.SOUTH);
		return panel;
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		if (aContextEntity instanceof Account) {
			account = (Account) aContextEntity;
		}
	}

	@Override
	protected void putListeners() {
		createOverview.addActionListener(this);
		expandTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiUtil.expandAllNodes(overviewTree, true);
			}
		});
		collapseTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiUtil.expandAllNodes(overviewTree, false);
			}
		});		
	}

	public void actionPerformed(ActionEvent e) {
		try {
			createOverview();
		} catch (BankingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void createOverview() throws BankingException {
		if (account != null) {
			Map<String, BookingWrapper> map = new HashMap<String, BookingWrapper>();
			List<BookingView> bookings = filterBookings(
					ApplicationRegistry.getInstance().getBankingAccessor().readBookingViewsByAccount(account));
			System.out.println("read " + bookings.size() + " bookings...");
			for (BookingView bookingView : bookings) {
				String purposeKey = bookingView.getPurposeKey();
				if (map.get(purposeKey) == null) {
					map.put(purposeKey, new BookingWrapper(purposeKey));
				}
				map.get(purposeKey).addBooking(bookingView);
			}
			overviewTree.setModel(makeTableModel(map));
		} else {
			ApplicationRegistry.getInstance().getInteractionHandler().showError("Kein Konto gewählt", createOverview);
		}
	}

	private List<BookingView> filterBookings(List<BookingView> aBookingViewsForAccount) {
		BrowseEntitiesDialog<?, ?> dialog = new SelectBookingDialog(account,
				ApplicationRegistry.getInstance().getParentView());
		BookingView startBooking = (BookingView) dialog.getSelectedEntity();
		List<BookingView> result = new ArrayList<BookingView>();
		for (BookingView aBookingView : aBookingViewsForAccount) {
			if (aBookingView.getBookingDate().isAfter(startBooking.getBookingDate())
					|| aBookingView.getBookingDate().equals(startBooking.getBookingDate())) {
				result.add(aBookingView);
			}
		}
		extendTitle("MOO");
		return result;
	}

	private TreeModel makeTableModel(Map<String, BookingWrapper> map) {

		bookingSummary = new BookingSummary();

		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		for (String key : map.keySet()) {
			BookingWrapper wrapper = map.get(key);
			bookingSummary.accept(wrapper);
			DefaultMutableTreeNode purposeRoot = new DefaultMutableTreeNode(wrapper);
			DefaultMutableTreeNode purposeRootPositive = new DefaultMutableTreeNode(
					"Zugänge: " + wrapper.summary(true));
			DefaultMutableTreeNode purposeRootNegative = new DefaultMutableTreeNode(
					"Abgänge: " + wrapper.summary(false));
			for (BookingView bookingView : wrapper.getViewsPositiveAmount()) {
				purposeRootPositive.add(makeBookingNode(bookingView));
			}
			for (BookingView bookingView : wrapper.getViewsNegativeAmount()) {
				purposeRootNegative.add(makeBookingNode(bookingView));
			}
			if (wrapper.hasPositiveBookings()) {
				purposeRoot.add(purposeRootPositive);
			}
			if (wrapper.hasNegativeBookings()) {
				purposeRoot.add(purposeRootNegative);
			}
			root.add(purposeRoot);
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		summaryLabel.setOpaque(true);
		if (bookingSummary.hasNegativeAmount()) {			
			summaryLabel.setBackground(Color.RED);
		} else {
			summaryLabel.setBackground(Color.GREEN);
		}
		summaryLabel.setText(bookingSummary.summary());
		return model;
	}

	private DefaultMutableTreeNode makeBookingNode(BookingView bookingView) {
		DefaultMutableTreeNode bookingNode = new DefaultMutableTreeNode(bookingView);
		return bookingNode;
	}

	private class BookingWrapper {

		private List<BookingView> bookings = new ArrayList<BookingView>();

		private String purposeKey;

		public BookingWrapper(String aPurposeKey) {
			super();
			this.purposeKey = aPurposeKey;
		}

		public boolean hasNegativeBookings() {
			return !getViewsNegativeAmount().isEmpty();
		}

		public boolean hasPositiveBookings() {
			return !getViewsPositiveAmount().isEmpty();
		}

		public String summary(boolean aPositiveAmount) {
			List<BookingView> tmpViews;
			if (aPositiveAmount) {
				tmpViews = getViewsPositiveAmount();
			} else {
				tmpViews = getViewsNegativeAmount();
			}
			return "(" + tmpViews.size() + " Buchungen) --> " + sumAmount(tmpViews);
		}

		private String sumAmount(List<BookingView> aBookings) {
			BigDecimal amount = new BigDecimal(0);
			for (BookingView aBookingView : aBookings) {
				amount = amount.add(aBookingView.getAmount());
			}
			return amount + " Euro";
		}

		public BigDecimal sumAmount() {
			BigDecimal amount = new BigDecimal(0);
			for (BookingView aBookingView : bookings) {
				amount = amount.add(aBookingView.getAmount());
			}
			return amount;
		}

		public void addBooking(BookingView bookingView) {
			bookings.add(bookingView);
		}

		public List<BookingView> getViewsPositiveAmount() {
			List<BookingView> result = new ArrayList<BookingView>();
			for (BookingView bookingView : bookings) {
				if (bookingView.getAmount().compareTo(new BigDecimal(0)) > 0) {
					result.add(bookingView);
				}
			}
			return sortBookingViews(result);
		}

		public List<BookingView> getViewsNegativeAmount() {
			List<BookingView> result = new ArrayList<BookingView>();
			for (BookingView bookingView : bookings) {
				if (bookingView.getAmount().compareTo(new BigDecimal(0)) < 0) {
					result.add(bookingView);
				}
			}
			return sortBookingViews(result);
		}

		private List<BookingView> sortBookingViews(List<BookingView> aBookingViews) {
			Collections.sort(aBookingViews, new BookingViewByDateComparator());
			return aBookingViews;
		}

		public String getPurposeKey() {
			return purposeKey;
		}

		public List<BookingView> getBookings() {
			return bookings;
		}
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}

	private class BookingViewByDateComparator implements Comparator<BookingView> {
		public int compare(BookingView o1, BookingView o2) {
			return o1.getBookingDate().compareTo(o2.getBookingDate()) * (-1);
		}
	}

	private class BookingSummaryTreeCellRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = -5747173264869163340L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			return makeUserObjectPanel(((DefaultMutableTreeNode) value).getUserObject());
		}

		private JPanel makeUserObjectPanel(Object aUserObject) {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			String text = "";
			Color color = Color.WHITE;
			if (aUserObject != null) {
				if (aUserObject instanceof BookingWrapper) {
					BookingWrapper wrapper = (BookingWrapper) aUserObject;
					BigDecimal amount = wrapper.sumAmount();
					if (amount.compareTo(BigDecimal.ZERO) > 0) {
						color = Color.GREEN;
					}
					if (amount.compareTo(BigDecimal.ZERO) < 0) {
						color = Color.RED;
					}
					text = amount + " Euro (" + wrapper.getPurposeKey() + ")";
				} else if (aUserObject instanceof BookingView) {
					BookingView view = (BookingView) aUserObject;
					text = getBookingViewText(view);
				} else if (aUserObject instanceof String) {
					text = (String) aUserObject;
				} else {
					text = "";
				}
			} else {
				text = "";
			}
			panel.setBackground(color);
			JLabel label = new JLabel(text);
			panel.add(label, BorderLayout.CENTER);
			return panel;
		}

		private String getBookingViewText(BookingView view) {
			return "[" + view.getAmount() + "] " + dateFormatter.format(view.getBookingDate()) + " ("
					+ view.getTradingPartnerKey() + ") - " + view.getPurposeOfUse();
		}
	}

	private class BookingSummary {

		private List<BookingView> bookings = new ArrayList<BookingView>();

		private List<Long> bookingsMillis = new ArrayList<Long>();

		public void accept(BookingWrapper wrapper) {
			for (BookingView aBookingView : wrapper.getBookings()) {
				bookings.add(aBookingView);
				bookingsMillis.add(DateUtil.getMilliSeconds(aBookingView.getBookingDate()));
			}
		}

		public boolean hasNegativeAmount() {
			return sumBookings().compareTo(BigDecimal.ZERO) < 0;
		}

		public String summary() {
			Collections.sort(bookingsMillis);
			return bookings.size() + " Buchungen, " + dateFromMillis(bookingsMillis.get(0)) + " bis "
					+ dateFromMillis(bookingsMillis.get(bookingsMillis.size() - 1)) + " (" + sumBookings()
					+ " Euro total)";
		}

		private String dateFromMillis(Long millis) {
			return dateFormatter.format(DateUtil.getLocalDate(millis));
		}

		private BigDecimal sumBookings() {
			BigDecimal sum = BigDecimal.ZERO;
			for (BookingView aBookingView : bookings) {
				sum = sum.add(aBookingView.getAmount());
			}
			return sum;
		}
	}
	
	private class BookingSummaryTreeCellEditor extends DefaultTreeCellEditor {

		public BookingSummaryTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
			super(tree, renderer);
		}
		
		@Override
		public boolean shouldSelectCell(EventObject event) {
			return true;
		}
	}
}