package de.gravitex.banking.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking_core.entity.view.BookingView;

/**
 * https://stackoverflow.com/questions/36239840/jfreechart-is-not-visible-in-modal-jdialog
 */
public class ShowBookingCurrentDialog extends JDialog {

	private static final long serialVersionUID = -2092767738191448861L;

	private static final int OFFSET = 20;

	private TradingPartner tradingPartner;

	private List<BookingView> bookingViews;

	private JPanel contentPanel;

	public ShowBookingCurrentDialog(TradingPartner aTradingPartner, List<BookingView> aBookingViews, Window parent) {

		super(parent);
		
		this.tradingPartner = aTradingPartner;
		this.bookingViews = aBookingViews;
		
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
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("First");
		series1.add(1.0, 1.0);
		series1.add(2.0, 4.0);
		series1.add(3.0, 3.0);
		series1.add(4.0, 5.0);
		series1.add(5.0, 5.0);
		series1.add(6.0, 7.0);
		series1.add(7.0, 7.0);
		series1.add(8.0, 8.0);
		dataset.addSeries(series1);
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		// chartPanel.setPreferredSize(new Dimension(400, 400));
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());		
		contentPanel.add(chartPanel, BorderLayout.CENTER);
		add(contentPanel, BorderLayout.CENTER);
		
		// contentPanel.revalidate();
		
		return contentPanel;
	}

	private JFreeChart createChart(XYSeriesCollection dataset) {
		final JFreeChart result = ChartFactory.createXYLineChart(null, "X", "Y", dataset, PlotOrientation.VERTICAL,
				true, true, false);
		result.setTitle("Example");
		final XYPlot plot = result.getXYPlot();
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);
		return result;
	}
}