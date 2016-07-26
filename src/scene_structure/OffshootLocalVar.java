package scene_structure;
import java.util.ArrayList;


public class OffshootLocalVar extends Offshoot {
	
	public OffshootLocalVar(
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
