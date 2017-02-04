package application.control;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JProgressBar;

import org.joda.time.DateTime;
import org.joda.time.Months;
import application.boundary.Command;

public class PrintTable implements Runnable {
	private ArrayList<String> searchterms;
	private Product p;
	private JProgressBar jpb;

	public PrintTable(ArrayList<String> searchterms, Product p, JProgressBar jpb) {
		this.searchterms = searchterms;
		this.p = p;
		this.jpb = jpb;
	}

	@Override
	public void run() {
		String s = searchterms.get(0);
		int counter = 0;
		int startMonth = p.getOrderDRequest().lastKey().get(Calendar.MONTH) - 1;
		int startYear = p.getOrderDRequest().lastKey().get(Calendar.YEAR);

		for (int i = 1; i < searchterms.size(); i++) {
			s += ", " + searchterms.get(i);
		}
		DateTime start = new DateTime(p.getOrderDRequest().firstKey());
		DateTime end = new DateTime(p.getOrderDRequest().lastKey());
		int anzahlM = Months.monthsBetween(start, end).getMonths();
		System.out.println("Anzahl Monate: " + anzahlM);

		PrintWriter writer;
		try {
			writer = new PrintWriter("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\table.old");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Command c = new Command("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples");
		jpb.setMinimum(counter);
		jpb.setMaximum(anzahlM);
		while (counter <= anzahlM) {
			this.jpb.setValue(counter);
			this.jpb.repaint();
			if (startMonth > 12) {
				startMonth = 1;
				startYear++;
			}
			try {
				if (startMonth < 10) {
					c.exec("py testjava.py 1 \"" + s + "\" 0" + startMonth + "/" + startYear + " >> table.old");
				} else {
					c.exec("py testjava.py 1 \"" + s + "\"  " + startMonth + "/" + startYear + " >> table.old");
				}
				c.exec("removelines > table.txt");
				System.out.println("Tschüss");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startMonth++;
			counter++;
		}
		jpb.setVisible(false);

	}

}
