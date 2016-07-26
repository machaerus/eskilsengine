package scene_structure;
import java.util.ArrayList;


public class OffshootVariable extends Offshoot {
	
	public OffshootVariable(
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
