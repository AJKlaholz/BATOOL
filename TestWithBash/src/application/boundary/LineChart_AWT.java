package application.boundary;

import org.jfree.chart.ChartPanel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.ui.ApplicationFrame;
import application.control.Excel;
import application.control.GPGTrends;
import application.control.GPRecordManager;
import application.control.Product;
import application.control.Record;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/*
public static double getCorrelation(java.lang.Number[] data1,
                                    java.lang.Number[] data2)
Calculates the correlation between two datasets. Both arrays should contain the same number of items. Null values are treated as zero.
Information about the correlation calculation was obtained from: http://trochim.human.cornell.edu/kb/statcorr.htm

Parameters:
data1 - the first dataset.
data2 - the second dataset.
Returns:
The correlation.






EXPORT TO EXEL: www.jfree.org/forum/viewtopic.php?f=3&t=4607




*/
public class LineChart_AWT extends ApplicationFrame {
	GPRecordManager rm = new GPRecordManager();

	public LineChart_AWT(String applicationTitle, String chartTitle, String r, OpenFile of, JColorComboBox[] box) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createTimeSeriesChart(chartTitle, "time interval", "popularity",
				createDataset(GPGTrends.parsDataFromJavaIntoRecord(rm.getRecord(r)), Excel.ExceltoJava(of)), true, true,
				false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1500, 1000));
		chartPanel.setMouseZoomable(true, false);

		final XYPlot plot = lineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
		for (int i = 0; i < rm.getRecord(r).getListOfSTerm().size(); i++) {
			renderer.setSeriesPaint(i, box[i].getSelectedColor());
		}
		renderer.setSeriesPaint(rm.getRecord(r).getListOfSTerm().size(), box[box.length - 1].getSelectedColor());
		// renderer.setSeriesPaint(rm.getRecord(r).getListOfSTerm().size(),box[rm.getRecord(r).getListOfSTerm().size()].getSelectedColor());

		System.out.println(box.length);
		plot.setRenderer(renderer);
		setContentPane(chartPanel);

	}

	public TimeSeriesCollection createDataset(Record gt, Product product2) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i = 0; i < gt.getListOfSTerm().size(); i++) {
			TimeSeries xys = new TimeSeries(gt.getListOfSTerm().get(i).getName());
			ArrayList<Double> a = new ArrayList<Double>();
			ArrayList<Double> b = new ArrayList<Double>();
			for (Entry<Calendar, Double> entry : gt.getListOfSTerm().get(i).getDateListFromSearchterm().entrySet()) {

				for (Entry<Calendar, Double> product : product2.getOrderDRequest().entrySet()) {

					if (entry.getKey().equals(product.getKey())) {
						a.add(entry.getValue());
						b.add(product.getValue());

					}
				}

				xys.addOrUpdate((new Day(entry.getKey().get(Calendar.DAY_OF_MONTH),
						(entry.getKey().get(Calendar.MONTH)) + 1, entry.getKey().get(Calendar.YEAR))),
						entry.getValue());

			}

			xys.setKey(gt.getListOfSTerm().get(i).getName() + "("
					+ roundScale2(Correlation(a.toArray(new Double[a.size()]), b.toArray(new Double[a.size()]))) + ")");
			dataset.addSeries(xys);

		}
		// TimeSeries pro = new TimeSeries(Excel.ExceltoJava().getName());
		TimeSeries pro = new TimeSeries(product2.getName());
		for (Entry<Calendar, Double> entry : product2.getOrderDRequest().entrySet()) {
			pro.addOrUpdate((new Day(entry.getKey().get(Calendar.DAY_OF_MONTH),
					(entry.getKey().get(Calendar.MONTH)) + 1, entry.getKey().get(Calendar.YEAR))), entry.getValue());
		}
		dataset.addSeries(pro);

		return dataset;
	}

	public static double Correlation(Double[] doubles, Double[] doubles2) {
		// TODO: check here that arrays are not null, of the same length etc

		double sx = 0.0;
		double sy = 0.0;
		double sxx = 0.0;
		double syy = 0.0;
		double sxy = 0.0;

		int n = doubles.length;

		for (int i = 0; i < n; ++i) {
			double x = doubles[i];
			double y = doubles2[i];

			sx += x;
			sy += y;
			sxx += x * x;
			syy += y * y;
			sxy += x * y;
		}

		// covariation
		double cov = sxy / n - sx * sy / n / n;
		// standard error of x
		double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
		// standard error of y
		double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

		// correlation is just a normalized covariation
		return cov / sigmax / sigmay;
	}

	static double roundScale2(double d) {
		return Math.rint(d * 1000) / 1000.;
	}

}