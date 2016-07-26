package scene_structure;
import java.util.ArrayList;


public class FinalModule extends AbstractModule {
	
	private Offshoot offshoot;
	
	public FinalModule(
			int id,
			String description,
			ArrayList<Event> eventList,
			OffshootGotoScene offshoot) 
	{
		super(id, description, eventList);
		this.offshoot = offshoot;
		this.isFinal = true;
	}
	
	public Offshoot getOffshoot() {
		return offshoot;
	}

}
