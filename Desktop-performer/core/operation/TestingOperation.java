package core.operation;

import core.tests.src.testovi.Tester;
import gui.performer_main.Assignment;

import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class TestingOperation represents an operation that tests an application written by a student.
 * @author Bojan Marjanovic
 *
 */
public class TestingOperation implements ITest {
	
	//Operation priority.
	private final Priority priority = Priority.LOWEST;
	
	//Path of the currently active project.
	private static String xmlProjectFile;
	//Path of a student file that is to be tested.
	private String file;
	
	
	/*
	 * This class represents a single XML test file.
	 * When creating an instance of the TestFile class, only the XML file path is given.
	 * Other members of the class are instantiated when the loadXMLTestFile() method is called.
	 * Method loadXMLTestFile() reads the XML file using the SAX parser.
	 */
	private class TestFile {
		
		private boolean outputTest = false;
		private boolean timeTest = false;
		private boolean memoryTest = false;
		private boolean CPUTimeTest = false;
		private boolean threadNumberTest = false;
		private boolean handlersTest = false;
		private boolean processIDTest = false;
		
		//Time limit in which a student program must finish.
		private int timeLimit;
		//Input parameters of a student program.
		private String[] input;
		//Output parameters of a student program.
		private String output;
		
		//Name of the test file.
		private String testFilePath;
		
		protected TestFile(String testFilePath) {
			this.testFilePath = testFilePath;
		}
		
		protected int getTimeLimit() {
			return timeLimit;
		}
		
		protected String[] getInput() {
			return input;
		}
		
		protected String getOutput() {
			return output;
		}
		
		protected boolean getOutputTest() {
			return outputTest;
		}
		
		protected boolean getTimeTest() {
			return timeTest;
		}
		
		protected boolean getMemoryTest() {
			return memoryTest;
		}
		
		protected boolean getCPUTimeTest() {
			return CPUTimeTest;
		}
		
		protected boolean getThreadNumberTest() {
			return threadNumberTest;
		}
		
		protected boolean gethandlersTest() {
			return handlersTest;
		}
		
		protected boolean getProcessIDTest() {
			return processIDTest;
		}
		
		//Loads test parameters from an XML file.
		protected void LoadXMLTestFile() {
			
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
		  	    			String in = new String(ch, start, length);
		  	    			String delimiter = "[ ]";
		  	    			input = in.split(delimiter);
		  	    			bInput = false;
		  	    		}
		  	    		  
		  	    		//Reads the data from the "output" tag
		  	    		if (bOutput) {
		  	    			outputTest = bOutput;
		  	    			output = new String(ch, start, length);
		  	    			bOutput = false;
		  	    		}
		  	    		  
		  	    		//Reads the data from the "time_limit" tag.
		  	    		if (bTimeLimit) {
		  	    			timeLimit = Integer.parseInt(new String(ch, start, length));
		  	    			bTimeLimit = false;
		  	    		}
		  	    		
		  	    		if (bMemory) {
		  	    			if ((new String(ch, start, length)).equals("true")) {
		  	    				memoryTest = true;
		  	    			}
		  	    		}
		  	    		
		  	    		if (bCPUTime) {
		  	    			if ((new String(ch, start, length)).equals("true")) {
		  	    				CPUTimeTest = true;
		  	    			}
		  	    		}
		  	    		
		  	    		if (bThreadNumber) {
		  	    			if ((new String(ch, start, length)).equals("true")) {
		  	    				threadNumberTest = true;
		  	    			}
		  	    		}
		  	    		
		  	    		if (bHandlers) {
		  	    			if ((new String(ch, start, length)).equals("true")) {
		  	    				handlersTest = true;
		  	    			}
		  	    		}
		  	    		
