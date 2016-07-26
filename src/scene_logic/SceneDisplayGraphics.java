package scene_logic;

import scene_structure.*;

public class SceneDisplayGraphics extends SceneDisplay {

	SceneDisplayGraphics(Scene scene) {
		super(scene);
	}

	public void background() {

	}

	public void statement(Event event) {
		String statement = parseStringVariables(event.getParameter("value"));
	}

	public void narration(Event event) {
		String narration = parseStringVariables(event.getParameter("value"));
	}

	public void animation(Event event) {

	}

	public void alternative(Alternative alternative) {

	}
	
}
