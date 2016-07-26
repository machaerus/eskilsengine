package scene_structure;
import java.util.HashMap;


public abstract class Variables {
	
	protected HashMap<String,String> vars;
	
	Variables() {
		vars = new HashMap<String,String>();
	}
	
	public Boolean addVar(String key, String value) {
		if(vars.containsKey(key)) return false;
		else {
			vars.put(key, value);
			return true;
		}
	}
	
	public String getVar(String key) {
		String value = vars.get(key);
		if(value == null) {
			System.err.println("Błąd klasy Variables: zażądano nieistniejącego klucza "+key);
			System.exit(-1);
		}
		return value;
	}
	
	public Boolean modifyVar(String key, String newValue) {
		if(!vars.containsKey(key)) return false;
		else {
			vars.remove(key);
			vars.put(key, newValue);
			return true;
		}
	}

}
