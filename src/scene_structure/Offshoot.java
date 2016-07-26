package scene_structure;
import java.util.ArrayList;
import java.util.Hashtable;


public abstract class Offshoot {

	/*
		possible types:
		GLOBAL_VARIABLE_CHANGE
		LOCAL_VARIABLE_CHANGE
		GOTO_MODULE
		GOTO_SCENE
	*/

	protected Hashtable<String,String> parameters;
	protected ArrayList<Condition> conditions;
	private int conditionCounter;
	
	public Offshoot(String type, ArrayList<Condition> conditions) {
		conditionCounter = 0;
		parameters = new Hashtable<String,String>();
		parameters.put("type", type);
		this.conditions = conditions;
	}
	
	public String getParameter(String par) {
		return parameters.get(par);
	}

	public Hashtable<String,String> getParameters() {
		return parameters;
	}
	
	public Condition getNextCondition() {
		if(conditionCounter < conditions.size()) {
			return conditions.get(conditionCounter++);
		} else {
			return null;
		}
	}
	
	
}
