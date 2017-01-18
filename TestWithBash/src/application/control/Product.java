package application.control;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Product {
	
	private String name;
	private HashMap<Calendar,Double> orderDRequest;
	
	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<Calendar,Double> getOrderDRequest() {
		return orderDRequest;
	}
	public void setOrderDRequest(HashMap<Calendar,Double> orderRequest) {
		this.orderDRequest = orderRequest;
	}
	
	
	
	

}
