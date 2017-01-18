package application.boundary;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jfree.ui.RefineryUtilities;

import application.control.Excel;
import application.control.GPGTrends;
import application.control.GPRecordManager;
import application.control.PrintTable;
import application.control.Record;
import application.control.Searchterm;

public class MockGUI extends JFrame implements ActionListener {
	Connection dbConnection = null;

	private JButton saveb = new JButton();
	private JButton loadb = new JButton();
	private JButton deleteb = new JButton();
	private JButton downloadb = new JButton();
	private JButton resultb = new JButton();
	private JButton fileChooseb = new JButton();
	private JComboBox<String> cm = new JComboBox<String>();

	static JTextField[] fieldList = new JTextField[6];
	JLabel[] jl = new JLabel[6];

	public MockGUI() {
		this.setTitle("Mockup GUI");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800, 400));
		this.setLayout(new FlowLayout());

		saveb.setText("Save");
		loadb.setText("Load");
		deleteb.setText("Delete");
		downloadb.setText("Download");
		resultb.setText("Result");
		fileChooseb.setText("Datei auswählen");

		saveb.setBounds(20, 300, 100, 30);
		loadb.setBounds(140, 300, 100, 30);
		deleteb.setBounds(380, 300, 100, 30);
		downloadb.setBounds(500, 300, 100, 30);
		resultb.setBounds(620, 300, 100, 30);
		cm.setBounds(260, 300, 100, 30);
		fileChooseb.setBounds(380, 100, 100, 30);

		// adds ALL items from database record to jcombobox
		GPRecordManager rm = new GPRecordManager();

		for (int i = 0; i < rm.getAllRecordnames().size(); i++) {
			cm.addItem(rm.getAllRecordnames().get(i));
		}

		saveb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				// INSERT INTO RECORD DATABASE

				GPRecordManager rp = new GPRecordManager();
				Record al = new Record();
				ArrayList<Searchterm> ast = new ArrayList<Searchterm>();
				al.setName(fieldList[0].getText());
				for (int i = 1; i < fieldList.length; i++) {
					Searchterm st = new Searchterm();
					st.setName(fieldList[i].getText());
					ast.add(st);
					System.out.println("Test");
				}

				al.setListofsterm(ast);
				rp.setRecord(al);

				// add Object to JComboBox
				cm.addItem(fieldList[0].getText());

			}
		});

		loadb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// Load selected record from JComboBox to JTextField.
				// Don't cast to record. Need to iterate on listofrec to get
				// selected record
				Object selcObj = cm.getSelectedItem();
				String ObjtoString = selcObj.toString();
				GPRecordManager rm = new GPRecordManager();

				Record tmp = rm.getRecord(ObjtoString);

				fieldList[0].setText(tmp.getName());

				for (int i = 1; i < 6; i++) {

					fieldList[i].setText(tmp.getListOfSTerm().get(i - 1).getName());

				}

				ArrayList<String> listtoTb = new ArrayList<String>();
				for (int i = 0; i < 5; i++) {
					listtoTb.add(tmp.getListOfSTerm().get(i).getName());

				}
				PrintTable.print(listtoTb);
			}
		});

		deleteb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GPRecordManager rm = new GPRecordManager();
				rm.deleteRecord((String) cm.getSelectedItem());
				cm.removeItemAt(cm.getSelectedIndex());
			}
		});

		resultb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				LineChart_AWT chart = new LineChart_AWT("Corelation", "correlation between searchterms and product",
						fieldList[0].getText());

				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);

			}
		});
		
		
		downloadb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("1111111111111");
		Excel e = new Excel();
		System.out.println("2222222222222222222");
		GPRecordManager rm = new GPRecordManager();
		e.saveToExcel(GPGTrends.parsDataFromJavaIntoRecord(rm.getRecord(fieldList[0].getText())));
		System.out.println("333333333333333");
			}
		});

		fileChooseb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				OpenFile of = new OpenFile();

				try {
					of.PickMe();
					System.out.println(of.PickMe());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		this.setLayout(null);

		addingTextfieldsandLabels(this, fieldList, jl);

		this.add(fileChooseb);
		this.add(saveb);
		this.add(loadb);
		this.add(deleteb);
		this.add(downloadb);
		this.add(resultb);
		this.add(cm);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		
		new MockGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	// adding an array of JTextFields to a JFrame
	public void addingTextfieldsandLabels(JFrame jf, JTextField[] fieldList, JLabel[] jl) {
		int y = 10;

		for (int i = 0; i < fieldList.length; i++) {
			if (i == 0) {
				jl[i] = new JLabel("Record:");
			} else {
				jl[i] = new JLabel("Searchterm:");
			}

			fieldList[i] = new JTextField(30);
			jf.add(fieldList[i]);
			jf.add(jl[i]);
			fieldList[i].setBounds(100, y, 150, 20);
			jl[i].setBounds(10, y, 100, 20);

			y = y + 30;

		}
	}

}
