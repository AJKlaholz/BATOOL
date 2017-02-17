package application.control;

import java.io.File;
import javax.swing.JFileChooser;

public class GPFileManager {

	private File file=null;
	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();

	
	public void setFile(){
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// get the file
			this.file = fileChooser.getSelectedFile();
			System.out.println(file.getName());
		} else {
			sb.append("No file was selected");
		}
	
	}
	public File getFile() throws Exception {
			return this.file;

	}

}
