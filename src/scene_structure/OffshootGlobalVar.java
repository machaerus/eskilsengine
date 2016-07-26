package scene_structure;
import java.util.ArrayList;


public class OffshootGlobalVar extends Offshoot{
	
	public OffshootGlobalVar(
			String type,
			ArrayList<Condition> conditions,
			String name,
			String value) 
	{
		super(type, conditions);
		parameters.put("name", name);
		parameters.put("value", value);
	}

}
