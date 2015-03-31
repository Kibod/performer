package core.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class TableReportFile is used for creating XLS, XLSX or HTML table files that contain results of the tested applications.
 * It does not represent a stand alone operation.<br />
 * To use the class just create a new instance of the class and then call the appropriate method:
 * <p>
 * 	<code>
 * 		TableReportFile reportFile = new TableReportFile("c:\\temp\\some_file.xlsx");<br />
 * 		reportFile.createXlsFile(); //or<br />
 * 		reportFile.createXlsxFile(); //or<br />
 * 		reportFile.createHtmlFile();
 * 	</code>
 * </p>
 * @author Bojan Marjanovic
 *
 */
public class TableReportFile {
	
	//File name of the report file without the extension.
	private String reportFile;
	
	private boolean bInput = false;
	private boolean bOutput = false;
	private boolean bTimeLimit = false;
	private boolean bMemory = false;
	private boolean bCPUTime = false;
	private boolean bThreadNumber = false;
	private boolean bHandlers = false;
	private boolean bProcessID = false;
	
	/**
	 * Creates an instance of the TableReportFile class.
	 * @param reportFile - <code>String</code> object that represents filename without the extension.
	 * @param testNumber - <code>Integer</code> number that represents the number of tests that were executed on a single application.
	 */
	public TableReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
	
