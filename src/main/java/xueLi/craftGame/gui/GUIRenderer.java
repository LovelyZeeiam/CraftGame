package xueLi.craftGame.gui;

import xueLi.craftGame.utils.Display;

public class GUIRenderer {

	public static GUI currentGui;
	
	public static void setGUI(GUI gui) {
		currentGui = gui;
		if(gui == null)
			Display.setSubtitie(null);
		else
			Display.setSubtitie(gui.title);
	}

	public static void render() {
		if(currentGui != null)
			currentGui.render();
	}
	
	
}
