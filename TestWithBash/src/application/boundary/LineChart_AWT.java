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

import application.control.GPGTrends;
import application.control.GPRecordManager;
import application.control.Record;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;	

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


   public TimeSeriesCollection createDataset(Record gt)
   {
	   
	  TimeSeriesCollection  dataset = new TimeSeriesCollection ( );  
     for(int i=0;i<gt.getListOfSTerm().size();i++){
    	 TimeSeries xys = new TimeSeries(gt.getListOfSTerm().get(i).getName()) ; 
     for(Entry<Calendar, Double> entry : gt.getListOfSTerm().get(i).getDateListFromSearchterm().entrySet()){
    // System.out.print(gt.getListOfSTerm().get(i).getName()+": ");
    	
   /* 	 Calendar cal = Calendar.getInstance();
    	 cal.setTime(entry.getKey());
    	 System.out.println(cal.get(Calendar.DAY_OF_MONTH) +" " +cal.get(Calendar.MONTH)+1+" "+cal.get(Calendar.YEAR) +"VAL:" + entry.getValue());
     xys.addOrUpdate((new Day(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR))), entry.getValue());
     
     */
    	 
    	/* Date bb = new Date();
    	Instant in = bb.toInstant();
    	 LocalDate ld = entry.getKey().to*/
    	 
    	
    	 xys.addOrUpdate((new Day(entry.getKey().get(Calendar.DAY_OF_MONTH),(entry.getKey().get(Calendar.MONTH))+1,entry.getKey().get(Calendar.YEAR))), entry.getValue());
    	 
    	 
    	 
     }
     dataset.addSeries(xys);
      
     }
      return dataset;
   }

}