package application.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import application.boundary.Command;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.UUID;

public class PrintTable {

	public static void print(ArrayList<String> searchterms) {
		String s = searchterms.get(0);

		for (int i = 1; i < searchterms.size(); i++) {
			s += ", " + searchterms.get(i);
		}

		Command c = new Command("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples");

		try {
			c.exec("py testjava.py 1 \"" + s + "\" 01/2013 > table.old");
			/*
			 * for(int i=1;i<=12;i++){
			 * 
			 * if(i==1){
			 * c.exec("py testjava.py 1 \""+s+"\" 01/2014 > table.old"); }else
			 * if(i>9){ c.exec("py testjava.py 1 \""+s+"\" "+ i
			 * +"/2014 >> table.old"); }else{
			 * c.exec("py testjava.py 1 \""+s+"\" 0"+ i +"/2014 >> table.old");
			 * }
			 * 
			 * }
			 */
			c.exec("removelines > table.txt");
			System.out.println("Tschüss");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
