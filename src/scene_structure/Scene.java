package scene_structure;
import java.util.ArrayList;

public class Scene {

	private int id;
	private String name;
	private String description;
	private GlobalVariables globals;
	private LocalVariables locals;
	private ArrayList<Character> characters;
	private ArrayList<AbstractModule> modules;
	private int initialModule;
	
	public Scene(
			int id,
			String name,
			String description,
			GlobalVariables globals,
			LocalVariables locals,
			ArrayList<Character> characters,
			ArrayList<AbstractModule> modules,
			int initialModule) 
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.globals = globals;
		this.locals = locals;
		this.characters = characters;
		this.modules = modules;
		this.initialModule = initialModule;
	}
	
	// TODO: Bardziej spójne API, zgodne z zasadą enkapsulacji
	// A może nie?
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public GlobalVariables getGlobals() {
		return globals;
	}
	
	public LocalVariables getLocals() {
		return locals;
	}
	
	public ArrayList<Character> getCharacters() {
		return characters;
	}
	
	public Character getCharacter(String charName) {
		for(Character character : characters) {
			if(character.getName().equals(charName)) return character;
		}
		return null;
	}
	
	public ArrayList<AbstractModule> getModules() {
		return modules;
	}
	
	public int getInitialModuleID() {
		return initialModule;
	}

	public AbstractModule getModule(int n) {
		AbstractModule module = null;
		for( AbstractModule m : modules ) {
			if( m.getID() == n ) {
				module = m;
				break;
			}
		}
		return module;
	}

	public AbstractModule getInitialModule() {
		return getModule(this.initialModule);
	}
	
	// method used to check variables
	public Boolean check(String key, String value) {
		String var;
		var = getGlobals().getVar(key);
		if(var != null) {
			if(var.equals(value)) return true;
			else return false;
		} else {
			var = getLocals().getVar(key);
			if(var != null) {
				if(var.equals(value)) return true;
				else return false;
			} else return false;
		}
	}
	
	
	
}
