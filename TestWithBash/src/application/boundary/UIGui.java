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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;

import org.jfree.ui.RefineryUtilities;

import application.control.GPExcelJavaMapper;
import application.control.GPGTrends;
import application.control.GPParseDataToInterface;
import application.control.GPRecordManager;
import application.control.GPRecord;
import application.control.GPSearchterm;
import application.control.GPColorComboBox;
import application.control.GPFileManager;
import application.control.GPshowResultInChart;

public class UIGui extends JFrame implements ActionListener {

	private JButton uiSaveB = new JButton();
	private JButton uiLoadB = new JButton();
	private JButton uiDeleteB = new JButton();
	private JButton uiDownloadB = new JButton();
	private JButton uiResultB = new JButton();
	private JButton uiLoadSalesFileB = new JButton();
	private JComboBox<String> cm = new JComboBox<String>();
	private JPanel panel = new JPanel();
	private GPColorComboBox[] box = new GPColorComboBox[7];
	private Process process = null;
	
	GPRecordManager rm = new GPRecordManager();
	final GPFileManager of = new GPFileManager();

	static JTextField[] fieldList = new JTextField[6];
	JLabel[] jl = new JLabel[7];

	public UIGui() {
		this.setTitle("Correlation Tool");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(520, 350));
		this.setLayout(new FlowLayout());

		uiSaveB.setText("Save");
		uiLoadB.setText("Load");
		uiDeleteB.setText("Delete");
		uiDownloadB.setText("Download");
		uiResultB.setText("Result");
		uiLoadSalesFileB.setText("Choose File");

		uiSaveB.setBounds(20, 250, 100, 30);
		uiLoadB.setBounds(140, 250, 100, 30);
		uiDeleteB.setBounds(380, 250, 100, 30);
		uiDownloadB.setBounds(380, 180, 100, 30);
		uiResultB.setBounds(380, 110, 100, 30);
		cm.setBounds(260, 250, 100, 30);
		uiLoadSalesFileB.setBounds(380, 50, 100, 30);

		for (int i = 0; i < rm.getAllRecordnames().size(); i++) {
			cm.addItem(rm.getAllRecordnames().get(i));
		}

		uiSaveB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(!(fieldList[0].getText().equals(""))){
				// INSERT INTO RECORD DATABASE
				GPRecordManager rp = new GPRecordManager();
				GPRecord al = new GPRecord();
				ArrayList<GPSearchterm> ast = new ArrayList<GPSearchterm>();
				al.setName(fieldList[0].getText());
				for (int i = 1; i < fieldList.length; i++) {
					GPSearchterm st = new GPSearchterm();
					st.setName(fieldList[i].getText());
					ast.add(st);
				}

				al.setListofsterm(ast);
				rp.setRecord(al);

				// add Object to JComboBox
				cm.addItem(fieldList[0].getText());

				}else{
					JOptionPane.showMessageDialog(null, "Please enter a record", "Inane error",
							JOptionPane.ERROR_MESSAGE);
					}
				}
		});

