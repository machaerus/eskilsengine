package scene_structure;
import java.util.ArrayList;
import java.util.HashMap;


public class Event {
	
	/*
	 * MOŻLIWE TYPY ORAZ ICH PARAMETRY DLA OBIEKTÓW TYPU EVENT:
	 * 
	 * STATEMENT
	 * 	- target
	 * 	- value
	 * 
	 * GLOBAL_VAR_CHANGE
	 * 	- name
	 * 	- value
	 * 
	 * LOCAL_VAR_CHANGE
	 * 	- name
	 * 	- value
	 * 
	 * CHARACTER_STATE
	 * 	- character
	 * 	- name
	 * 	- value
	 * 
	 * NARRATION
	 * 	- value
	 * 	- ?
	 * 
	 * ANIMATION
	 * 	- ?
	 * 
	 */

	protected String type;
	protected String description;
	private LocalVariables parameters;
	private ArrayList<Condition> conditions;
	private int conditionCounter;
	
	public Event(String type, String description, ArrayList<Condition> conditions, HashMap<String,String> prmts) {
		this.type = type;
		this.description = description;
		this.conditions = conditions;
		parameters = new LocalVariables(prmts);
		conditionCounter = 0;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getParameter(String name) {
		return parameters.getVar(name);
	}
	
	public Condition getNextCondition() {
		if(conditionCounter < conditions.size()) {
			return conditions.get(conditionCounter++);
		} else {
			return null;
		}
	}
	
}
