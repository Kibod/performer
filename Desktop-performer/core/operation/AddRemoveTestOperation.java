package core.operation;

import java.io.File;
import java.io.IOException;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class AddTestOperation represents an operation that reads test parameters from an XML file and loads them into an appropriate container.<br/>
 * The class uses SAX parser to read the data from an XML file. 
 * @author Bojan Marjanovic
 *
 */
public class AddRemoveTestOperation implements IGlobal {
	
	//Operation priority.
	private final Priority priority = Priority.LOW;
	
	//An XML project file path.
	private String projectPath;
	//An XML test file path.
	private String testPath;
	
	//Add or remove test from file.
	private boolean add_remove;
	
	/**
	 * Creates a new AddTestOperation instance.
	 * @param file - A String pathname of the XML file from which the data will be read.
	 */
	public AddRemoveTestOperation(String projectPath, String testPath, boolean add_remove) {
		this.projectPath = projectPath;
		this.testPath = testPath;
		this.add_remove = add_remove;
	}
	
	private void addTestFile() {
		
		try {
			
			String filepath = projectPath;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			
			Node testRooth = doc.getFirstChild();
			
			NodeList list = testRooth.getChildNodes();
			
			if (add_remove) {
				Element test = doc.createElement("test_path");
				test.appendChild(doc.createTextNode(testPath));
				testRooth.appendChild(test);
			} else {
				for (int i=0; i<list.getLength(); i++) {
					Node node = list.item(i);
					if (testPath.equals(node.getNodeName())) {
						testRooth.removeChild(node);
					}
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			//Indent the XML tags.
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//Set the amount of indentation.
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			
			//Write the content into an XML file.
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
	}
		
	/**
	 * Execute the operation.
	 */
	@Override
	public void execute() {
		this.addTestFile();		
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
