package de.gravitex.banking.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Window;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking_core.dto.BookingCurrent;
import de.gravitex.banking_core.dto.BookingCurrentItem;

public class ShowBookingCurrentDialog extends JDialog {

	private static final long serialVersionUID = -2092767738191448861L;

	private static final int OFFSET = 20;

	private TradingPartner tradingPartner;

	private BookingCurrent bookingCurrent;

	private JPanel contentPanel;

	public ShowBookingCurrentDialog(TradingPartner aTradingPartner, BookingCurrent aBookingCurrent, Window parent) {

		super(parent);

		this.tradingPartner = aTradingPartner;
		this.bookingCurrent = aBookingCurrent;

		setModal(true);
		setSize(900, 600);

		setTitle("Buchungs-Verlauf für " + tradingPartner.getTradingKey());

		setLocation(parent.getX() + OFFSET, parent.getY() + OFFSET);

		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());

		contentPanel.add(createGraphPanel(), BorderLayout.CENTER);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
	}

	public JPanel createGraphPanel() {

		final JFreeChart chart = createChart();
		final ChartPanel chartPanel = new ChartPanel(chart);
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(chartPanel, BorderLayout.CENTER);
		add(contentPanel, BorderLayout.CENTER);
		return contentPanel;
	}

	private JFreeChart createChart() {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (BookingCurrentItem aItem : bookingCurrent.getItems()) {
			dataset.addValue(aItem.getAmount(), "Buchungen", aItem.getDate().toString());
		}
		final JFreeChart result = ChartFactory.createBarChart("", "Daten", "Betrag", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		return result;
	}
}