/*		uiLoadB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					if (of.getFile() != null) {
						JFrame meinJFrame = new JFrame();
						meinJFrame.setSize(300, 100);
						meinJFrame.setTitle("Loading Data...");
						meinJFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
						meinJFrame.setLocationRelativeTo(null);
						JPanel meinPanel = new JPanel();
						JProgressBar meinLadebalken = new JProgressBar();

						meinLadebalken.setStringPainted(true);

						meinPanel.add(meinLadebalken);

						meinJFrame.add(meinPanel);
						meinJFrame.setVisible(true);
						// Load selected record from JComboBox to JTextField.
						// Don't cast to record. Need to iterate on listofrec to
						// get
						// selected record
						Object selcObj = cm.getSelectedItem();
						String ObjtoString = selcObj.toString();
						GPRecordManager rm = new GPRecordManager();

						GPRecord tmp = rm.getRecord(ObjtoString);

						fieldList[0].setText(tmp.getName());

						for (int i = 1; i < 6; i++) {

							fieldList[i].setText(tmp.getListOfSTerm().get(i - 1).getName());

						}

						ArrayList<String> listtoTb = new ArrayList<String>();
						for (int i = 0; i < 5; i++) {
							listtoTb.add(tmp.getListOfSTerm().get(i).getName());

						}

						Thread t = new Thread(new GPParseDataToInterface(listtoTb, GPExcelJavaMapper.ExceltoJava(of),
								meinLadebalken));
						t.start();
					} else {
						JOptionPane.showMessageDialog(null, "Please select a file", "Inane error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
*/
		
		uiLoadB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					if (of.getFile() != null) {
						final JFrame meinJFrame = new JFrame();
						meinJFrame.setSize(300, 150);
						meinJFrame.setTitle("Loading Data...");
						meinJFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						meinJFrame.setLocationRelativeTo(null);
						JPanel meinPanel = new JPanel();
						final JProgressBar meinLadebalken = new JProgressBar();
						JButton cancel = new JButton();
						cancel.setText("Cancel Process");
						cancel.setBounds(100, 50, 100, 30);
						meinJFrame.add(cancel);
						meinLadebalken.setStringPainted(true);

						meinPanel.add(meinLadebalken);

						meinJFrame.add(meinPanel);
						meinJFrame.setVisible(true);

						// Load selected record from JComboBox to JTextField.
						// Don't cast to record. Need to iterate on listofrec to
						// get
						// selected record
						Object selcObj = cm.getSelectedItem();
						String ObjtoString = selcObj.toString();
						GPRecordManager rm = new GPRecordManager();

						GPRecord tmp = rm.getRecord(ObjtoString);

						fieldList[0].setText(tmp.getName());

						for (int i = 1; i < 6; i++) {

							fieldList[i].setText(tmp.getListOfSTerm().get(i - 1).getName());

						}

						ArrayList<String> listtoTb = new ArrayList<String>();
						for (int i = 0; i < 5; i++) {
							listtoTb.add(tmp.getListOfSTerm().get(i).getName());

						}

						final Thread t = new Thread(new GPParseDataToInterface(listtoTb,
								GPExcelJavaMapper.ExceltoJava(of), meinLadebalken));
						t.start();

						cancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ev) {
								meinLadebalken.setVisible(false);
								while (t.isAlive()) {
								}

								
								for (int i = 0; i < 6; i++) {

									fieldList[i].setText("");

								}

							}
						});
						
					} else {
						JOptionPane.showMessageDialog(null, "Please select a file", "Inane error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			});
		uiDeleteB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				int sel = JOptionPane.showConfirmDialog(
					    null, "Do you want to delete the record  \""+ (String)cm.getSelectedItem() +"\"?",
					    "Delete record", JOptionPane.WARNING_MESSAGE,
					    JOptionPane.YES_NO_OPTION);
				if(sel==0){
				GPRecordManager rm = new GPRecordManager();
				rm.deleteRecord((String) cm.getSelectedItem());
				cm.removeItemAt(cm.getSelectedIndex());
				}
			}
		});

		uiResultB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(!fieldList[0].getText().equals("")&&(!of.equals(null))){
				GPshowResultInChart chart = new GPshowResultInChart("Corelation",
						"correlation between searchterms and product", fieldList[0].getText(), of, box);

				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);

				}else{
					JOptionPane.showMessageDialog(null, "Please select file and record", "Inane error",
							JOptionPane.ERROR_MESSAGE);
					}
				}
		});

		uiDownloadB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(!fieldList[0].getText().equals("")&&(!of.equals(null))){
				GPExcelJavaMapper e = new GPExcelJavaMapper();

				GPRecordManager rm = new GPRecordManager();
				e.saveToExcel(GPGTrends.createRecordFromTable(rm.getRecord(fieldList[0].getText())),
						GPExcelJavaMapper.ExceltoJava(of));

			}else{
				JOptionPane.showMessageDialog(null, "Please select file and record", "Inane error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		uiLoadSalesFileB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					of.setFile();
					jl[jl.length - 1].setText("Product:               " + GPExcelJavaMapper.ExceltoJava(of).getName());
					jl[jl.length - 1].paintImmediately(jl[jl.length - 1].getVisibleRect());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		this.setLayout(null);

		addingTextfieldsandLabels(this, fieldList, jl, box);


		this.add(uiLoadSalesFileB);
		this.add(uiSaveB);
		this.add(uiLoadB);
		this.add(uiDeleteB);
		this.add(uiDownloadB);
		this.add(uiResultB);
		this.add(cm);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public static void main(String[] args) {

		new UIGui();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	// adding an array of JTextFields to a JFrame
	public void addingTextfieldsandLabels(JFrame jf, JTextField[] fieldList, JLabel[] jl, GPColorComboBox[] jcc) {

		GPRecordManager rm = new GPRecordManager();
		new GPExcelJavaMapper();

		Object selcObj = cm.getSelectedItem();
		String ObjtoString = selcObj.toString();

		rm.getRecord(ObjtoString);

		int y = 10;

		for (int i = 0; i < fieldList.length; i++) {
			if (i == 0) {
				jl[i] = new JLabel("Record:");
			} else {
				jl[i] = new JLabel("Searchterm:");
				jcc[i - 1] = new GPColorComboBox();
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
		jcc[jcc.length - 1] = new GPColorComboBox();
		jcc[jcc.length - 1].setSelectedIndex(jcc.length - 1);
		jf.add(jcc[jcc.length - 1]);
		jf.add(jl[jl.length - 1]);
		jl[jl.length - 1].setBounds(10, y, 500, 20);
		jcc[jcc.length - 1].setBounds(250, y, 100, 20);

	}

}
