package application.boundary;

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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.control.GPCorrelationCalculater;
import application.control.GPFileManager;
import application.control.GPProduct;
import application.control.GPRecord;

public class UIExcelJavaMapper {
	/*
	 * Mit dieser Methode werden die Daten aus den Record und Product Objekten
	 * in eine Excel-Datei geschrieben
	 */
	public void saveToExcel(GPRecord record, GPProduct product) {
		double[] cor = new double[record.getListOfSTerm().size()];
		java.util.Date now = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
		String dateAcTime = sdf.format(now);
		/*
		 * In diesem Block werden die Werte des jeweiligem Suchbegriffs und des
		 * Produkts in eine ArrayListe hinzugefügt. Die ArrayListen werden dann
		 * für die Korrelation an die Methode getCorreltaion übergeben.
		 */
		for (int i = 0; i < record.getListOfSTerm().size(); i++) {

			ArrayList<Integer> corArrRec = new ArrayList<Integer>();
			ArrayList<Integer> corArrPro = new ArrayList<Integer>();

			for (Entry<Calendar, Integer> entryRecord : record.getListOfSTerm().get(i).getDateListFromSearchterm()
					.entrySet()) {

				for (Entry<Calendar, Integer> entryProduct : product.getOrderDRequest().entrySet()) {

					if (entryRecord.getKey().equals(entryProduct.getKey())) {
						corArrRec.add(entryRecord.getValue());
						corArrPro.add(entryProduct.getValue());

					}
				}

			}
			cor[i] = ((GPCorrelationCalculater.getCorrelation(corArrRec.toArray(new Integer[corArrRec.size()]),
					corArrPro.toArray(new Integer[corArrRec.size()]))));

		}

		int rowI = 2;

		// Erstellt eine XLSX Datei
		Workbook file = new XSSFWorkbook();

		Sheet sheet = file.createSheet(WorkbookUtil.createSafeSheetName("Result"));
		//Beschreibung der Datei und Bezeichnungen der Spalten 
		Row frow = sheet.createRow(0);
		Cell fcell = frow.createCell(0);
		fcell.setCellValue(
				"Result on " + dateAcTime + " with record " + record.getName() + " and product " + product.getName());

		Row srow = sheet.createRow(1);
		Cell srowfcell = srow.createCell(0);
		srowfcell.setCellValue("Datum");
		for (int i = 1; i <= record.getListOfSTerm().size(); i++) {
			Cell descriptionCell = srow.createCell(i);
			descriptionCell.setCellValue(record.getListOfSTerm().get(i - 1).getName() + "(" + cor[i - 1] + ")");
		}
		Cell productNameCell = srow.createCell(record.getListOfSTerm().size() + 1);
		productNameCell.setCellValue(product.getName());
		Row reihe1 = null;
		//Schreibe die Beliebtheit der Suchbegriffe in die Cellen	
		for (int i = 0; i < record.getListOfSTerm().size(); i++) {

			for (Entry<Calendar, Integer> entry : record.getListOfSTerm().get(i).getDateListFromSearchterm()
					.entrySet()) {
				if (i == 0) {
					reihe1 = sheet.createRow(rowI++);
				} else {
					reihe1 = sheet.getRow(rowI++);
				}

				Cell cell = reihe1.createCell(i + 1);

				cell.setCellValue(entry.getValue());

			}

			rowI = 2;
		}
		/*
		 * Fülle die linke Spalte mit dem Datum und
		 * 	fülle die Spalte des Produkts mit den Absatzdaten
		 */
		Iterator<Entry<Calendar, Integer>> it = product.getOrderDRequest().entrySet().iterator();
		Entry<Calendar, Integer> test = it.next();
		for (Entry<Calendar, Integer> entry : record.getListOfSTerm().get(0).getDateListFromSearchterm().entrySet()) {
			reihe1 = sheet.getRow(rowI++);
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
			//Dialog zum Speichern der Datei
			JFileChooser chooser = new JFileChooser(".");
			String suggestion = dateAcTime + "_" + record.getName() + "_" + product.getName();
			suggestion = suggestion.replaceAll(" ", "_");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls", "excel", "xlsx");

			chooser.addChoosableFileFilter(filter);
			chooser.setFileFilter(filter);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setDialogTitle("Save File");
			chooser.setCurrentDirectory(new File(System.getProperties().getProperty("user.home")));
			chooser.setSelectedFile(new File(suggestion));
			chooser.showSaveDialog(null);

			FileOutputStream output = new FileOutputStream(chooser.getSelectedFile() + ".xlsx");
			file.write(output);
			output.close();
			file.close();
		} catch (Exception e) {

		}

	}
	
	//Schreibt die Absatzdaten aus der Excel-Datei in ein GPProducit
	public static GPProduct ExceltoJava(GPFileManager of) {
		GPProduct tmp = new GPProduct();
		int countRow = 0;
		TreeMap<Calendar, Integer> orderDrequested = new TreeMap<Calendar, Integer>();

		try {
			//die Datei auswählen
			FileInputStream file = new FileInputStream(of.getFile().getPath());
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(1);
			Iterator<Row> rowIterator = sheet.iterator();
			//Überspringe die ersten fünf Zeilen
			for (int i = 0; i < 5; i++) {
				rowIterator.next();
			}
		
			while (true) {

				Row row = rowIterator.next();

				// Für jede Zeile, iteriere über alle Spalten
				Iterator<Cell> cellIterator = row.cellIterator();
				cellIterator.next();
				Cell cell = cellIterator.next();
				if (countRow == 0 || workbook.getSheetAt(1).getRow(row.getRowNum() - 1).getCell(cell.getColumnIndex())
						.getStringCellValue().equals(cell.getStringCellValue())) {
					tmp.setName(cell.getStringCellValue());
					cellIterator.next();
					Cell cell2 = cellIterator.next();

					Date sdate = cell2.getDateCellValue();

					Calendar calendar = new GregorianCalendar();
					calendar.setTime(sdate);
					cellIterator.next();
					Cell cell3 = cellIterator.next();
					if (cell3.getNumericCellValue() < 0) {
						orderDrequested.put(calendar, 0);
					} else {
						orderDrequested.put(calendar, (int) cell3.getNumericCellValue());
					}
				} else {
					break;
				}
				countRow++;
			}

			// Befüllt alle Absatzdaten ohne Wert mit 0
			Calendar cp = new GregorianCalendar();
			cp.setTime(orderDrequested.firstKey().getTime());
			do {

				Calendar a = cp;
				Calendar b = orderDrequested.higherKey(cp);
				a.add(Calendar.DAY_OF_MONTH, 1);
				while (a.before(b)) {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(a.getTime());
					orderDrequested.put(calendar, 0);
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
