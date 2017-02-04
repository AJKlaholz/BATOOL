package application.control;
import java.awt.Color;
import java.util.Calendar;
import java.util.TreeMap;

public class Product {
	
	private String name;
	private TreeMap<Calendar,Double> orderDRequest;
	private Color color;
	
	

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
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	

}