	/*
	 * This method is used for creating cells in an excel file.
	 * Alignment of the cell is automatically adjusted by calling the setAlignment() method for horizontal alignment,
	 * and the setVerticalAlignment() method for vertical alignment of the cell.
	 */
	private void createCell(Workbook wb, Row row, int column, String cellValue, short halign, short valign) {
		Cell cell = row.createCell(column);
		cell.setCellValue(cellValue);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);
		cell.setCellStyle(cellStyle);
	}
	
	/*
	 * This method is used to enable the creation of certain sheet columns.
	 * It does so by reading an XML test file and setting the appropriate boolean class members to true.
	 * Those members are later used to create the columns.
	 */
	private void setColumns(String testFilePath) {
		
		try {
			 
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

	  	    DefaultHandler handler = new DefaultHandler() {

	  	    	boolean bInput = false;
	  	    	boolean bOutput = false;
	  	    	boolean bTimeLimit = false;
	  	    	boolean bMemory = false;
	  	    	boolean bCPUTime = false;
	  	    	boolean bThreadNumber = false;
	  	    	boolean bHandlers = false;
	  	    	boolean bProcessID = false;
	  	        
	  	    	@Override
	  	    	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	  	    		  
	  	    		if (qName.equals("input")) {
	  	    			bInput = true;
	  	    		}

	  	    		if (qName.equalsIgnoreCase("output")) {
	  	    			bOutput = true;
	  	    		}

	  	    		if (qName.equalsIgnoreCase("time_limit")) {
	  	    			bTimeLimit = true;
	  	    		}

	  	    		if (qName.equalsIgnoreCase("memory")) {
	  	    			bMemory = true;
	  	    		}

	  	    		if (qName.equalsIgnoreCase("cpu_time")) {
	  	    			bCPUTime = true;
	  	    		}

	  	    		if (qName.equalsIgnoreCase("thread_number")) {
	  	    			bThreadNumber = true;
	  	    		}

	  	    		if (qName.equalsIgnoreCase("handlers")) {
	  	    			bHandlers = true;
	  	    		}
	  	    		
	  	    		if (qName.equalsIgnoreCase("pid")) {
	  	    			bProcessID = true;
	  	    		}

	  	    	}

	  	    	public void characters(char ch[], int start, int length) throws SAXException {
	  	    		  
	  	    		//Reads the data from the "input" tag.
	  	    		if (bInput) {
	  	    			this.bInput = true;
	  	    			bInput = false;
	  	    		}
	  	    		  
	  	    		//Reads the data from the "output" tag
	  	    		if (bOutput) {
	  	    			this.bOutput = true;
	  	    			bOutput = false;
	  	    		}
	  	    		  
	  	    		//Reads the data from the "time_limit" tag.
	  	    		if (bTimeLimit) {
	  	    			this.bTimeLimit = true;
	  	    			bTimeLimit = false;
	  	    		}
	  	    		
	  	    		if (bMemory) {
	  	    			if ((new String(ch, start, length)).equals("true")) {
	  	    				this.bMemory = true;
	  	    			}
	  	    		}
	  	    		
	  	    		if (bCPUTime) {
	  	    			if ((new String(ch, start, length)).equals("true")) {
	  	    				this.bCPUTime = true;
	  	    			}
	  	    		}
	  	    		
	  	    		if (bThreadNumber) {
	  	    			if ((new String(ch, start, length)).equals("true")) {
	  	    				this.bThreadNumber = true;
	  	    			}
	  	    		}
	  	    		
	  	    		if (bHandlers) {
	  	    			if ((new String(ch, start, length)).equals("true")) {
	  	    				this.bHandlers = true;
	  	    			}
	  	    		}
	  	    		
	  	    		if (bProcessID) {
	  	    			if ((new String(ch, start, length)).equals("true")) {
	  	    				this.bProcessID = true;
	  	    			}
	  	    		}

	  	    	}

	  	    };

	  	    File XMLFile = new File(testFilePath);
	  	    InputStream inputStream= new FileInputStream(XMLFile);
	  	    Reader reader = new InputStreamReader(inputStream,"UTF-8");

	  	    InputSource iSource = new InputSource(reader);
	  	    //Enables UTF-8 encoded characters to be read. 
	  	    iSource.setEncoding("UTF-8");

	  	    saxParser.parse(iSource, handler);

		} catch (Exception e) {
	  	      e.printStackTrace();
		}
		
	}
	
	/**
	 * Creates an XLSX or an XLS type file using apache poi library depending on the extension.
	 * Only the first row of every sheet is initialized.
	 */
	public void createFile(LinkedList<String> tests) {
			
		Workbook workbook = new HSSFWorkbook();
			
		for (int cnt=0; cnt<tests.size(); cnt++) {
			setColumns(tests.get(cnt));
			Sheet sheet = workbook.createSheet("Test " + cnt);
			Row row = sheet.createRow(0);

			int counter = 2; //A counter which is used to determine how many additional columns are needed.
			createCell(workbook, row, 0, "Grupa", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
			createCell(workbook, row, 1, "Indeks", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
			createCell(workbook, row, 2, "Naziv Fajla", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);			
			if (bInput) {
				createCell(workbook, row, counter+1, "Ulaz", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}
			if (bOutput) {
				createCell(workbook, row, counter+1, "Izlaz", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}
			if (bTimeLimit) {
				createCell(workbook, row, counter+1, "Vremensko Ogranicenje", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}
			if (bMemory) {
				createCell(workbook, row, counter+1, "Zauzece Memorije", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}
			if (bCPUTime) {
				createCell(workbook, row, counter+1, "CPU Vreme", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}
			if (bThreadNumber) {
				createCell(workbook, row, counter+1, "Broj Niti", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}
			if (bHandlers) {
				createCell(workbook, row, counter+1, "Rukovaoci Greskama", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}
			if (bProcessID) {
				createCell(workbook, row, counter+1, "Proces ID", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
				counter++;
			}			
			createCell(workbook, row, counter+1, "Uspesno/Neuspesno", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
			createCell(workbook, row, counter+2, "Komentar", CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
			
			for (int i=0; i<counter+3; i++) {
				sheet.autoSizeColumn(i);
			}
		}
			
		try {
			FileOutputStream fileOutput = new FileOutputStream(reportFile + ".xls");
			workbook.write(fileOutput);
			fileOutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Takes the given XLS or XLSX file and writes in the new data from the test.
	 * @param file - <code>String</code> object that represents an XLS type file.
	 * @param assigNumber - <code>Integer</code> value that represents the ordered number of the tested assignment. 
	 * @param test - <code>Integer</code> value that represents an ordered number of the test that has been executed.
	 * @param argument - <code>String</code> array of all the values that the current test has returned.
	 */
	public void writeNewData(String file, int assigNumber, int testListPosition, String ... argument) {
		
		try {
			
			InputStream input = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(input);
			Sheet sheet = workbook.getSheetAt(testListPosition);
			Row row = sheet.createRow(assigNumber);
			
			int lastColumn = sheet.getRow(0).getPhysicalNumberOfCells();			
			for (int cn=0; cn<lastColumn; cn++) {
				Cell cell = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					cell = row.createCell(cn);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(argument[cn]);
				}
			}
			
			FileOutputStream fileOutput = new FileOutputStream(file);
			workbook.write(fileOutput);

			input.close();
			fileOutput.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Edits already existing data.
	 * @param file - <code>String</code> object that represents project report file.
	 */
	public void editFileData(String file) {
		//Code to be added.
	}

}
