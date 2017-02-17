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

	public GPParseDataToInterface(ArrayList<String> searchterms, GPProduct p, JProgressBar jpb) {
		this.searchterms = searchterms;
		this.p = p;
		this.jpb = jpb;
	}
/*
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
		int anzahlM = Months.monthsBetween(start, end).getMonths()+1;
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

		setCommand("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples");
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
					exec("py testjava.py \"" + s + "\" 0" + startMonth + "/" + startYear + " >> table.old");
				} else {
					exec("py testjava.py \"" + s + "\"  " + startMonth + "/" + startYear + " >> table.old");
				}
				exec("removelines > table.txt");
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
    public void setCommand(String basedir) { 
        this.basedir = basedir; 
    } 
    public void exec(String command) throws InterruptedException { 
        System.out.println("executing command: " + command); 
        Process p = null; 
        try { 
            p = Runtime.getRuntime().exec( 
                    "cmd /c " //Nur unter Windows notwendig! 
                    + command, null, new File(basedir)); 


        } catch (IOException ex) { 
            ex.printStackTrace(); 
        } 
        System.out.println("OUTPUT"); 
        printStream(p.getInputStream()); 
        System.out.println("ERROR-OUTPUT"); 
        printStream(p.getErrorStream()); 
    } 

   */
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
		int anzahlM = Months.monthsBetween(start, end).getMonths()+1;
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

		setCommand("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples");
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
					exec("py testjava.py \"" + s + "\" 0" + startMonth + "/" + startYear + " >> table.old");
					
				} else {
					exec("py testjava.py \"" + s + "\"  " + startMonth + "/" + startYear + " >> table.old");
				}
				exec("removelines > table.txt");
				System.out.println("Tschüss");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startMonth++;
			counter++;
			if(!jpb.isVisible()){
				System.out.println("TESTESTEST");
				process.destroy();
				break;
		}	
		}
		SwingUtilities.getRoot(jpb).setVisible(false);
	}
    public void setCommand(String basedir) { 
        this.basedir = basedir; 
    } 
    public void exec(String command) throws InterruptedException { 
        System.out.println("executing command: " + command); 
        process = null; 
        try { 
        	process = Runtime.getRuntime().exec( 
                    "cmd /c " //Nur unter Windows notwendig! 
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
        BufferedReader in = new BufferedReader( 
                new InputStreamReader(stream)); 
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
