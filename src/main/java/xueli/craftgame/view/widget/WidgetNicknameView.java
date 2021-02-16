package xueli.craftgame.view.widget;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_RIGHT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCircle;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import com.google.gson.JsonObject;

import xueli.craftgame.CraftGame;
import xueli.gamengine.resource.GuiResource;
import xueli.gamengine.utils.evalable.EvalableFloat;
import xueli.gamengine.view.ViewWidget;

public class WidgetNicknameView extends ViewWidget {

	private static NVGPaint paint = NVGPaint.create();
	private static NVGColor backgroundColor = NVGColor.create();
	
	static {
		NanoVG.nvgRGBAf(0.4f, 0.4f, 0.8f, 0.2f, backgroundColor);
		
	}
	
	private NVGColor text_color;
	
	private static EvalableFloat icon_name_space = new EvalableFloat("8.0 * scale");
	private static EvalableFloat icon_scale_width = new EvalableFloat("3.0 * scale");
	private static float icon_scale = 1.5f;
	
	public WidgetNicknameView(JsonObject object) {
		super(object);
		
		this.text_color = GuiResource.loadColor(object.getAsJsonArray("text_color"));
		
	}

	@Override
	public void draw(long nvg) {
		super.anim_tick();
		
		nvgFontSize(nvg, height.getValue());
		nvgFontFace(nvg, "simhei");
		// 由于直接画靠右的字，最后返回的值还是原来的x坐标，所以先试一试靠左便于测量
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		float textWidth = nvgText(nvg, 0, -114514, CraftGame.INSTACE_CRAFT_GAME.getPlayerStat().getName());
		
		// 然后画背景
		nvgBeginPath(nvg);
		nvgRect(nvg, x.getValue() - textWidth - height.getValue() / 2 * icon_scale - icon_name_space.getValue(), y.getValue() - icon_scale_width.getValue(), textWidth + height.getValue() / 2 * icon_scale + icon_name_space.getValue() + icon_scale_width.getValue(), height.getValue() + icon_scale_width.getValue() * 2);
		nvgFillColor(nvg, backgroundColor);
		nvgFill(nvg);
		
		// 然后再来真正的字绘制
		nvgFontSize(nvg, height.getValue());
		nvgFontFace(nvg, "simhei");
		nvgTextAlign(nvg, NVG_ALIGN_RIGHT | NVG_ALIGN_TOP);
		nvgFillColor(nvg, text_color);
		nvgText(nvg, x.getValue(), y.getValue(), CraftGame.INSTACE_CRAFT_GAME.getPlayerStat().getName());
		
		// 头像
		nvgImagePattern(nvg, x.getValue() - textWidth - height.getValue() * icon_scale - icon_name_space.getValue(), y.getValue() - (icon_scale - 1.0f) * height.getValue() / 2, height.getValue() * icon_scale, height.getValue() * icon_scale, 0, CraftGame.INSTACE_CRAFT_GAME.getPlayerStat().getIconTexture(), 1, paint);
		nvgBeginPath(nvg);
		nvgCircle(nvg, x.getValue() - textWidth - height.getValue() / 2 * icon_scale - icon_name_space.getValue(), y.getValue() + height.getValue() / 2, height.getValue() / 2 * icon_scale);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);
		
		
	}

}
