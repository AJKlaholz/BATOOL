package application.control;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class Product {
	
	private String name;
	private TreeMap<Calendar,Double> orderDRequest;
	
	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TreeMap<Calendar,Double> getOrderDRequest() {
		return orderDRequest;
	}
	public void setOrderDRequest(TreeMap<Calendar,Double> orderRequest) {
		this.orderDRequest = orderRequest;
	}
	
	
	
	

}
