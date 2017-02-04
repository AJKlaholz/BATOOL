package application.control;



import java.util.ArrayList;

public class Record implements Cloneable{
	private String name;
	private ArrayList<Searchterm> listofsterm;
	
	public Record(){
		
	}
	
	public Record(Record re) {
		this.name = new String(re.name);
		this.listofsterm = new ArrayList<Searchterm>(re.listofsterm);
	}


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
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
