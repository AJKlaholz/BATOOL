package application.boundary;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.ui.RefineryUtilities;

import application.control.ExcelJavaMapper;
import application.control.GPGTrends;
import application.control.GPRecordManager;
import application.control.PrintTable;
import application.control.Product;
import application.control.Record;
import application.control.Searchterm;

public class GUI extends JFrame implements ActionListener {

	private JButton saveb = new JButton();
	private JButton loadb = new JButton();
	private JButton deleteb = new JButton();
	private JButton downloadb = new JButton();
	private JButton resultb = new JButton();
	private JButton fileChooseb = new JButton();
	private JComboBox<String> cm = new JComboBox<String>();
	private JPanel panel = new JPanel();
	private JColorComboBox[] box = new JColorComboBox[7];
	GPRecordManager rm = new GPRecordManager();
	final OpenFile of = new OpenFile();

	static JTextField[] fieldList = new JTextField[6];
	JLabel[] jl = new JLabel[7];

	public GUI() {
		this.setTitle("Correlation Tool");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(520, 350));
		this.setLayout(new FlowLayout());

		saveb.setText("Save");
		loadb.setText("Load");
		deleteb.setText("Delete");
		downloadb.setText("Download");
		resultb.setText("Result");
		fileChooseb.setText("Choose File");

		saveb.setBounds(20, 250, 100, 30);
		loadb.setBounds(140, 250, 100, 30);
		deleteb.setBounds(380, 250, 100, 30);
		downloadb.setBounds(380, 180, 100, 30);
		resultb.setBounds(380, 110, 100, 30);
		cm.setBounds(260, 250, 100, 30);
		fileChooseb.setBounds(380, 50, 100, 30);

		// JPanel panel = new JPanel();

		// panel.add(box);
		// panel.setLayout(null);

		// panel.setSize(250, 100);

		// adds ALL items from database record to jcombobox

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
				}

				al.setListofsterm(ast);
				rp.setRecord(al);

				// add Object to JComboBox
				cm.addItem(fieldList[0].getText());

			}
		});

		loadb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JFrame meinJFrame = new JFrame();
				meinJFrame.setSize(300, 100);
				meinJFrame.setLocationRelativeTo(null);
				JPanel meinPanel = new JPanel();
				JProgressBar meinLadebalken = new JProgressBar();

				meinLadebalken.setStringPainted(true);

				meinPanel.add(meinLadebalken);

				meinJFrame.add(meinPanel);
				meinJFrame.setVisible(true);
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

				ExcelJavaMapper e = new ExcelJavaMapper();

				Thread t = new Thread(new PrintTable(listtoTb, e.ExceltoJava(of), meinLadebalken));
				t.start();
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
						fieldList[0].getText(), of, box);

				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);

			}
		});

		downloadb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				ExcelJavaMapper e = new ExcelJavaMapper();

				GPRecordManager rm = new GPRecordManager();
				e.saveToExcel(GPGTrends.parsDataFromJavaIntoRecord(rm.getRecord(fieldList[0].getText())),
						e.ExceltoJava(of));

			}
		});

		fileChooseb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ExcelJavaMapper ec = new ExcelJavaMapper();
				try {
					of.setFile();
					jl[jl.length - 1].setText("Product:               " + 
					ec.ExceltoJava(of).getName());
					jl[jl.length - 1].paintImmediately(jl[jl.length - 1]
							.getVisibleRect());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		this.setLayout(null);

		addingTextfieldsandLabels(this, fieldList, jl, box);

		// this.getContentPane().add(panel);

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

		new GUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	// adding an array of JTextFields to a JFrame
	public void addingTextfieldsandLabels(JFrame jf, JTextField[] fieldList, JLabel[] jl, JColorComboBox[] jcc) {

		GPRecordManager rm = new GPRecordManager();
		ExcelJavaMapper e = new ExcelJavaMapper();

		Object selcObj = cm.getSelectedItem();
		String ObjtoString = selcObj.toString();

		Record tmp = rm.getRecord(ObjtoString);

		int y = 10;

		for (int i = 0; i < fieldList.length; i++) {
			if (i == 0) {
				jl[i] = new JLabel("Record:");
			} else {
				jl[i] = new JLabel("Searchterm:");
				jcc[i - 1] = new JColorComboBox();
				jcc[i - 1].setSelectedIndex(i - 1);
				jf.add(jcc[i - 1]);
				jcc[i - 1].setBounds(250, y, 100, 20);

			}

			fieldList[i] = new JTextField(30);
			jf.add(fieldList[i]);
			jf.add(jl[i]);
			fieldList[i].setBounds(100, y, 150, 20);
			jl[i].setBounds(10, y, 100, 20);

			y = y + 30;

		}
		jl[jl.length - 1] = new JLabel("Product: ");
		jcc[jcc.length - 1] = new JColorComboBox();
		jcc[jcc.length - 1].setSelectedIndex(jcc.length - 1);
		jf.add(jcc[jcc.length - 1]);
		jf.add(jl[jl.length - 1]);
		jl[jl.length - 1].setBounds(10, y, 500, 20);
		jcc[jcc.length - 1].setBounds(250, y, 100, 20);

		// for(int l=0;l< tmp.getListOfSTerm().size();l++){
		// tmp.getListOfSTerm().get(l).setColor(jcc[l].getSelectedColor());
		// }
		// Excel.ExceltoJava(of).setColor(jcc[tmp.getListOfSTerm().size()-1].getBackground());

	}

}
