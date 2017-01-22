package application.control;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Searchterm {
	private String name;
	private TreeMap<Calendar, Double> DateListFromSearchterm = new TreeMap<Calendar, Double>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void addDateAndPopularity(Calendar calendar, double pop){
		this.DateListFromSearchterm.put(calendar, pop);
	}
	
	
	public TreeMap<Calendar, Double> getDateListFromSearchterm(){
		return this.DateListFromSearchterm;
	}

}
