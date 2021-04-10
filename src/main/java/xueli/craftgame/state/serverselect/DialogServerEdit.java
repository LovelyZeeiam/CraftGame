package xueli.craftgame.state.serverselect;

import xueli.craftgame.state.StateServerSelect;
import xueli.game.Game;
import xueli.game.renderer.widgets.Button;
import xueli.game.renderer.widgets.IListEntry;
import xueli.game.renderer.widgets.IWidget;
import xueli.utils.eval.EvalableFloat;

import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.glfw.GLFW;

public class DialogServerEdit extends IWidget {
	
	private static EvalableFloat test_button_x = new EvalableFloat("win_width / 2 - 100 * scale");
	private static EvalableFloat test_button_y = new EvalableFloat("win_height / 2 - 25 * scale");
	private static EvalableFloat test_button_width = new EvalableFloat("200 * scale");
	private static EvalableFloat test_button_height = new EvalableFloat("50 * scale");
	
	private StateServerSelect state;
	private ListEntryServer entry;
	
	private Button testButton;

	public DialogServerEdit(StateServerSelect state, IListEntry entry) {
		super(0,0,Game.INSTANCE_GAME.getWidth(), Game.INSTANCE_GAME.getHeight());
		this.state = state;
		this.entry = (ListEntryServer) entry;
		
		testButton = new Button(test_button_x, test_button_y, test_button_width, test_button_height, "Exit", new EvalableFloat("10.0 * scale"), true);
		
	}

	@Override
	public void stroke(long nvg, String fontName) {
		testButton.stroke(nvg, fontName);
		
	}

	@Override
	public void update() {
		testButton.update();
		
		if(Game.INSTANCE_GAME.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			if(testButton.canBePressed()) {
				state.dialogExit("Hey!");
				
			}
			
		}
		
	}
	
	@Override
	public void size(int w, int h) {
		testButton.size(w, h);
		
	}

}
