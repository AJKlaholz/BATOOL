package application.boundary;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class OpenFile {

	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();

	public File PickMe() throws Exception {
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// get the file
			File file = fileChooser.getSelectedFile();
			/*
			 * //create a scanner for the file Scanner input = new
			 * Scanner(file);
			 * 
			 * //read text from file while(input.hasNext()){
			 * sb.append(input.nextLine()); sb.append("\n"); }
			 * 
			 * input.close();
			 */
			return file;
		} else {
			sb.append("No file was selected");
		}
		return null;

	}

}