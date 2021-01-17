package xueli.craftgame.view;

import xueli.craftgame.CraftGame;
import xueli.craftgame.world.WorldLogic;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.Vector;
import xueli.gamengine.view.GuiColor;

import static org.lwjgl.nanovg.NanoVG.*;

public class HUDView extends InGameHUDView {
	
	public HUDView(WorldLogic logic, CraftGame game) {
		super(logic, game);

	}

	@Override
	public void onClick(float x, float y, int button) {
		
	}

	@Override
	public void onScroll(float x, float y, float scroll) {

	}

	@Override
	public void draw(long nvg) {
		float pointerY = 0;
		float fontSize = 20 * game.getDisplay().getScale();
		
		// 坐标
		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, "game");
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		nvgFillColor(nvg, GuiColor.WHITE);
		Vector clientPlayerPosVector = logic.getClientPlayer().pos;
		float textWidth = nvgText(nvg, 2 * game.getDisplay().getScale(),pointerY, 
				game.getLangManager().getStringFromLangMap("#hud.ingame.position") + (int)clientPlayerPosVector.x + ", " + (int)clientPlayerPosVector.y + ", " + (int)clientPlayerPosVector.z);
		
		// 背景
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, textWidth, fontSize);
		nvgFillColor(nvg, GuiColor.TRANSPARENT_BLACK);
		nvgFill(nvg);
		
		nvgFillColor(nvg, GuiColor.WHITE);
		nvgText(nvg, 2 * game.getDisplay().getScale(),pointerY, 
				game.getLangManager().getStringFromLangMap("#hud.ingame.position") + (int)clientPlayerPosVector.x + ", " + (int)clientPlayerPosVector.y + ", " + (int)clientPlayerPosVector.z);

		// FPS
		float fpsTextWidth = nvgText(nvg, 2 * game.getDisplay().getScale(),pointerY + fontSize,
				game.getLangManager().getStringFromLangMap("#hud.ingame.fps") + Time.fps);

		// 背景
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, fontSize, fpsTextWidth, fontSize);
		nvgFillColor(nvg, GuiColor.TRANSPARENT_BLACK);
		nvgFill(nvg);

		nvgFillColor(nvg, GuiColor.WHITE);
		nvgText(nvg, 2 * game.getDisplay().getScale(),pointerY + fontSize,
				game.getLangManager().getStringFromLangMap("#hud.ingame.fps") + Time.fps);


	}

}
