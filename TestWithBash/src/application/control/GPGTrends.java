package application.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class GPGTrends {

	public static GPRecord createRecordFromTable(GPRecord re) {

		GPRecord tmp = new GPRecord(re);

		for (int i = 0; i < re.getListOfSTerm().size(); i++) {
			System.out.print(tmp.getListOfSTerm().get(i).getName() + " ");

		}
		System.out.println();
		Scanner sc = null;
		try {
			Collections.sort(re.getListOfSTerm(), new Comparator<GPSearchterm>() {
				@Override
				public int compare(GPSearchterm s1, GPSearchterm s2) {
					return s1.getName().compareToIgnoreCase(s2.getName());
				}
			});
			sc = new Scanner(new File("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\table.txt"));
			sc.nextLine();
			sc.nextLine();
			for (int i = 0; i < re.getListOfSTerm().size(); i++) {
				System.out.print(tmp.getListOfSTerm().get(i).getName() + " ");

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

		for (int i = 0; i < re.getListOfSTerm().size(); i++) {
			for (int l = 0; l < re.getListOfSTerm().size(); l++) {
				if (tmp.getListOfSTerm().get(i).getName().equals(re.getListOfSTerm().get(l).getName())) {

					tmp.getListOfSTerm().get(i)
							.setDateListFromSearchterm(re.getListOfSTerm().get(l).getDateListFromSearchterm());
				}
			}
		}

		return tmp;

	}

}
