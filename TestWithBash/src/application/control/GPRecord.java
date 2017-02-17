package application.control;

import java.util.ArrayList;

public class GPRecord implements Cloneable {
	private String name;
	private ArrayList<GPSearchterm> listofsterm;

	public GPRecord() {

	}

	public GPRecord(GPRecord re) {
		this.name = new String(re.name);
		this.listofsterm = new ArrayList<GPSearchterm>(re.listofsterm);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String s) {
		this.name = s;

	}

	public void setListofsterm(ArrayList<GPSearchterm> listofsterm) {
		this.listofsterm = listofsterm;
	}

	public ArrayList<GPSearchterm> getListOfSTerm() {
		return this.listofsterm;

	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
