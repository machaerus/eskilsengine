package scene_structure;
import java.util.ArrayList;


public class Choice {
	
	private String name;
	private ArrayList<Offshoot> offshoots;
	private int offshootCounter;
	
	public Choice(
			String name,
			ArrayList<Offshoot> offshoots)
	{
		offshootCounter = 0;
		this.name = name;
		this.offshoots = offshoots;
	}
	
	public String getName() {
		return name;
	}
	
	public Offshoot getNextOffshoot() {
		if(offshootCounter < offshoots.size()) {
			return offshoots.get(offshootCounter++);
		} else {
			return null;
		}
	}

}
