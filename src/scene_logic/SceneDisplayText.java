package scene_logic;

import scene_structure.*;

public class SceneDisplayText extends SceneDisplay {

	SceneDisplayText(Scene scene) {
		super(scene);
	}

	public void background() {
		System.out.println(":: SCENA "+scene.getID()+" — "+scene.getName());
		System.out.println(":: "+scene.getDescription());
		System.out.println("\n");
	}
	
	public void statement(Event event) {
		String statement = parseStringVariables(event.getParameter("value"));
		System.out.println(event.getDescription());
		System.out.println("["+event.getParameter("character")+":] "+statement);
	}
	
	public void narration(Event event) {
		String narration = parseStringVariables(event.getParameter("value"));
		System.out.println(narration);
	}
	
	public void animation(Event event) {
		String description = parseStringVariables(event.getDescription());
		System.out.println(description+"\n");
	}
	
	public void alternative(Alternative alternative) {
		String head = parseStringVariables(alternative.getHead());
		System.out.println("\n:: "+head);
		Choice choice = null;
		for(int i = 0; true; i++) {
			choice = alternative.getChoice(i);
			if(choice == null) break;
			else {
				System.out.println((i+1)+". "+choice.getName());
			}
		}
	}
	
}
