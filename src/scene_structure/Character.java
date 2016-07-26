package scene_structure;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import utils.XMLLoader;


public class Character {
	
	private String name;
	private LocalVariables options;
	
	public Character(
			String name, 
			HashMap<String,String> optionsOverride) 
	{
		this.name = name;
		options = new LocalVariables();

		try {
			load();
		} catch (ParserConfigurationException e) {
			System.err.println("Błąd krytyczny Character.load(): nie można załadować pliku "+name+".chr. " +
				"Błąd konfiguracji parsera.");
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.println("Błąd krytyczny Character.load(): nie można załadować pliku "+name+".chr. " +
					"Plik nie istnieje lub jest niepoprawny.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Błąd krytyczny Character.load(): nie można załadować pliku "+name+".chr. " +
					"Plik nie istnieje lub jest niepoprawny.");
			e.printStackTrace();
		}
		
		// swap original variable values with overriden ones
		// or add new if the key is not found
		if(!(optionsOverride == null || optionsOverride.isEmpty())){
			// if we have anything inside
			for(String key : optionsOverride.keySet()) {
				if(!options.modifyVar(key, optionsOverride.get(key)))
					options.addVar(key, optionsOverride.get(key));
			}
		}
	}
	
	public void load() throws ParserConfigurationException, SAXException, IOException {
		// load data from $(name).chr
		XMLLoader xml = XMLLoader.getInstance();
		Document doc = xml.loadDocument("data/"+name+".chr");
		if(doc == null) {
			System.out.println("Nie udało się wczytać pliku "+name+".chr");
			System.exit(-1);
		}
		Element ROOT = doc.getDocumentElement();
		// normalize the document
		ROOT.normalize();
		
		// WARNING: overriding existing field just for teh lulz 
		name = ROOT.getAttribute("name");
		ArrayList<Node> optionNodes = xml.getChildrenByName(ROOT, "option");
		String optionName, optionValue;
		for(Node option : optionNodes) {
			optionName = xml.getChildByName(option, "name").getTextContent();
			optionValue = xml.getChildByName(option, "value").getTextContent();
			options.addVar(optionName, optionValue);
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getState(String key) {
		return options.getVar(key);
	}
	
	public void changeState(String stateName, String stateValue) {
		if(!options.modifyVar(stateName, stateValue))
			options.addVar(stateName, stateValue);
	}

}
