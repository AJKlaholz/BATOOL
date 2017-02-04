package application.control;

import java.awt.Color;
import java.util.Calendar;
import java.util.TreeMap;

public class Searchterm {
	private String name;
	private TreeMap<Calendar, Double> DateListFromSearchterm = new TreeMap<Calendar, Double>();
	private Color color;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void addDateAndPopularity(Calendar calendar, double pop){
		this.DateListFromSearchterm.put(calendar, pop);
	}
	
	public void setDateListFromSearchterm(TreeMap<Calendar, Double> DateListFromSearchterms){
		this.DateListFromSearchterm=DateListFromSearchterms;
	}
	
	
	public TreeMap<Calendar, Double> getDateListFromSearchterm(){
		return this.DateListFromSearchterm;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
