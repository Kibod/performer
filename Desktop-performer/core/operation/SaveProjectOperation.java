package core.operation;

import java.io.File;
import java.util.ArrayList;

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

public class SaveProjectOperation implements IGlobal {
	
	//Operation priority.
	private final Priority priority = Priority.NORMAL;
	
	private String projectPath;
	private String studentRoothFolderPath;
	private ArrayList<String> xmlTests;
	private String fileType;
	
	public SaveProjectOperation(String projectPath, String studentFolderPath,String fileType, String ... xmlTests) {
		this.projectPath = projectPath;
		this.studentRoothFolderPath = studentFolderPath;
		this.fileType = fileType;
		for (String test : xmlTests) {
			this.xmlTests.add(test);
		}
	}
	
	//Creates an XML file that will hold all project parameters.
	private void createXMLProjectFile() {
		
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			//Template elements.
			Document document = docBuilder.newDocument();
			Element rootElement = document.createElement("project");
			document.appendChild(rootElement);
			
			//Project name element.
			Element project = document.createElement("project_path");
			project.appendChild(document.createTextNode(this.projectPath));
			rootElement.appendChild(project);
			
			//Student folder name element.
			Element student = document.createElement("student_rooth_path");
			student.appendChild(document.createTextNode(this.studentRoothFolderPath));
			rootElement.appendChild(student);
			
			//Test parent element.
			Element test = document.createElement("test_rooth");
			rootElement.appendChild(test);
			
			//Test path elements.
			for (String t : xmlTests) {
				Element testChild = document.createElement("test_path");
				testChild.appendChild(document.createTextNode(t));
				test.appendChild(testChild);
			}
			
			//File type element.
			Element fType = document.createElement("file_type");
			fType.appendChild(document.createTextNode(this.fileType));
			rootElement.appendChild(fType);
	 
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			//Indent the XML tags.
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//Set the amount of indentation.
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(projectPath + ".xml"));

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
		this.createXMLProjectFile();
	}
	
	/**
	 * Return the priority of the operation.
	 * @return a Priority value
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}

}
