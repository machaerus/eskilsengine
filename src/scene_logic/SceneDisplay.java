package scene_logic;

import scene_structure.*;

public abstract class SceneDisplay {
	
	protected Scene scene;

	SceneDisplay(Scene scene) {
		this.scene = scene;
	}

	protected String parseStringVariables(String s) {
		// TODO: do something
		return s;
	}

	public abstract void background();
	public abstract void statement(Event event);
	public abstract void narration(Event event);
	public abstract void animation(Event event);
	public abstract void alternative(Alternative alternative);

}
