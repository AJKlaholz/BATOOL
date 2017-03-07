package application.control;

import java.util.ArrayList;

import application.entity.RecordToDB;


//Schnittstelle zwischen Geschäftslogik und Persistenzschicht. 
public class GPRecordManager {
		public void setRecord(GPRecord rs){
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(rs.getName());
			for(int i=0;i<rs.getListOfSTerm().size();i++){
				tmp.add(rs.getListOfSTerm().get(i).getName());
			}
			RecordToDB rdb = new RecordToDB();
			rdb.pushRecordToDb(tmp);
			
		}
		public GPRecord getRecord(String s){
			GPRecord lr = new GPRecord();
			ArrayList<GPSearchterm> als = new ArrayList<GPSearchterm>();
			RecordToDB rdb = new RecordToDB();
			ArrayList <String> tmp = new ArrayList <String>(6);
			tmp = rdb.pullRecordFromDb(s);
			if(tmp.size()!=0){
			lr.setName(tmp.get(0));
			}else{
				lr.setName("");
			}
			for(int i=1;i<tmp.size();i++){
				GPSearchterm st = new GPSearchterm();
				st.setName(tmp.get(i));
				als.add(st);
			}
			lr.setListofsterm(als);
			return lr;
			
			
		}
		
		public void deleteRecord(String ds){
			RecordToDB rtdb = new RecordToDB();
			rtdb.deleteRecordFromDb(ds);
		}
		
		public ArrayList<String> getAllRecordnames(){
			RecordToDB rtdb = new RecordToDB();
			
			
			return rtdb.pullAllRecordnamesFromDb();
		}
}
