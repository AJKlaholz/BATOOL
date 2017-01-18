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

	public void saveToExcel(Record record) {
		System.out.println(record.getListOfSTerm().get(0).getName());
		int rowI=2;

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
			zelle3.setCellValue(record.getListOfSTerm().get(i-1).getName());
		}
		Cell zelle4 = reihe2.createCell(record.getListOfSTerm().size()+1);
		zelle4.setCellValue("Produkt");

		
	/*	
		for(int i=0;i<record.getListOfSTerm().get(0).getDateListFromSearchterm().size();i++){
			Row reihetmp = blatt.createRow(i+2);
			Cell zelletmp1 = reihetmp.createCell(0);
			
			
			zelletmp1.setCellValue(record.getListOfSTerm().get(0).getDateListFromSearchterm().);
			for(int l=0;l<record.getListOfSTerm().size()+2;l++){
			Cell zelletmp = reihetmp.createCell(i);
			
			}
		}
		*/
		
		Row reihe1 =blatt.createRow(rowI);
		
		System.out.println("MAP GRÖßE" +record.getListOfSTerm().get(0).getDateListFromSearchterm().entrySet().size());
		for(int i=0;i<record.getListOfSTerm().size();i++){
			
			//System.out.println("4444444444444");
			
		for(Entry<Calendar, Double> entry : record.getListOfSTerm().get(i).getDateListFromSearchterm().entrySet()){
			System.out.println("Tag: "+entry.getKey().get(Calendar.DAY_OF_MONTH)+"Monat: "+entry.getKey().get(Calendar.MONTH)+1);
			Cell cell = reihe1.createCell(i+1);
			
			cell.setCellValue(entry.getValue());
			
			if(i==0){
			reihe1 = blatt.createRow(rowI++);
			}else{
			reihe1 = blatt.getRow(rowI++);	
			}
			
		}
		
			rowI=2;
		}
		for(Entry<Calendar, Double> entry : record.getListOfSTerm().get(0).getDateListFromSearchterm().entrySet()){
			Cell cell = reihe1.createCell(0);
			cell.setCellValue(entry.getKey().get(Calendar.DAY_OF_MONTH)+"."+(entry.getKey().get(Calendar.MONTH)+1)+"."+entry.getKey().get(Calendar.YEAR));
			reihe1 = blatt.getRow(rowI++);	
		}
		
		try {
			FileOutputStream output = new FileOutputStream("ExcelTest2.xlsx");
			datei.write(output);
			output.close();
		} catch (Exception e) {

		}

	}// saveExcel

	public static Product ExceltoJava() {
		Product tmp = new Product();
		HashMap<Calendar, Double> orderDrequested = new HashMap<Calendar, Double>();

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
