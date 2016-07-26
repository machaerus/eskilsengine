package scene_logic;

import java.util.Scanner;
import java.util.Hashtable;
import scene_structure.*;

public class SceneLogic {
	
	private SceneDisplay DISPLAY;
	private Boolean isGraphicsOn;
	private Scene scene;
	private Scanner sc;
	
	public SceneLogic(Scene scene, Boolean isGraphicsOn) {
		this.scene = scene;
		this.isGraphicsOn = isGraphicsOn;
		this.sc = new Scanner(System.in);

		if(isGraphicsOn) {
			DISPLAY = new SceneDisplayGraphics(scene);
		} else {
			DISPLAY = new SceneDisplayText(scene);
		}
	}

	public int[] playScene() {
		int[] nextScene = {0,0};
		// odgrywamy wszystko po kolei.
		DISPLAY.background();
		AbstractModule next = null;
		int nextID = scene.getInitialModuleID();
		while(true) {
			next = scene.getModule(nextID);
			if( next.isFinal() ) {
				nextScene = playFinalModule((FinalModule)next);
				break;
			} else {
				nextID = playModule((Module)next);
			}
		}
		System.out.println("nextScene scene: "+nextScene[0]+", nextScene module: "+nextScene[1]);
		return nextScene;
	}
	
	private int playModule(Module module) {
		Event e = module.getNextEvent();
		while(e != null) {
			playEvent(e);			
			e = module.getNextEvent();
		}
		Alternative alternative = module.getAlternative();
		DISPLAY.alternative(alternative);
		Choice choice = alternative.getChoice(getInput()-1);
		Offshoot offshoot = choice.getNextOffshoot();
		Hashtable<String,String> results = null;
		while(offshoot != null) {
			results = playOffshoot(offshoot);
			// zapamiętujemy wynik tylko ostatniego z offshootów - to powinien być GOTO
			// dobrze byłoby potem to jakoś poprawić
			offshoot = choice.getNextOffshoot();
		}
		return Integer.parseInt(results.get("moduleNo"));
	}

	private int[] playFinalModule(FinalModule module) {
		Event e = module.getNextEvent();
		while(e != null) {
			playEvent(e);			
			e = module.getNextEvent();
		}
		Offshoot offshoot = module.getOffshoot();
		int[] results = {0,0};
		results[0] = Integer.parseInt(offshoot.getParameter("sceneNo"));
		results[1] = Integer.parseInt(offshoot.getParameter("moduleNo"));

		return results;
	}
	
	private Hashtable<String,String> playOffshoot(Offshoot offshoot) {
		Condition condition;
		Boolean occur = true;
		Hashtable<String,String> results = null;
		while(true) {
			condition = offshoot.getNextCondition();
			// if no conditions or every condition is met
			if(condition == null) break;
			// else: check condition, if false - set occur to false & end the loop
			if(!scene.check(condition.getName(), condition.getValue())) {
				occur = false;
				break;
			}
		}
		if(occur) {	// jeśli rezultat zachodzi (jeśli spełnił warunki)
			String type = offshoot.getParameter("type");
			if(type.equals("GLOBAL_VARIABLE_CHANGE")) {	
				if(! scene.getGlobals()
						.modifyVar(
							offshoot.getParameter("name"), 
							offshoot.getParameter("value")) 
				) {
					scene.getGlobals().addVar(
							offshoot.getParameter("name"), 
							offshoot.getParameter("value"));
				}
				results = offshoot.getParameters();
			} else if(type.equals("LOCAL_VARIABLE_CHANGE")) {
				if(! scene.getLocals()
						.modifyVar(
							offshoot.getParameter("name"), 
							offshoot.getParameter("value")) 
				) {
					scene.getLocals().addVar(
							offshoot.getParameter("name"), 
							offshoot.getParameter("value"));
				}
				results = offshoot.getParameters();
			} else if(type.equals("GOTO_MODULE")) {
				results = offshoot.getParameters();
			} else if(type.equals("GOTO_SCENE")) {
				results = offshoot.getParameters();
			}
		}
		return results;
	}
	
	private void playEvent(Event event) {		
		Condition condition;
		Boolean occur = true;
		while(true) {
			condition = event.getNextCondition();
			// if no conditions or every condition is met
			if(condition == null) break;
			// else: check condition, if false - set occur to false & end the loop
			if(!scene.check(condition.getName(), condition.getValue())) {
				occur = false;
				break;
			}
		}
		if(occur) {
			String type = event.getType();
			if(type.equals("GLOBAL_VARIABLE_CHANGE")) {	
				if(! scene.getGlobals().modifyVar(event.getParameter("name"), event.getParameter("value")) ) {
					scene.getGlobals().addVar(event.getParameter("name"), event.getParameter("value"));
				}
			} else if(type.equals("LOCAL_VARIABLE_CHANGE")) {
				if(! scene.getLocals().modifyVar(event.getParameter("name"), event.getParameter("value")) ) {
					scene.getLocals().addVar(event.getParameter("name"), event.getParameter("value"));
				}
			} else if(type.equals("STATEMENT")) {
				DISPLAY.statement(event);
			} else if(type.equals("CHARACTER_STATE")) {
				String ch = event.getParameter("character");
				String stateName = event.getParameter("name");
				String stateValue = event.getParameter("value");
				scene.getCharacter(ch).changeState(stateName, stateValue);
			} else if(type.equals("NARRATION")) {
				DISPLAY.narration(event);
			} else if(type.equals("ANIMATION")) {
				DISPLAY.animation(event);
			}
		}
	}

	private int getInput() {
		if(isGraphicsOn) {
			// some magic to get keyboard input
			return 0;
		} else {
			return sc.nextInt();
		}
	}
}
