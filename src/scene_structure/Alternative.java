package scene_structure;
import java.util.ArrayList;


public class Alternative {
	
	private String head;
	private ArrayList<Choice> choices;
	
	public Alternative(String head, ArrayList<Choice> choices) {
		this.head = head;
		this.choices = choices;
	}
	
	public String getHead() {
		return head;
	}
	
	public Choice getChoice(int n) {

		//System.out.println("n: "+n);

		if(n < choices.size()) {
			return choices.get(n);
		} else {
			//System.err.println("choices.size: "+choices.size()+", n: "+n);
			return null;
		}

	}

}
