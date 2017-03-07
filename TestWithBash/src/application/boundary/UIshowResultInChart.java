package application.boundary;

import org.jfree.chart.ChartPanel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map.Entry;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import application.control.GPCorrelationCalculater;
import application.control.GPFileManager;
import application.control.GPGTrends;
import application.control.GPProduct;
import application.control.GPRecord;
import application.control.GPRecordManager;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


// Diese Klasse enthält die Logik für die Erstellung der Ausgabe in einem Diagramm

public class UIshowResultInChart extends JFrame {

	private static final long serialVersionUID = -2289933520349453153L;
	GPRecordManager rm = new GPRecordManager();

	public UIshowResultInChart(String applicationTitle, String chartTitle, String r, GPFileManager of,
			UIColorComboBox[] box) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createTimeSeriesChart(chartTitle, "time", "popularity and order request",
				createDataset(GPGTrends.createRecordFromTableFile(rm.getRecord(r)), UIExcelJavaMapper.ExceltoJava(of)),
				true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1500, 1000));
		chartPanel.setMouseZoomable(true, false);

		final XYPlot plot = lineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
		for (int i = 0; i < rm.getRecord(r).getListOfSTerm().size(); i++) {
			renderer.setSeriesPaint(i, box[i].getSelectedColor());
		}
		renderer.setSeriesPaint(rm.getRecord(r).getListOfSTerm().size(), box[box.length - 1].getSelectedColor());
		plot.setRenderer(renderer);
		this.setContentPane(chartPanel);

	}
	//Füllt eine TimeSeriesCollection mit den Suchbegriffs-und Produktdaten
	public TimeSeriesCollection createDataset(GPRecord recordInput, GPProduct productInput) {

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i = 0; i < recordInput.getListOfSTerm().size(); i++) {
			TimeSeries xys = new TimeSeries(recordInput.getListOfSTerm().get(i).getName());
			ArrayList<Integer> corArrRec = new ArrayList<Integer>();
			ArrayList<Integer> corArrPro = new ArrayList<Integer>();
			for (Entry<Calendar, Integer> recordEntry : recordInput.getListOfSTerm().get(i).getDateListFromSearchterm()
					.entrySet()) {

				for (Entry<Calendar, Integer> productEntry : productInput.getOrderDRequest().entrySet()) {

					if (recordEntry.getKey().equals(productEntry.getKey())) {
						corArrRec.add(recordEntry.getValue());
						corArrPro.add(productEntry.getValue());

					}
				}

				xys.addOrUpdate((new Day(recordEntry.getKey().get(Calendar.DAY_OF_MONTH),
						(recordEntry.getKey().get(Calendar.MONTH)) + 1, recordEntry.getKey().get(Calendar.YEAR))),
						recordEntry.getValue());

			}
			xys.setKey(recordInput.getListOfSTerm().get(i).getName() + "(" + GPCorrelationCalculater
					.getCorrelation(corArrRec.toArray(new Integer[corArrRec.size()]), corArrPro.toArray(new Integer[corArrPro.size()])) + ")");
			dataset.addSeries(xys);

		}
		
		//Skalierung des Wertebereichs der Absatzdaten auf 0-100
		double maxProductValue = (Collections.max(productInput.getOrderDRequest().values()));
		double minProductValue = (Collections.min(productInput.getOrderDRequest().values()));

		TimeSeries pro = new TimeSeries(productInput.getName());
		for (Entry<Calendar, Integer> productEntry : productInput.getOrderDRequest().entrySet()) {
			pro.addOrUpdate(
					(new Day(productEntry.getKey().get(Calendar.DAY_OF_MONTH), (productEntry.getKey().get(Calendar.MONTH)) + 1,
							productEntry.getKey().get(Calendar.YEAR))),
					(productEntry.getValue() - minProductValue) * (1 / (maxProductValue - minProductValue)) * 100);
		}
		dataset.addSeries(pro);

		return dataset;
	}

}