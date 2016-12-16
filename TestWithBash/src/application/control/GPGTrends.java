package application.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import application.boundary.Command;

public class GPGTrends {

	public static Record parsDataFromJavaIntoRecord(Record re){
		try {
			   Collections.sort(re.getListOfSTerm(), new Comparator<Searchterm>() {
			        @Override
			        public int compare(Searchterm s1, Searchterm s2) {
			            return s1.getName().compareToIgnoreCase(s2.getName());
			        }
			    });
			Scanner sc = new Scanner(new File("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\table.txt"));
			while(sc.hasNext()){
				sc.nextLine();
				sc.nextLine();
				//sc.nextLine();
				String sdate = sc.next();
				String year = "";
				String month = "";
				String day = "";
				for(int i=0;i<sdate.length();i++){
					if(i<4){
						year+=sdate.charAt(i);
					}else if((i==5)||(i==6)){
						month+=sdate.charAt(i);
					}else if((i==8)||(i==9)){
						day+=sdate.charAt(i);
					}
					
					
				}
				
				Calendar calendar =new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
				//calendar.set(year, month,day);
				for(int i=0;i<re.getListOfSTerm().size();i++){
					
					try{re.getListOfSTerm().get(i).addDateAndPopularity(calendar, Double.parseDouble(sc.next()));
					
					}catch(NumberFormatException e){
						re.getListOfSTerm().get(i).addDateAndPopularity(calendar, 0.0);
					}
					}
			 	
		/*		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = format.parse(sc.next());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

				
				
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return re;

		
	}
	
}
