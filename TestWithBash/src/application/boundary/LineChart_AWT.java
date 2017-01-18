package application.boundary;

import org.jfree.chart.ChartPanel;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.joda.time.Instant;
import org.joda.time.LocalDate;

import application.control.Excel;
import application.control.GPGTrends;
import application.control.GPRecordManager;
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
public class LineChart_AWT extends ApplicationFrame
{
	GPRecordManager rm = new GPRecordManager();
	GPGTrends t = new GPGTrends();
   public LineChart_AWT( String applicationTitle , String chartTitle ,String r)
   {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
         chartTitle,
         "time interval","popularity",
         createDataset(GPGTrends.parsDataFromJavaIntoRecord(t.parsDataFromJavaIntoRecord(rm.getRecord(r)))),
         true,true,false);
     
      

         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension(1500,1000 ) );
      chartPanel.setMouseZoomable(true, false);
      setContentPane( chartPanel );
     
   }


   public TimeSeriesCollection createDataset(Record gt){
	   System.out.println("MAP GRÖßE" +gt.getListOfSTerm().get(0).getDateListFromSearchterm().entrySet().size());
	  TimeSeriesCollection  dataset = new TimeSeriesCollection ( );  
     for(int i=0;i<gt.getListOfSTerm().size();i++){
    	 TimeSeries xys = new TimeSeries(gt.getListOfSTerm().get(i).getName()) ; 
     for(Entry<Calendar, Double> entry : gt.getListOfSTerm().get(i).getDateListFromSearchterm().entrySet()){

    	 
    	
    	 xys.addOrUpdate((new Day(entry.getKey().get(Calendar.DAY_OF_MONTH),(entry.getKey().get(Calendar.MONTH))+1,entry.getKey().get(Calendar.YEAR))), entry.getValue());
    	 
    	 
    	 
     }
     dataset.addSeries(xys);
      
     }
     TimeSeries pro = new TimeSeries(Excel.ExceltoJava().getName()) ; 
     for(Entry<Calendar, Double> entry : Excel.ExceltoJava().getOrderDRequest().entrySet()){
    	 pro.addOrUpdate((new Day(entry.getKey().get(Calendar.DAY_OF_MONTH),(entry.getKey().get(Calendar.MONTH))-1,entry.getKey().get(Calendar.YEAR))), entry.getValue());
     }
     dataset.addSeries(pro);
      return dataset;
   }

}