		  	    		if (bProcessID) {
		  	    			if ((new String(ch, start, length)).equals("true")) {
		  	    				processIDTest = true;
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
		
	}
	
	
	//An array of all test files that will be used for testing an assignment.
	private ArrayList<TestFile> testList;
	
	/**
	 * Creates a new TestingOperation instance.
	 * <br/>
	 * Tests that will be used for testing are read from the XML file of the currently active project.
	 * @param assignment - Assignment file that will be tested
	 */
	public TestingOperation(Assignment assignment) {
		file = assignment.getPath();
		testList = collectTestFiles();
	}
	
	/**
	 * Creates a new TestingOperation instance.
	 * @param assignment - Assignment file that will be tested.
	 * @param tests - An array of String files that represent XML test paths. 
	 */
	public TestingOperation(Assignment assignment, String ... tests) {
		file = assignment.getPath();
		testList = new ArrayList<TestFile>();
		for (String test : tests) {
			TestFile tFile = new TestFile(test);
			tFile.LoadXMLTestFile();
			testList.add(tFile);
		}
	}
	
	/*
	 * The method creates an array list of test files using SAX parser.
	 * The method only reads the nodes in the XML files that are named "test_path".
	 * It returns an ArrayList<TestFile> object. 
	 */
	private ArrayList<TestFile> collectTestFiles() {
		ArrayList<TestFile> tests = new ArrayList<TestFile>();
		try {
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			DefaultHandler handler = new DefaultHandler() {
			
				boolean bTest = false;
			
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("test_path")) {
						bTest = true;
					}
				}
				
				public void characters(char ch[], int start, int length) throws SAXException {
					if (bTest) {
						testList.add(new TestFile(new String(ch, start, length)));
					}
				}
			};
			
			File XMLFile = new File(TestingOperation.xmlProjectFile);
	  	    InputStream inputStream= new FileInputStream(XMLFile);
	  	    Reader reader = new InputStreamReader(inputStream,"UTF-8");

	  	    InputSource iSource = new InputSource(reader);
	  	    //Enables UTF-8 encoded characters to be read. 
	  	    iSource.setEncoding("UTF-8");

	  	    saxParser.parse(iSource, handler);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tests;
	}
	
	/*
	 * Test the given assignment while circulating through all given tests.
	 * If all tests have been passed successfully, set the result for the assignment as PASS.
	 * If any of the tests have failed, set the result as FAILED.
	 * The result is used for visually marking the assignment in the gui student list panel. 
	 */
	private void testAssignment() {
		String result = "";
		Assignment assignment = new Assignment(file);
		for (TestFile tFile : testList) {
			Tester test = new Tester(file, tFile.getInput());
			if (tFile.getOutputTest()) {
				test.doOutput_test(tFile.getOutputTest(), tFile.getOutput());
			}
			if (tFile.getTimeTest()) {
				test.doElapsed_time_test(tFile.getTimeTest());
			}
			if (tFile.getCPUTimeTest()) {
				test.doCpu_time_test(tFile.getCPUTimeTest());
			}
			if (tFile.getMemoryTest()) {
				test.doMemory_test(tFile.getMemoryTest());
			}
			if (tFile.getThreadNumberTest()) {
				test.doThd_test(tFile.getThreadNumberTest());
			}
			if (tFile.gethandlersTest()) {
				test.doHnd_test(tFile.gethandlersTest());
			}
			if (tFile.getProcessIDTest()) {
				test.doPid_test(tFile.getProcessIDTest());
			}
			test.setTime_limit(tFile.getTimeLimit());
			test.start();
			if (test.getOutput().equalsIgnoreCase("false") || test.getHas_error().equalsIgnoreCase("false")) result = "PASS";
			else result = "FAILED";
		}
		assignment.setResult(result);
	}
	
	public static void setXMLProjectFile(String projectFile) {
		TestingOperation.xmlProjectFile = projectFile;
	}
	
	/**
	 * Return the priority of the operation.
	 * @return a Priority value
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}
	
	/**
	 * Execute the operation.
	 */
	@Override
	public void execute() {
		testAssignment();
	}

}
