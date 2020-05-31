package xueLi.craftGame.gui.guis;

import xueLi.craftGame.gui.Color;
import xueLi.craftGame.gui.GUI;
import xueLi.craftGame.gui.GuiTextView;
import xueLi.craftGame.utils.Display;

import static org.lwjgl.nanovg.NanoVG.*;

public class GuiPauseMenu extends GUI {
	
	private GuiTextView pausedTextView;

	public GuiPauseMenu() {
		super("Game paused");
		
		pausedTextView = new GuiTextView("Game Paused", "System", 20, Color.rgba(255, 255, 255, 255),NVG_ALIGN_CENTER | NVG_ALIGN_TOP, Display.d_width / 2, 0, 100, 20);
		super.addWidget(pausedTextView);
		
	}

	@Override
	public void update() {
		
		
	}

	@Override
	public void sizedUpdate(float width, float height) {
		pausedTextView.x = width / 2;
		
	}
	
	

}
