package application.control;

import java.util.Calendar;
import java.util.TreeMap;
//Entitätsklasse für die Produktabsatzdaten
public class GPProduct {

	private String name;
	private TreeMap<Calendar, Integer> orderDRequest;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeMap<Calendar, Integer> getOrderDRequest() {
		return orderDRequest;
	}

	public void setOrderDRequest(TreeMap<Calendar, Integer> orderRequest) {
		this.orderDRequest = orderRequest;
	}

}
