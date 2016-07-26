package scene_structure;
import java.util.ArrayList;


public class OffshootGotoScene extends Offshoot {
	
	public OffshootGotoScene(
			String type,
			ArrayList<Condition> conditions, 
			String scene,
			String module) 
	{
		super(type, conditions);
		parameters.put("sceneNo", scene);
		parameters.put("moduleNo", module);
	}

}
