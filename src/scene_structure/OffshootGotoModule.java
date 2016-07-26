package scene_structure;
import java.util.ArrayList;


public class OffshootGotoModule extends Offshoot {

	public OffshootGotoModule(
			String type,
			ArrayList<Condition> conditions, 
			String moduleNo) 
	{
		super(type,conditions);
		parameters.put("moduleNo", moduleNo);
	}
	
}
