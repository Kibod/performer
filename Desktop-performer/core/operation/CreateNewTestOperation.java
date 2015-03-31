package core.operation;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class CreateNewTestOperation represents an operation that creates a new test and stores its parameters in an XML file.
 * The class uses DOM parser to create a new XML file.
 * @author Bojan Marjanovic
 */
public class CreateNewTestOperation implements IGlobal {
	
	//Operation priority.
	private final Priority priority = Priority.LOW;
	
	//Name of the XML file.
	private String fileName;
	//Time limit in which a student program must finish.
	private String timeLimit;
	//Input and output parameters of a student program.
	private String input, output;
	
	private boolean bInput = false;
	private boolean bOutput = false;
	private boolean bTimeElapsed = false;
	private boolean bMemory = false;
	private boolean bCPUTime = false;
	private boolean bThreadNumber = false;
	private boolean bHandlers = false;
	private boolean bProcessID = false;
	
	/**
	 * Creates a new CreateNewTestOperation instance.
	 * @param fileName - A String pathname of the XML file (without the extension) that is to be created.  
	 */
	public CreateNewTestOperation(String fileName) {
		this.fileName = fileName;
	}
	
	public void setInput(String input) {
		bInput = true;
		this.input = input;
	}
	
	public void setOutput(String output) {
		bOutput = true;
		this.output = output;
	}
	
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public void setMemory() {
		bMemory = true;
	}
	
	public void setCPUTime() {
		bCPUTime = true;
	}
	
	public void setThreadNumber() {
		bThreadNumber = true;
	} 
	
	public void setHandlers() {
		bHandlers = true;
	}
        
        public void setElapsedTime() {
		bTimeElapsed = true;
	}
        
        public void setProcessID() {
		bProcessID = true;
	}
	
	//Creates an XML file that will hold all test parameters.
	private void createXMLTestFile() {
		
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			//Test elements.
			Document document = docBuilder.newDocument();
			Element rootElement = document.createElement("test");
			document.appendChild(rootElement);
			
			//Input element.
			if (bInput) {
				Element input = document.createElement("input");
				input.appendChild(document.createTextNode(this.input));
				rootElement.appendChild(input);
			}
						
			if (bOutput) {
				Element output = document.createElement("output");
				output.appendChild(document.createTextNode(this.output));
				rootElement.appendChild(output);
			}
            
			Element tLimit = document.createElement("time_limit");
			tLimit.appendChild(document.createTextNode(this.timeLimit));
			rootElement.appendChild(tLimit);
			
			if (bTimeElapsed) {
				Element elapsedTime = document.createElement("elapsed_time");
				rootElement.appendChild(elapsedTime);
			}
			
			if (bMemory) {
				Element memory = document.createElement("memory");
				rootElement.appendChild(memory);
			}
			
			if (bCPUTime) {
				Element cpuTime = document.createElement("cpu_time");
				rootElement.appendChild(cpuTime);
			}
			
			if (bThreadNumber) {
				Element threadNumber = document.createElement("thread_number");
				rootElement.appendChild(threadNumber);
			}
			
			if (bHandlers) {
				Element handlers = document.createElement("handlers");
				rootElement.appendChild(handlers);
			}
                        
            if (bProcessID) {
				Element processID = document.createElement("pid");
				rootElement.appendChild(processID);
			}
	 
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			//Indent the XML tags.
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//Set the amount of indentation.
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);
                        
			StreamResult result = new StreamResult(new File(gui.performer_main.MainWindow.runningProject.getTestsRootFolder() + "\\" + fileName + ".xml"));

			//Write the content into an XML file.
			transformer.transform(source, result);
	 
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
	/**
	 * Execute the operation.
	 */
	@Override
	public void execute() {
		this.createXMLTestFile();
	}
	
	/**
	 * Return the priority of the operation.
	 * @return an int value
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}

}
