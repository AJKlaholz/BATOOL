package application.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GPExcelJavaMapper {

	public void saveToExcel(GPRecord record, GPProduct product) {
		double[] cor = new double[record.getListOfSTerm().size()];
		java.util.Date now = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
		String ausgabe = sdf.format(now);

		for (int i = 0; i < record.getListOfSTerm().size(); i++) {

			ArrayList<Double> a = new ArrayList<Double>();
			ArrayList<Double> b = new ArrayList<Double>();

			for (Entry<Calendar, Double> entry : record.getListOfSTerm().get(i).getDateListFromSearchterm()
					.entrySet()) {

				for (Entry<Calendar, Double> product2 : product.getOrderDRequest().entrySet()) {

					if (entry.getKey().equals(product2.getKey())) {
						a.add(entry.getValue());
						b.add(product2.getValue());

					}
				}

			}
			cor[i] = ((GPCorrelationCalculater.getCorrelation(a.toArray(new Double[a.size()]), b.toArray(new Double[a.size()]),true)));

		}

		int rowI = 2;

		// Erstellt eine XLSX Datei
		Workbook datei = new XSSFWorkbook();

		Sheet blatt = datei.createSheet(WorkbookUtil.createSafeSheetName("Result"));



		Row reihe = blatt.createRow(0);
		Cell zelle = reihe.createCell(0);
		zelle.setCellValue("Result on " + ausgabe + " with record " + record.getName() + " and product "
				+ product.getName());

		Row reihe2 = blatt.createRow(1);
		Cell zelle2 = reihe2.createCell(0);
		zelle2.setCellValue("Datum");
		for (int i = 1; i <= record.getListOfSTerm().size(); i++) {
			Cell zelle3 = reihe2.createCell(i);
			zelle3.setCellValue(record.getListOfSTerm().get(i - 1).getName() + "(" + cor[i - 1] + ")");
		}
		Cell zelle4 = reihe2.createCell(record.getListOfSTerm().size() + 1);
		zelle4.setCellValue(product.getName());
		// Den Namen für das Produkt villeicht hinzufügen ?!
		Row reihe1 = null;
		for (int i = 0; i < record.getListOfSTerm().size(); i++) {

			for (Entry<Calendar, Double> entry : record.getListOfSTerm().get(i).getDateListFromSearchterm()
					.entrySet()) {
				if (i == 0) {
					reihe1 = blatt.createRow(rowI++);
				} else {
					reihe1 = blatt.getRow(rowI++);
				}

				Cell cell = reihe1.createCell(i + 1);

				cell.setCellValue(entry.getValue());

			}

			rowI = 2;
		}
		Iterator<Entry<Calendar, Double>> it = product.getOrderDRequest().entrySet().iterator();

		Entry<Calendar, Double> test = it.next();
		for (Entry<Calendar, Double> entry : record.getListOfSTerm().get(0).getDateListFromSearchterm().entrySet()) {
			reihe1 = blatt.getRow(rowI++);
			Cell cell = reihe1.createCell(0);
			cell.setCellValue(entry.getKey().get(Calendar.DAY_OF_MONTH) + "." + (entry.getKey().get(Calendar.MONTH) + 1)
					+ "." + entry.getKey().get(Calendar.YEAR));
			if (entry.getKey().getTime().equals(test.getKey().getTime())) {
				Cell cell2 = reihe1.createCell(record.getListOfSTerm().size() + 1);
				cell2.setCellValue(test.getValue());
				if (it.hasNext()) {
					test = it.next();
				}
			}

		}
		try {
			JFileChooser chooser=new JFileChooser(".");
			String suggestion = ausgabe+"_"+record.getName()+"_"+product.getName();
			suggestion=suggestion.replaceAll(" ", "_");
			   FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files","xls","excel","xlsx");

			   chooser.addChoosableFileFilter(filter);


			   chooser.setFileFilter(filter);
			   chooser.setFileSelectionMode(chooser.FILES_AND_DIRECTORIES);
			   chooser.setDialogTitle("Save File");
			   chooser.setCurrentDirectory(new File(System.getProperties().getProperty("user.home")));
			   chooser.setSelectedFile(new File(suggestion));
			   chooser.showSaveDialog(null);
			 
			
			
			FileOutputStream output = new FileOutputStream(chooser.getSelectedFile()+".xlsx");
			datei.write(output);
			output.close();
			datei.close();
		} catch (Exception e) {

		}

	}// saveExcel

	public static GPProduct ExceltoJava(GPFileManager of) {
		GPProduct tmp = new GPProduct();
		TreeMap<Calendar, Double> orderDrequested = new TreeMap<Calendar, Double>();

		try {

			// FileInputStream file = new FileInputStream(new
			// File("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\Excel\\Sales_Daten_Sennheiser_BAGehrig.xlsx"));
			FileInputStream file = new FileInputStream(of.getFile().getPath());

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(1);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			for (int i = 0; i < 5; i++) {
				rowIterator.next();
			}

			for (int i = 0; i < 3; i++) {

				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				cellIterator.next();
				Cell cell = cellIterator.next();

				tmp.setName(cell.getStringCellValue());
				cellIterator.next();
				Cell cell2 = cellIterator.next();

				Date sdate = cell2.getDateCellValue();

				Calendar calendar = new GregorianCalendar();
				calendar.setTime(sdate);
				cellIterator.next();
				Cell cell3 = cellIterator.next();

				// Cell cell4 = row.createCell(cell3.getColumnIndex()+1);

				// cell4.setCellFormula(("("+cell3.getNumericCellValue()+"-MIN(F:F))*(1/(MAX(F:F)-MIN(F:F)))*100"));
				// evaluator.notifySetFormula(cell4);
				// CellValue cellValue = evaluator.evaluate(cell4);

				orderDrequested.put(calendar, cell3.getNumericCellValue());
			}
			Calendar cp = new GregorianCalendar();
			cp.setTime(orderDrequested.firstKey().getTime());
			do {

				Calendar a = cp;
				Calendar b = orderDrequested.higherKey(cp);
				a.add(Calendar.DAY_OF_MONTH, 1);
				while (a.before(b)) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(a.getTime());
					orderDrequested.put(calendar, 0.0);
					a.add(Calendar.DAY_OF_MONTH, 1);

				}
				try {
					cp.setTime(b.getTime());

				} catch (NullPointerException e) {
					break;
				}

			} while (cp != orderDrequested.lastKey());
			tmp.setOrderDRequest(orderDrequested);
			file.close();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tmp;
	}


	
}
