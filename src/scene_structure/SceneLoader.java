package scene_structure;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import utils.XMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class SceneLoader {
	
	// singleton class setup
	// ----------------------------------------------------------------

	private static SceneLoader uniqInstance;
	private XMLLoader xml;
	private SceneLoader() {
		xml = XMLLoader.getInstance();
	}
	public static synchronized SceneLoader getInstance() {
		//System.out.println("Pobieranie instancji SceneLoader...");
	    if (uniqInstance == null) {
	      uniqInstance = new SceneLoader();
	    }
	    return uniqInstance;
	}
	
	
	// character building method
	// ----------------------------------------------------------------
	
	private Character buildCharacter(Node root) {
		
		String name = null;
		HashMap<String,String> options;
		
		if(root.getAttributes().item(0).getNodeName() == "name") {
			name = root.getAttributes().item(0).getNodeValue();
		} else {
			System.err.println("Błąd składni w pliku wejściowym - błędna lista atrybutów znacznika char.");
			System.exit(-1);
		}
		
		options = new HashMap<String,String>();
		ArrayList<Node> optionNodes = xml.getChildrenByName(root,"option");
		String optionName = null, optionValue = null;
		for(Node option : optionNodes) {
			if(option.getAttributes().item(0).getNodeName() == "name") {
				optionName = option.getAttributes().item(0).getNodeValue();
			} else {
				System.err.println("Błąd składni w pliku wejściowym - błędna lista atrybutów znacznika option.");
				System.exit(-1);
			}
			optionValue = xml.normalize(option.getTextContent());
			options.put(optionName, optionValue);
		}
		
		Character character = new Character(name,options);
		return character;
	}
	
		
	
	// condition building method
	// ----------------------------------------------------------------
	
	private Condition buildCondition(Node root) {
		
		String name;
		String value;
		
		name = xml.normalize(xml.getChildByName(root,"name").getTextContent());
		value = xml.normalize(xml.getChildByName(root,"value").getTextContent());
		
		Condition condition = new Condition(name, value);
		return condition;
	}
	
	
	
	// offshoot building method
	// ----------------------------------------------------------------
	
	private Offshoot buildOffshoot(Node root) {
		
		Offshoot offshoot = null;
		String type = null;
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		String name;
		String value;
		String moduleNo;
		String sceneNo;
		
		if(root.getAttributes().item(0).getNodeName() == "type") {
			type = root.getAttributes().item(0).getNodeValue();
		} else {
			System.err.println("Błąd składni w pliku wejściowym - błędna lista atrybutów znacznika offshoot.");
			System.exit(-1);
		}
		
		ArrayList<Node> conditionNodes = xml.getChildrenByName(root,"condition");
		for(Node node : conditionNodes) {
			conditions.add(buildCondition(node));
		}
		
		// checking different types of offshoot
		
		if(type.equals("GLOBAL_VARIABLE_CHANGE")) {
			name = xml.normalize(xml.getChildByName(root, "name").getTextContent());
			value = xml.normalize(xml.getChildByName(root, "value").getTextContent());
			offshoot = new OffshootVariable(type,conditions,name,value);
		}
		else if(type.equals("LOCAL_VARIABLE_CHANGE")) {
			name = xml.normalize(xml.getChildByName(root,"name").getTextContent());
			value = xml.normalize(xml.getChildByName(root,"value").getTextContent());
			offshoot = new OffshootVariable(type,conditions,name,value);
		}
		else if(type.equals("GOTO_MODULE")) {
			moduleNo = xml.normalize(xml.getChildByName(root,"moduleNo").getTextContent());
			offshoot = new OffshootGotoModule(type,conditions,moduleNo);
		}
		else if(type.equals("GOTO_SCENE")) {
			sceneNo = xml.normalize(xml.getChildByName(root,"sceneNo").getTextContent());
			moduleNo = xml.normalize(xml.getChildByName(root,"moduleNo").getTextContent());
			offshoot = new OffshootGotoScene(type,conditions,sceneNo,moduleNo);
		}
		else {
			System.err.println("Błąd składni w pliku wejściowym - błędny atrybut znacznika offshoot.");
			System.err.println("Nieznana wartość atrybutu: "+type);
			System.exit(-1);
		}
		
		return offshoot;
	}
	
	
	
	// event building method
	// ----------------------------------------------------------------
	
	private Event buildEvent(Node root) {
		
		Event event = null;
		String type = null;
		String description = null;
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		HashMap<String,String> parameters = new HashMap<String,String>();
		
		if(root.getAttributes().item(0).getNodeName() == "type") {
			type = root.getAttributes().item(0).getNodeValue();
		} else {
			System.err.println("Błąd składni w pliku wejściowym - błędna lista atrybutów znacznika event.");
			System.exit(-1);
		}
		
		description = xml.normalize(xml.getChildByName(root,"description").getTextContent());
		
		ArrayList<Node> conditionNodes = xml.getChildrenByName(root,"condition");
		for(Node node : conditionNodes) {
			conditions.add(buildCondition(node));
		}
		
		ArrayList<Node> parameterNodes = xml.getChildrenByName(root, "parameter");
		String paramType = null, value;
		for(Node node : parameterNodes) {
			if(node.getAttributes().item(0).getNodeName() == "type") {
				paramType = node.getAttributes().item(0).getNodeValue();
			} else {
				System.err.println("Błąd składni w pliku wejściowym - błędna lista atrybutów znacznika parameter.");
				System.exit(-1);
			}
			value = xml.normalize(node.getTextContent());
			parameters.put(paramType, value);
		}
		
		if(type.equals("GLOBAL_VARIABLE_CHANGE")
		|| type.equals("LOCAL_VARIABLE_CHANGE")
		|| type.equals("STATEMENT")
		|| type.equals("CHARACTER_STATE")
		|| type.equals("NARRATION")
		|| type.equals("ANIMATION")) {
			event = new Event(type, description, conditions, parameters);
		}
		else {
			System.err.println("Błąd składni w pliku wejściowym - błędny atrybut znacznika event.");
			System.err.println("Nieznana wartość atrybutu: "+type);
			System.exit(-1);
		}
		
		return event;
	}
	
	
	
	// choice building method
	// ----------------------------------------------------------------
	
	private Choice buildChoice(Node root) {
		
		Choice choice = null;
		String name;
		ArrayList<Offshoot> offshoots = new ArrayList<Offshoot>();
		
		name = xml.normalize(xml.getChildByName(root,"name").getTextContent());
		
		ArrayList<Node> offshootNodes = xml.getChildrenByName(root,"offshoot");
		for(Node node : offshootNodes) {
			offshoots.add(buildOffshoot(node));
		}
		
		choice = new Choice(name, offshoots);
		return choice;
	}
	
	
	
	// alternative building method
	// ----------------------------------------------------------------
	
	private Alternative buildAlternative(Node root) {
		
		Alternative alternative = null;
		String head;
		ArrayList<Choice> choices = new ArrayList<Choice>();
		
		head = xml.normalize(xml.getChildByName(root,"head").getTextContent());
		
		ArrayList<Node> choiceNodes = xml.getChildrenByName(root,"choice");
		for(Node node : choiceNodes) {
			choices.add(buildChoice(node));
		}
		
		alternative = new Alternative(head, choices);
		return alternative;
	}
	
	
	
	// module building method
	// ----------------------------------------------------------------
	
	private AbstractModule buildModule(Node root, int isFinal) {
		
		AbstractModule module = null;
		int id = 0;
		String description = "";
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		
		if(root.getAttributes().item(0).getNodeName() == "id") {
			id = Integer.parseInt(root.getAttributes().item(0).getNodeValue());
		} else {
			System.err.println("Błąd składni w pliku wejściowym - błędna lista atrybutów znacznika module.");
			System.exit(-1);
		}
		
		// description - optional!
		Node desc = xml.getChildByName(root,"description");
		if(desc != null) description = xml.normalize(desc.getTextContent());
		
		// eventList		
		Node tmp = xml.getChildByName(root, "eventList");
		ArrayList<Node> eventNodes = xml.getChildrenByName(tmp,"event");
		for(Node node : eventNodes) {
			eventList.add(buildEvent(node));
		}
		
		// if module -- alternative
		if(isFinal == 0) {
			Node alternativeNode = xml.getChildByName(root, "alternative");
			Alternative alternative = buildAlternative(alternativeNode);
			module = new Module(id, description, eventList, alternative);
		}
		
		// if finalModule -- offshoot
		else if(isFinal == 1) {
			Node offshootNode = xml.getChildByName(root, "offshoot");
			OffshootGotoScene offshoot = (OffshootGotoScene) buildOffshoot(offshootNode);
			// this casting WILL work, since buildOffshoot() takes care of
			// building always the right type of Offshoot!
			module = new FinalModule(id, description, eventList, offshoot);
		}
		
		// xD
		else {
			System.err.println("Nieprawidłowy typ modułu!");
			System.exit(-1);
		}
		
		return module;
	}
	
	
	
	// the main building method
	// ----------------------------------------------------------------
	
	public Scene Build(String sceneID, int initialModule) {
		
		Scene scene;
		int id;
		String name;
		String description;
		GlobalVariables globals;
		LocalVariables locals;
		ArrayList<Character> characters;
		ArrayList<AbstractModule> modules;
		
		//System.out.println("huj"); //1

		// load global variables
		globals = new GlobalVariables();
		try {
			globals.load();
		} catch (IOException e1) {
			System.err.println("Błąd podczas wczytywania zmiennych globalnych.");
			e1.printStackTrace();
		}
		
		
		//////////////////////////////////////////////
		//											//
		//	LOAD AND PARSE XML						//
		//											//
		//////////////////////////////////////////////
		
		//System.out.println("huj"); //2

		Document doc = null;
		
		try {
			doc = xml.loadDocument("scenes/"+sceneID+".scn");
		} catch (ParserConfigurationException e) {
			System.err.println("Błąd krytyczny SceneLoader: nie można załadować pliku "+sceneID+".scn. " +
				"Błąd konfiguracji parsera.");
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.println("Błąd krytyczny SceneLoader: nie można załadować pliku "+sceneID+".scn. " +
					"Plik nie istnieje lub jest niepoprawny.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Błąd krytyczny SceneLoader: nie można załadować pliku "+sceneID+".scn. " +
					"Plik nie istnieje lub jest niepoprawny.");
			e.printStackTrace();
		}
		
		if(doc == null) {
			System.out.println("Przerywanie pracy programu...");
			System.exit(-1);
		}
		
		Element ROOT = doc.getDocumentElement();
		
		// normalize the document
		ROOT.normalize();
		
		
		//////////////////////////////////////////////
		//											//
		//	EXTRACT DATA & BUILD OBJECTS			//
		//											//
		//////////////////////////////////////////////
		
		//System.out.println("huj");//3

		id = Integer.parseInt(ROOT.getAttribute("id"));
		name = ROOT.getAttribute("name");
		
		// description
		description = xml.normalize(xml.getChildByName(ROOT,"description").getTextContent());
		
		// characterList
		characters = new ArrayList<Character>();
		Node characterListNode = xml.getChildByName(ROOT,"characterList");
		ArrayList<Node> charNodes = xml.getChildrenByName(characterListNode,"char");
		for(Node character : charNodes) {
			characters.add(buildCharacter(character));
		}
		
		//System.out.println("huj");//4

		// localVars
		locals = new LocalVariables();
		String varName = null, varValue = null;
		Node localVarsNode = xml.getChildByName(ROOT,"localVars");
		ArrayList<Node> varNodes = xml.getChildrenByName(localVarsNode,"var");
		for(Node var : varNodes) {
			if(var.getAttributes().item(0).getNodeName() == "name") {
				varName = var.getAttributes().item(0).getNodeValue();
			} else {
				System.err.println("Błąd składni w pliku wejściowym - błędna lista atrybutów znacznika option.");
				System.exit(-1);
			}
			varValue = xml.normalize(var.getTextContent());
			locals.addVar(varName, varValue);
		}

		//System.out.println("huj");//5
		
		// modules and final modules
		modules = new ArrayList<AbstractModule>();
		ArrayList<Node> moduleNodes = xml.getChildrenByName(ROOT,"module");
		ArrayList<Node> finalModuleNodes = xml.getChildrenByName(ROOT,"finalModule");
		for(Node node : moduleNodes) {
			modules.add(buildModule(node,0));
		}
		for(Node node : finalModuleNodes) {
			modules.add(buildModule(node,1));
		}		
		
		/* 	structure:
		 * 	----------
		 * 
		 * 	description
		 * 	characterList
		 * 		char
		 * 			option
		 * 			...
		 * 		...
		 * 	localVars
		 * 		var
		 * 		...
		 *	module
		 *		description
		 *		eventList
		 *			event
		 *				condition
		 *					description
		 *					name
		 *					value
		 *				...
		 *				parameter
		 *				...
		 *			...
		 *		alternative / offshootList
		 *	...
		 * 
		 */

		// building finished
		
		
		//////////////////////////////////////////////
		//											//
		//	CREATE NEW SCENE & RETURN IT			//
		//											//
		//////////////////////////////////////////////
		
		//System.out.println("huj");//6

		scene = new Scene(
				id, 
				name, 
				description, 
				globals, 
				locals, 
				characters, 
				modules,  
				initialModule);

		//System.out.println("huj");//7

		return scene;
	}
  	
	
}
