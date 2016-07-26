package scene_structure;
import java.util.ArrayList;


public abstract class AbstractModule {

	protected int id;
	protected String description;
	protected ArrayList<Event> eventList;
	protected int eventCounter;
	protected Boolean isFinal;
	
	public AbstractModule(int id,
			String description,
			ArrayList<Event> eventList) 
	{
		eventCounter = 0;
		this.id = id;
		this.description = description;
		this.eventList = eventList;
	}
	
	public int getID() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Event getNextEvent() {
		if(eventCounter < eventList.size()) {
			return eventList.get(eventCounter++);
		} else {
			return null;
		}
	}

	public Boolean isFinal() {
		return isFinal;
	}
}
