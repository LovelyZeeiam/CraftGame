package xueli.craftgame.view;

import xueli.craftgame.world.WorldLogic;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.vector.Vector;
import xueli.gamengine.view.GuiColor;

import static org.lwjgl.nanovg.NanoVG.*;

public class HUDView extends InGameView {

	private static final float debugTextSize = 20;
	private static final float minimap_margin = 20;
	private static final float minimap_size = 80;
	private static final float minimap_padding = 3;
	private static final float minimap_player_size = 5;
	private static final float crossbarSize = 20;
	private static final float chatbox_margin_bottom = 40;

	public HUDView(WorldLogic logic) {
		super(logic);

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
		float fontSize = debugTextSize * game.getDisplay().getScale();

		// 坐标
		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, "game");
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		nvgFillColor(nvg, GuiColor.WHITE);
		Vector clientPlayerPosVector = logic.getClientPlayer().pos;
		float textWidth = nvgText(nvg, 2 * game.getDisplay().getScale(), pointerY,
				game.getLangManager().getStringFromLangMap("#hud.ingame.position") + (int) clientPlayerPosVector.x
						+ ", " + (int) clientPlayerPosVector.y + ", " + (int) clientPlayerPosVector.z);

		// 背景
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, textWidth, fontSize);
		nvgFillColor(nvg, GuiColor.TRANSPARENT_BLACK);
		nvgFill(nvg);

		nvgFillColor(nvg, GuiColor.WHITE);
		nvgText(nvg, 2 * game.getDisplay().getScale(), pointerY,
				game.getLangManager().getStringFromLangMap("#hud.ingame.position") + (int) clientPlayerPosVector.x
						+ ", " + (int) clientPlayerPosVector.y + ", " + (int) clientPlayerPosVector.z);

		// FPS
		float fpsTextWidth = nvgText(nvg, 2 * game.getDisplay().getScale(), pointerY + fontSize,
				game.getLangManager().getStringFromLangMap("#hud.ingame.fps") + Time.fps);

		// 背景
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, fontSize, fpsTextWidth, fontSize);
		nvgFillColor(nvg, GuiColor.TRANSPARENT_BLACK);
		nvgFill(nvg);

		nvgFillColor(nvg, GuiColor.WHITE);
		nvgText(nvg, 2 * game.getDisplay().getScale(), pointerY + fontSize,
				game.getLangManager().getStringFromLangMap("#hud.ingame.fps") + Time.fps);

		// 准心
		nvgImagePattern(nvg,
				game.getDisplay().getWidth() / 2.0f - crossbarSize * game.getDisplay().getScale(),
				game.getDisplay().getHeight() / 2.0f - crossbarSize * game.getDisplay().getScale(),
				crossbarSize * game.getDisplay().getScale(),
				crossbarSize * game.getDisplay().getScale(),
				0,
				logic.getNvgTextures().get("ingame.hud.crossbar"),
				1,
				getPaint());
		nvgBeginPath(nvg);
		nvgRoundedRect(nvg, game.getDisplay().getWidth() / 2.0f - crossbarSize * game.getDisplay().getScale(), game.getDisplay().getHeight() / 2.0f - crossbarSize * game.getDisplay().getScale(),crossbarSize * game.getDisplay().getScale(), crossbarSize * game.getDisplay().getScale(), 0);
		nvgFillPaint(nvg, getPaint());
		nvgFill(nvg);

		/*// 小地图 右上角的边框
		float minimap_realMargin = minimap_margin * game.getDisplay().getScale();
		float minimap_realSize = minimap_size * game.getDisplay().getScale();
		float minimap_realPadding = minimap_padding * game.getDisplay().getScale();
		float minimap_real_player_size = minimap_player_size * game.getDisplay().getScale();

		float miniMap_x = game.getDisplay().getWidth() - minimap_realMargin - minimap_realSize;
		float miniMap_y = minimap_realMargin;
		GUIButton.buttonBorder(nvg, miniMap_x, miniMap_y, minimap_realSize, minimap_realSize);

		float minimap_center_x = miniMap_x + minimap_realSize / 2.0f;
		float minimap_center_y = miniMap_y + minimap_realSize / 2.0f;

		// 裁剪
		nvgSave(nvg);
		nvgScissor(nvg, miniMap_x + minimap_realPadding, miniMap_y + minimap_realPadding,
				minimap_realSize - minimap_realPadding * 2, minimap_realSize - minimap_realPadding * 2);

		// 获取玩家所在的坐标
		float playerX = logic.getClientPlayer().pos.x;
		float playerZ = logic.getClientPlayer().pos.z;
		float offsetX = playerX - (int) playerX;
		float offsetZ = playerZ - (int) playerZ;

		// TODO: 在Chunk更新的时候就自己生成一个区块地图以避免每一帧像这样画

		for (int x = ((int) playerX - 50); x < ((int) playerX + 50); x++) {
			for (int z = ((int) playerZ - 50); z < ((int) playerZ + 50); z++) {
				Tile tile = logic.getWorld().getBlock(x, logic.getWorld().getHeight(x, z), z);
				if (tile != null) {
					NVGColor color = tile.getData().getMapColorNVG();

					int blockPlayerXDistance = x - (int) playerX;
					int blockPlayerZDistance = z - (int) playerZ;
					drawPoint(minimap_center_x - offsetX + blockPlayerXDistance,
							minimap_center_y - offsetZ + blockPlayerZDistance, color, nvg);

				}
			}
		}

		// 玩家图标
		drawBox(minimap_center_x - minimap_real_player_size / 2.0f, minimap_center_y - minimap_real_player_size / 2.0f,
				minimap_real_player_size, minimap_real_player_size, GuiColor.BLACK, GuiColor.WHITE, 1.0f, nvg);

		// 取消裁剪
		nvgRestore(nvg);*/
		
		/**
		 * 聊天窗口
		 */
		
		

	}

}
