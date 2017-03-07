package application.control;

import java.awt.Color;
import java.util.Calendar;
import java.util.TreeMap;
//Entitätsklasse für den Suchbegriff
public class GPSearchterm {
	private String name;
	private TreeMap<Calendar, Integer> dateAndValueListFromSearchterm = new TreeMap<Calendar, Integer>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addDateAndPopularity(Calendar calendar, Integer pop) {
		this.dateAndValueListFromSearchterm.put(calendar, pop);
	}

	public void setDateListFromSearchterm(TreeMap<Calendar, Integer> DateListFromSearchterms) {
		this.dateAndValueListFromSearchterm = DateListFromSearchterms;
	}

	public TreeMap<Calendar, Integer> getDateListFromSearchterm() {
		return this.dateAndValueListFromSearchterm;
	}

}
