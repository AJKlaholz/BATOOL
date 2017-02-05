package application.control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.time.TimeSeries;

import application.boundary.OpenFile;




public class Excel {
	
	public void saveToExcel(Record record, Product product) {
		double[] cor = new double[record.getListOfSTerm().size()];
		
		
		for (int i = 0; i < record.getListOfSTerm().size(); i++) {
		
			ArrayList<Double> a = new ArrayList<Double>();
			ArrayList<Double> b = new ArrayList<Double>();
		
		for (Entry<Calendar, Double> entry : record.getListOfSTerm().get(i).getDateListFromSearchterm().entrySet()) {

			for (Entry<Calendar, Double> product2 : product.getOrderDRequest().entrySet()) {
		
					if(entry.getKey().equals(product2.getKey())){
						a.add(entry.getValue());
						b.add(product2.getValue());
						
					}
			}
			
			
		}
		cor[i] = roundScale2(((Correlation(a.toArray(new Double[a.size()]), b.toArray(new Double[a.size()])))));
		
		}
		
		
		
		
		
		
		
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
			zelle3.setCellValue(record.getListOfSTerm().get(i - 1).getName()+"("+ cor[i-1] +")");
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
		try {
			FileOutputStream output = new FileOutputStream("ExcelTest2.xlsx");
			datei.write(output);
			output.close();
			datei.close();
		} catch (Exception e) {

		}
		
	}// saveExcel

	public static Product ExceltoJava(OpenFile of) {
		Product tmp = new Product();
		TreeMap<Calendar, Double> orderDrequested = new TreeMap<Calendar, Double>();
		

		try {

			
			// FileInputStream file = new FileInputStream(new
			// File("C:\\Users\\Adrian\\Documents\\pytrends-master1.2\\Excel\\Sales_Daten_Sennheiser_BAGehrig.xlsx"));
			FileInputStream file = new FileInputStream(of.PickMe().getPath());
		
		

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
			
				
				
				Cell cell4 = row.createCell(cell3.getColumnIndex()+1);
				
				cell4.setCellFormula(("("+cell3.getNumericCellValue()+"-MIN(F:F))*(1/(MAX(F:F)-MIN(F:F)))*100"));
				evaluator.notifySetFormula(cell4);
				CellValue cellValue = evaluator.evaluate(cell4);
				
				
			
				
				System.out.println(cellValue.getNumberValue());
				orderDrequested.put(calendar, cellValue.getNumberValue());
		

				
				
			
				
				
			}
			tmp.setOrderDRequest(orderDrequested);
			file.close();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tmp;
	}
	public static double Correlation(Double[] doubles, Double[] doubles2) {
	    //TODO: check here that arrays are not null, of the same length etc

	    double sx = 0.0;
	    double sy = 0.0;
	    double sxx = 0.0;
	    double syy = 0.0;
	    double sxy = 0.0;

	    int n = doubles.length;

	    for(int i = 0; i < n; ++i) {
	      double x = doubles[i];
	      double y = doubles2[i];

	      sx += x;
	      sy += y;
	      sxx += x * x;
	      syy += y * y;
	      sxy += x * y;
	    }

	    // covariation
	    double cov = sxy / n - sx * sy / n / n;
	    // standard error of x
	    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
	    // standard error of y
	    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

	    // correlation is just a normalized covariation
	    return cov / sigmax / sigmay;
	  }
	static double roundScale2( double d )
	  {
	    return Math.rint( d * 1000 ) / 1000.;
	  }
}
