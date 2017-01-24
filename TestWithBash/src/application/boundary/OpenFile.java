package application.boundary;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class OpenFile {

	private File file=null;
	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();

	
	public void setFile(){
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// get the file
			this.file = fileChooser.getSelectedFile();
			System.out.println(file.getName());
			/*
			 * //create a scanner for the file Scanner input = new
			 * Scanner(file);
			 * 
			 * //read text from file while(input.hasNext()){
			 * sb.append(input.nextLine()); sb.append("\n"); }
			 * 
			 * input.close();
			 */
			
		} else {
			sb.append("No file was selected");
		}
	
	}
	public File PickMe() throws Exception {
		System.out.println(file);
			return this.file;

	}

}
