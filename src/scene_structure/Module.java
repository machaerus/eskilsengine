package scene_structure;
import java.util.ArrayList;


public class Module extends AbstractModule {
	
	private Alternative alternative;
	
	public Module(
			int id,
			String description,
			ArrayList<Event> eventList,
			Alternative alternative) 
	{
		super(id, description, eventList);
		this.alternative = alternative;
		this.isFinal = false;
	}
	
	public Alternative getAlternative() {
		return alternative;
	}
	
	

}
