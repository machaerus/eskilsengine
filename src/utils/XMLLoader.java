package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLLoader {
	
	private static XMLLoader uniqInstance;
	private XMLLoader() {}
	public static synchronized XMLLoader getInstance() {
	    if (uniqInstance == null) {
	      uniqInstance = new XMLLoader();
	      //System.out.println("XMLLoader: pobieramy nową instancję xmla");
	    }
	    //System.out.println("XMLLoader: pobieramy istniejącą instancję xmla");
	    return uniqInstance;
	}

	public Document loadDocument(String documentName) throws ParserConfigurationException, SAXException, IOException {
		File sceneFile = new File(documentName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(sceneFile);
	}
	
	// returns first matched child of a given root node
	public Node getChildByName(Node root, String name) {
		NodeList children = root.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			if(children.item(i).getNodeName() == name) {
				return children.item(i);
			}
		}
		return null;
	}
	
	// returns all matched children of a given root node
	public ArrayList<Node> getChildrenByName(Node root, String name) {
		NodeList children = root.getChildNodes();
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i = 0; i < children.getLength(); i++) {
			if(children.item(i).getNodeName() == name) {
				nodes.add(children.item(i));
			}
		}
		return nodes;
	}
	
	// removes leading (& trailing?) whitespaces
	public String normalize(String str) {
		str = str.replaceAll("^\\s+", "");
		str = str.replaceAll("\\s+$", "");
		str = str.replaceAll("\\s+", " ");
		return str;
	}

}
