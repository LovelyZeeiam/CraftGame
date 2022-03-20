package xueli.craftgame.renderer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.nanovg.NVGPaint;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.player.LocalPlayer;
import xueli.game.display.Display;
import xueli.game.utils.NVGColors;
import xueli.game.utils.Time;
import xueli.utils.io.Files;

public class HUDRenderer implements IGameRenderer {
	
	private CraftGameContext ctx;
	
	private long nvg;
	private NVGPaint paint = NVGPaint.create();
	
	private int texCross;
	private int fontId;
	
	public HUDRenderer(CraftGameContext ctx) {
		this.ctx = ctx;
	}
	
	public void init() {
		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			ctx.announceCrash("HUD Init", new Exception("Couldn't init NanoVG!"));
			return;
		}
		
		ByteBuffer bufferTexCross = null;
		try {
			bufferTexCross = Files.readResourcePackedInJarAndPackedToBuffer("/assets/images/hud/cross.png");
		} catch (IOException e) {
			ctx.announceCrash("HUD Init", new Exception("Could load image: /assets/images/hud/cross.png", e));
			return;
		}
		this.texCross = nvgCreateImageMem(nvg, NVG_IMAGE_NEAREST, bufferTexCross);
		
		ByteBuffer bufferFont;
		try {
			bufferFont = Files.readResourcePackedInJarAndPackedToBuffer("/assets/fonts/minecraft.ttf");
		} catch (IOException e) {
			ctx.announceCrash("HUD Init", new Exception("Could load font: /assets/fonts/minecraft.ttf", e));
			return;
		}
		this.fontId = nvgCreateFontMem(nvg, "Minecraft", bufferFont, 1);
		
	}
	
	public void render() {
		Display display = ctx.getDisplay();
		nvgBeginFrame(nvg, display.getWidth(), display.getHeight(), (float) display.getWidth() / display.getHeight());
		stroke();
		nvgEndFrame(nvg);
		
	}
	
	private void stroke() {
		LocalPlayer player = ctx.getPlayer();
		
		String posTextString = "Position: " + (int) Math.floor(player.getPlayer().getPos().x) + ", "
				+ (int) Math.floor(player.getPlayer().getPos().y) + ", " + (int) Math.floor(player.getPlayer().getPos().z);
		String fpsTextString = "FPS: " + Time.fps;
		
		float fontSize = 15.0f * ctx.getDisplayScale();
		float measuredTextLength = Math.max(measureTextWidth(fontSize, posTextString),
				measureTextWidth(fontSize, fpsTextString));
		
		nvgFillColor(nvg, NVGColors.TRANSPARENT_BLACK);
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, 4.0f * ctx.getDisplayScale() + measuredTextLength,
				8.0f * ctx.getDisplayScale() + 2 * fontSize);
		nvgFill(nvg);

		nvgFontSize(nvg, fontSize);
		nvgFontFaceId(nvg, fontId);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		nvgText(nvg, 2.0f * ctx.getDisplayScale(), 2.0f * ctx.getDisplayScale(), posTextString);
		nvgText(nvg, 2.0f * ctx.getDisplayScale(), 4.0f * ctx.getDisplayScale() + fontSize, fpsTextString);

		nvgImagePattern(nvg, ctx.getWidth() / 2 - 5.0f * ctx.getDisplayScale(),
				ctx.getHeight() / 2 - 5.0f * ctx.getDisplayScale(), 10.0f * ctx.getDisplayScale(),
				10.0f * ctx.getDisplayScale(), 0, texCross, 1.0f, paint);
		nvgBeginPath(nvg);
		nvgRect(nvg, ctx.getWidth() / 2 - 5.0f * ctx.getDisplayScale(),
				ctx.getHeight() / 2 - 5.0f * ctx.getDisplayScale(), 10.0f * ctx.getDisplayScale(),
				10.0f * ctx.getDisplayScale());
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);
		
	}
	
	private float measureTextWidth(float size, String text) {
		nvgFontSize(nvg, size);
		return nvgText(nvg, 0, -10000000, text);
	}
	
	public void release() {
		nvgDelete(nvg);
		
	}
	
}
