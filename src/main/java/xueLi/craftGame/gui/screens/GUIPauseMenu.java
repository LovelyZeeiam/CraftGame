package xueLi.craftGame.gui.screens;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import xueLi.craftGame.gui.GUIRenderer;
import xueLi.craftGame.gui.GUIScreen;
import xueLi.craftGame.gui.animation2D.Animation2D;
import xueLi.craftGame.gui.widgets.GUITextView;

public class GUIPauseMenu extends GUIScreen {
	
	private GUITextView tv;
	
	private Animation2D ani;
	
	public GUIPauseMenu() {
		super(false);
		GUIRenderer.shader.use();
		tv = new GUITextView("Game Paused",new Vector2f(0,50),new Vector4f(0,0,0,0));
		this.add(tv);
		
		
		GUIRenderer.shader.unbind();
		
	}

	
	@Override
	public void update() {
		
		
	}

}
