package application.control;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Searchterm {
	private String name;
	private HashMap<Calendar, Double> DateListFromSearchterm = new HashMap<Calendar, Double>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void addDateAndPopularity(Calendar calendar, double pop){
		this.DateListFromSearchterm.put(calendar, pop);
	}
	
	
	public Map<Calendar, Double> getDateListFromSearchterm(){
		return this.DateListFromSearchterm;
	}

}
