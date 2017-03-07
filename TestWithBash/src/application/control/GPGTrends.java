package application.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class GPGTrends {
	// Selektiere die Daten aus dem String und speichere sie in ein Record
	public static GPRecord createRecordFromTableFile(GPRecord record) {
		for (int i = 0; i < record.getListOfSTerm().size(); i++) {
			if (record.getListOfSTerm().get(i).getName().equals("")) {
				record.getListOfSTerm().remove(i);
			}
		}

		GPRecord returnRecord = new GPRecord(record);

		Collections.sort(record.getListOfSTerm(), new Comparator<GPSearchterm>() {
			@Override
			public int compare(GPSearchterm s1, GPSearchterm s2) {
				return s1.getName().compareToIgnoreCase(s2.getName());
			}
		});
		Scanner sc = null;
		try {
			sc = new Scanner(new File("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\table.txt"));
			// sc = new Scanner(new File("table.txt"));
			sc.nextLine();
			sc.nextLine();

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

				for (int i = 0; i < record.getListOfSTerm().size(); i++) {

					try {
						record.getListOfSTerm().get(i).addDateAndPopularity(calendar,
								(int) Double.parseDouble(sc.next()));

					} catch (NumberFormatException e) {
						record.getListOfSTerm().get(i).addDateAndPopularity(calendar, 0);
					}
				}

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		sc.close();

		for (int i = 0; i < record.getListOfSTerm().size(); i++) {
			for (int l = 0; l < record.getListOfSTerm().size(); l++) {
				if (returnRecord.getListOfSTerm().get(i).getName().equals(record.getListOfSTerm().get(l).getName())) {

					returnRecord.getListOfSTerm().get(i)
							.setDateListFromSearchterm(record.getListOfSTerm().get(l).getDateListFromSearchterm());
				}
			}
		}

		return returnRecord;

	}

}
