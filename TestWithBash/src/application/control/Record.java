package application.control;



import java.util.ArrayList;

public class Record {
	private String name;
	private ArrayList<Searchterm> listofsterm;
	
	public String getName() {
		return this.name;
	}
	
	
	public void setName(String s){
		this.name=s;
		
	}

	public void setListofsterm(ArrayList<Searchterm> listofsterm) {
		this.listofsterm = listofsterm;
	}
	
	public ArrayList<Searchterm> getListOfSTerm(){
		return this.listofsterm;
		
	}

}
