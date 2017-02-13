package application.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class GPGTrends {

	public static Record parsDataFromJavaIntoRecord(Record re) {
		
		Record tmp=new Record(re);
		
		for( int i = 0; i < re.getListOfSTerm().size(); i++){
			System.out.print(tmp.getListOfSTerm().get(i).getName()+" ");
	
			
		}
		System.out.println();
		Scanner sc = null;
		try {
			Collections.sort(re.getListOfSTerm(), new Comparator<Searchterm>() {
				@Override
				public int compare(Searchterm s1, Searchterm s2) {
					return s1.getName().compareToIgnoreCase(s2.getName());
				}
			});
			sc = new Scanner(new File("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\table.txt"));
			sc.nextLine();
			sc.nextLine();
			for( int i = 0; i < re.getListOfSTerm().size(); i++){
				System.out.print(tmp.getListOfSTerm().get(i).getName()+" ");
		
				
			}
			System.out.println();
			while (sc.hasNext()) {


				String sdate = sc.next();
				String year = "";
				String month = "";
				String day = "";
				for (int i = 0; i < sdate.length(); i++) {
					if (i < 4) {
						year += sdate.charAt(i);
					} else if ((i == 5) || (i == 6)) {
						month += sdate.charAt(i);
					} else if ((i == 8) || (i == 9)) {
						day += sdate.charAt(i);
					}

				}

				Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1,
						Integer.parseInt(day));

				for (int i = 0; i < re.getListOfSTerm().size(); i++) {

					try {
						re.getListOfSTerm().get(i).addDateAndPopularity(calendar, Double.parseDouble(sc.next()));

					} catch (NumberFormatException e) {
						re.getListOfSTerm().get(i).addDateAndPopularity(calendar, 0.0);
					}
				}

			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		sc.close();
		
		for( int i = 0; i < re.getListOfSTerm().size(); i++){
			System.out.print(tmp.getListOfSTerm().get(i).getName()+" ");
	
			
		}
		System.out.println();
		
		
		
		for( int i = 0; i < re.getListOfSTerm().size(); i++){
			for( int l = 0; l < re.getListOfSTerm().size(); l++){
	//			System.out.println(tmp.getListOfSTerm().get(i).getName() +" == "+ re.getListOfSTerm().get(l).getName());
			if(tmp.getListOfSTerm().get(i).getName().equals(re.getListOfSTerm().get(l).getName())){
				
				tmp.getListOfSTerm().get(i).setDateListFromSearchterm(re.getListOfSTerm().get(l).getDateListFromSearchterm());
	//			System.out.println("GEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
			}
			}
	}
		
		for( int i = 0; i < re.getListOfSTerm().size(); i++){
			System.out.print(tmp.getListOfSTerm().get(i).getName()+" ");
	
			
		}
		System.out.println();
		for( int i = 0; i < re.getListOfSTerm().size(); i++){
	
			System.out.print(re.getListOfSTerm().get(i).getName()+" ");
			
		}

		return tmp;

	}
	

}
