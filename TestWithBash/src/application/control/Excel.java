package application.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.boundary.OpenFile;

public class Excel {

	public void saveToExcel(Record record, Product product) {

		int rowI = 2;

		// Erstellt eine XLSX Datei
		Workbook datei = new XSSFWorkbook();

		Sheet blatt = datei.createSheet(WorkbookUtil.createSafeSheetName("Ergebnis"));

		Row reihe = blatt.createRow(0);
		Cell zelle = reihe.createCell(0);
		zelle.setCellValue("Ergebnis: ");

		Row reihe2 = blatt.createRow(1);
		Cell zelle2 = reihe2.createCell(0);
		zelle2.setCellValue("Datum");
		for (int i = 1; i <= record.getListOfSTerm().size(); i++) {
			Cell zelle3 = reihe2.createCell(i);
			zelle3.setCellValue(record.getListOfSTerm().get(i - 1).getName());
		}
		Cell zelle4 = reihe2.createCell(record.getListOfSTerm().size() + 1);
		zelle4.setCellValue("Produkt");

		Row reihe1 = null;

		System.out.println("MAP GRÖßE" + record.getListOfSTerm().get(0).getDateListFromSearchterm().entrySet().size());
		for (int i = 0; i < record.getListOfSTerm().size(); i++) {

			for (Entry<Calendar, Double> entry : record.getListOfSTerm().get(i).getDateListFromSearchterm()
					.entrySet()) {
				System.out.println("Tag: " + entry.getKey().get(Calendar.DAY_OF_MONTH) + "Monat: "
						+ (entry.getKey().get(Calendar.MONTH) + 1) + "Value: " + entry.getValue());
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
		System.out.println(blatt.getRow(3).getCell(0));
		Iterator<Entry<Calendar, Double>> it = product.getOrderDRequest().entrySet().iterator();

		Entry<Calendar, Double> test = it.next();
		for (Entry<Calendar, Double> entry : record.getListOfSTerm().get(0).getDateListFromSearchterm().entrySet()) {
			reihe1 = blatt.getRow(rowI++);
			Cell cell = reihe1.createCell(0);
			cell.setCellValue(entry.getKey().get(Calendar.DAY_OF_MONTH) + "." + (entry.getKey().get(Calendar.MONTH) + 1)
					+ "." + entry.getKey().get(Calendar.YEAR));
			System.out.println(
					"Datensatzdatum: " + entry.getKey().getTime() + " Produktdatum: " + test.getKey().getTime());
			if (entry.getKey().getTime().equals(test.getKey().getTime())) {
				Cell cell2 = reihe1.createCell(record.getListOfSTerm().size() + 1);
				cell2.setCellValue(test.getValue());
				if (it.hasNext()) {
					test = it.next();
				}
			}

		}
		System.out.println(blatt.getRow(3).getCell(0));
		try {
			FileOutputStream output = new FileOutputStream("ExcelTest2.xlsx");
			datei.write(output);
			output.close();
		} catch (Exception e) {

		}
		System.out.println(blatt.getRow(3).getCell(0));
	}// saveExcel

	public static Product ExceltoJava() {
		Product tmp = new Product();
		TreeMap<Calendar, Double> orderDrequested = new TreeMap<Calendar, Double>();

		try {

			OpenFile of = new OpenFile();
			// FileInputStream file = new FileInputStream(new
			// File("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\Excel\\Sales_Daten_Sennheiser_BAGehrig.xlsx"));
			FileInputStream file = new FileInputStream(new File("" + of.PickMe()));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

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
				orderDrequested.put(calendar, cell3.getNumericCellValue());

				System.out.println();
			}
			tmp.setOrderDRequest(orderDrequested);
			file.close();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tmp;
	}
}
