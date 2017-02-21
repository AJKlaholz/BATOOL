package application.control;

import org.jfree.chart.ChartPanel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class GPshowResultInChart extends ApplicationFrame {
	GPRecordManager rm = new GPRecordManager();

	public GPshowResultInChart(String applicationTitle, String chartTitle, String r, GPFileManager of, GPColorComboBox[] box) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createTimeSeriesChart(chartTitle, "time", "popularity and order request",
				createDataset(GPGTrends.createRecordFromTable(rm.getRecord(r)), GPExcelJavaMapper.ExceltoJava(of)),
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

	public TimeSeriesCollection createDataset(GPRecord recordInput, GPProduct productInput) {
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i = 0; i < recordInput.getListOfSTerm().size(); i++) {
			TimeSeries xys = new TimeSeries(recordInput.getListOfSTerm().get(i).getName());
			ArrayList<Double> a = new ArrayList<Double>();
			ArrayList<Double> b = new ArrayList<Double>();
			for (Entry<Calendar, Double> record : recordInput.getListOfSTerm().get(i).getDateListFromSearchterm()
					.entrySet()) {

				for (Entry<Calendar, Double> product : productInput.getOrderDRequest().entrySet()) {

					if (record.getKey().equals(product.getKey())) {
						a.add(record.getValue());
						b.add(product.getValue());

					}
				}

				xys.addOrUpdate((new Day(record.getKey().get(Calendar.DAY_OF_MONTH),
						(record.getKey().get(Calendar.MONTH)) + 1, record.getKey().get(Calendar.YEAR))),
						record.getValue());

			}

			xys.setKey(recordInput.getListOfSTerm().get(i).getName() + "("
					+ GPCorrelationCalculater.getCorrelation(a.toArray(new Double[a.size()]), b.toArray(new Double[a.size()]), true) + ")");
			dataset.addSeries(xys);

		}
		double maxProductValue=(Collections.max(productInput.getOrderDRequest().values()));
		double minProductValue=(Collections.min(productInput.getOrderDRequest().values()));
		
		// cell4.setCellFormula(("("+cell3.getNumericCellValue()+"-MIN(F:F))*(1/(MAX(F:F)-MIN(F:F)))*100"));
		TimeSeries pro = new TimeSeries(productInput.getName());
		for (Entry<Calendar, Double> entry : productInput.getOrderDRequest().entrySet()) {
			pro.addOrUpdate((new Day(entry.getKey().get(Calendar.DAY_OF_MONTH),
					(entry.getKey().get(Calendar.MONTH)) + 1, entry.getKey().get(Calendar.YEAR))), (entry.getValue()-minProductValue)*(1/(maxProductValue-minProductValue))*100);
		}
		dataset.addSeries(pro);

		return dataset;
	}

}