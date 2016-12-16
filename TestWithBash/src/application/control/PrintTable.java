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
	
	public static void print(ArrayList <String> searchterms){
	String s = searchterms.get(0);
	
	for(int i=1;i<searchterms.size();i++){
		s+=", "+searchterms.get(i);
	}
	

	Command c = new Command("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples");
	
	try {
		c.exec("py testjava.py 1 \""+s+"\" 01/2015 > table.old");
		/*	for(int i=1;i<=12;i++){
		
		if(i==1){
			
	}else if(i>9){
			c.exec("py testjava.py 1 \""+s+"\" "+ i +"/2014 >> table.old");
		}else{
			c.exec("py testjava.py 1 \""+s+"\" 0"+ i +"/2014 >> table.old");	
		}
		*/
		//}
		c.exec("removelines > table.txt");
		System.out.println("Tschüss");
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*for(int k=1;k<2;k++){
	for(int i=1;i<6;i++){	
	try { 


	       String nameAusgabedatei = "C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\testjava.py"; 
	        File ausgabedatei = new File(nameAusgabedatei); 
	        FileWriter fw = new FileWriter(ausgabedatei,false); //true für append!
	       BufferedWriter bw = new BufferedWriter(fw); 
	        bw.write(""); 
	        bw.close(); 
	      } catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
	System.out.println("Hallo2");
		try(FileWriter fw = new FileWriter("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples\\testjava.py", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println("from pytrends.request import TrendReq \n"+
			    			"import time"+
			    		"\n"+
			    		"google_username = \"aspam.kspam@gmail.com\" \n" +
			    		"google_password = \"Zoxoduvu123\"\n" +
			    		"path = \"\"\n"+
			    		"\n"+
			    		"# connect to Google\n"+
			    		"pytrend = TrendReq(google_username, google_password, custom_useragent='My"+UUID.randomUUID().toString()+"Script')\n"+
			    		"\n"+
			    		"trend_payload = {'q': '"+s+"','date':'0" + i + "/201"+(k+3)+" 1m'}\n"+
			    		"# trend\n"+
			    		"trend = pytrend.trend(trend_payload)\n"+
			    		"#print(trend)\n"+
			    		"df = pytrend.trend(trend_payload, return_type='dataframe')\n"+
			    		"print(df)\n"+
			    		"time.sleep(6)");
			    
			    
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		
			//"trend_payload = {'q': '"+s+"','date':'0"+ 1 +"/2015 1m'}\n"+
		
		
		Command c = new Command("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\examples");
		if(i==1){
		try {
			System.out.println("Hallo");
			c.exec("py testjava.py > table.txt");
			System.out.println("Tschüss");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
		try {
			c.exec("py testjava.py > tabletmp.txt");
			c.exec("More +2 tabletmp.txt >> table.txt");
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		}
		}*/
	}
	
	
}
