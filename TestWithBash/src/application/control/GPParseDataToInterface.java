package application.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.joda.time.Months;

public class GPParseDataToInterface implements Runnable {
	private ArrayList<String> searchterms;
	private GPProduct p;
	private JProgressBar jpb;
	private String basedir;
	private Process process;
	private JFrame jf;

	public GPParseDataToInterface(ArrayList<String> searchterms, GPProduct p, JProgressBar jpb, JFrame jf) {
		this.searchterms = searchterms;
		this.p = p;
		this.jpb = jpb;
		this.jf = jf;
	}
	/* Logik zum Ausführen des Python-Skripts.
	 * Speichern der Ausgabe in table.old.
	 * Führe removelines Skript aus um unnützliche Daten aus table.old zu löschen
	 * Schreibe das Ergebnis in table.txt
	 */
	public void run() {
		
		int idxSearchterm = 0;
		int counter = 0;
		int month = p.getOrderDRequest().lastKey().get(Calendar.MONTH) - 1;
		int startYear = p.getOrderDRequest().lastKey().get(Calendar.YEAR);
		//Löst Zaunphalproblem
		while(searchterms.get(idxSearchterm).equals("")){
			idxSearchterm++;
		}
		String s = searchterms.get(idxSearchterm);
		idxSearchterm++;
		for (int i = idxSearchterm; i < searchterms.size(); i++) {
			if(!searchterms.get(i).equals("")){
			s += ", " + searchterms.get(i);
			}
		}
		DateTime start = new DateTime(p.getOrderDRequest().firstKey());
		DateTime end = new DateTime(p.getOrderDRequest().lastKey());
		int numberOfMonths = Months.monthsBetween(start, end).getMonths() + 1;
		System.out.println("Anzahl Monate: " + numberOfMonths);

		PrintWriter writer;
		try {
			// writer = new PrintWriter("table.old");
			writer = new PrintWriter("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\table.old");

			writer.print("");
			writer.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setCommand("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples");
		// setCommand(".");
		jpb.setMinimum(counter);
		jpb.setMaximum(numberOfMonths);
		while (counter < numberOfMonths) {
			this.jpb.setValue(counter);
			this.jpb.repaint();
			if (month > 12) {
				month = 1;
				startYear++;
			}
			try {
				if (month < 10) {
					exec("py testjava.py \"" + s + "\" 0" + month + "/" + startYear + " >> table.old");

				} else {
					exec("py testjava.py \"" + s + "\"  " + month + "/" + startYear + " >> table.old");
				}
				exec("removelines > table.txt");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			month++;
			counter++;
			if (!jpb.isVisible()) {
				process.destroy();
				break;
			}
		}
		SwingUtilities.getRoot(jpb).setVisible(false);
		jf.setEnabled(true);
	}

	public void setCommand(String basedir) {
		this.basedir = basedir;
	}

	public void exec(String command) throws InterruptedException {
		System.out.println("executing command: " + command);
		process = null;
		try {
			process = Runtime.getRuntime().exec("cmd /c " // Nur unter Windows
															// notwendig!
					+ command, null, new File(basedir));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("OUTPUT");
		printStream(process.getInputStream());
		System.out.println("ERROR-OUTPUT");
		printStream(process.getErrorStream());
	}

	public void printStream(InputStream stream) {
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
