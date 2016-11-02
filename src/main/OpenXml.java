package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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
 * @author Kayne
 *
 * Opens xml file types, sorts by user ID and then writes to new output file
 */
public class OpenXml extends FileParser {

	private Document document;
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DOMSource domSource;
	private StreamResult streamResult;
	private String xmlFileName = null;
	private List<User> users = null;
	private NodeList nodeList = null;
	private Node node = null;
	private Element rootElement = null;
	private Element xmlUser = null;
	private Element element = null;
	private Iterator itr;
	private User user;

	/**
	 * @param String fileName: name of input file to open
	 * @return boolean: returns true or false if file successfully opened
	 * Opens xml file type flagging whether successful or not
	 */
	@Override
	public boolean openFile(String fileName) {
		xmlFileName = fileName;
		documentBuilderFactory = DocumentBuilderFactory.newInstance();

		try {
			// Create new instance of document builder to parse XML file
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(xmlFileName);
			document.getDocumentElement().normalize();

		} catch (ParserConfigurationException pce) {
			return false;
		} catch (SAXException se) {
			return false;
		} catch (IOException ioe) {
			return false;
		}
		return true;
	}

	/**
	 * @return List<User>: returns list of User objects
	 * Generates list of User objects from input file
	 */
	@Override
	public List<User> getListOfUsers() {
		users = new ArrayList<User>();//List of user objects
		nodeList = document.getElementsByTagName("user");//Retrieve each use node

		//Loop through each user node and create new User object
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				element = (Element) node;
				users.add(new User(Integer.valueOf(element.getElementsByTagName("userid").item(0).getTextContent()),
						element.getElementsByTagName("firstname").item(0).getTextContent(),
						element.getElementsByTagName("surname").item(0).getTextContent(),
						element.getElementsByTagName("username").item(0).getTextContent(),
						element.getElementsByTagName("type").item(0).getTextContent(),
						element.getElementsByTagName("lastlogintime").item(0).getTextContent()));
			}
		}
		return users;
	}

	/**
	 * @param List<User> users: list of User objects to output
	 * @param String outputFileName: name of output file
	 * Creates output file and writes user attributes from list of User objects
	 **/
	@Override
	public void writeFile(List<User> users, String outputFileName) {
		this.users = users;//List of User objects
		xmlFileName = outputFileName;//Output file
		itr = users.iterator();//To iterate through list of User objects

		document = documentBuilder.newDocument();
		rootElement = document.createElement("users");
		document.appendChild(rootElement);

		//Loop through users list and write to file
		while (itr.hasNext()) {
			user = (User) itr.next();

			xmlUser = document.createElement("user");
			rootElement.appendChild(xmlUser);

			element = document.createElement("userid");
			element.appendChild(document.createTextNode(Integer.toString(user.userID)));
			xmlUser.appendChild(element);

			element = document.createElement("firstname");
			element.appendChild(document.createTextNode(user.firstName));
			xmlUser.appendChild(element);

			element = document.createElement("surname");
			element.appendChild(document.createTextNode(user.secondName));
			xmlUser.appendChild(element);

			element = document.createElement("username");
			element.appendChild(document.createTextNode(user.userName));
			xmlUser.appendChild(element);

			element = document.createElement("type");
			element.appendChild(document.createTextNode(user.userType));
			xmlUser.appendChild(element);

			element = document.createElement("lastlogintime");
			element.appendChild(document.createTextNode(user.lastLoginTime));
			xmlUser.appendChild(element);
		}

		try {
			//Transform for XML output
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			domSource = new DOMSource(document);
			streamResult = new StreamResult(new File(xmlFileName));
			transformer.transform(domSource, streamResult);

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

}
