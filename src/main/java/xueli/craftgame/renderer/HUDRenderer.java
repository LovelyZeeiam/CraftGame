package xueli.craftgame.renderer;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFaceId;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.nanovg.NVGPaint;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.player.LocalPlayer;
import xueli.game.resource.ImageResourceManager;
import xueli.game.resource.NVGImage;
import xueli.game.resource.ResourceHolder;
import xueli.game.utils.NVGColors;
import xueli.game.utils.Time;
import xueli.game2.display.Display;
import xueli.utils.io.Files;

public class HUDRenderer implements IGameRenderer {

	private CraftGameContext ctx;

	long nvg;
	NVGPaint paint = NVGPaint.create();

	private ResourceHolder<NVGImage> texCrossHolder, texPaimonHolder;
	private int fontId;

	// private int nullImage;

	private HUDInventoryRenderer inventoryRenderer;

	public HUDRenderer(CraftGameContext ctx) {
		this.ctx = ctx;
		this.inventoryRenderer = new HUDInventoryRenderer(this);

	}

	public void init() {
//		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
//		if (this.nvg == 0) {
//			ctx.announceCrash("HUD Init", new Exception("Couldn't init NanoVG!"));
//		}
//
//		ImageResourceManager imageResourceManager = ctx.getResourceMaster().getImageResourceManager();
//
//		this.texCrossHolder = imageResourceManager.addToken("hud.cross", "/assets/images/hud/cross.png");
//		// this.texPaimonHolder = imageResourceManager.addToken("hud.paimon",
//		// "/assets/images/hud/paimon.png");
//
//		try {
//			imageResourceManager.setCurrentNvg(nvg);
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			// ctx.announceCrash("HUD Init", exception);
//
//			imageResourceManager.getMissingProvider().onMissing(texCrossHolder);
//
//		}
//
//		ByteBuffer bufferFont;
//		try {
//			bufferFont = Files.readResourcePackedInJarAndPackedToBuffer("/assets/fonts/minecraft.ttf");
//			this.fontId = nvgCreateFontMem(nvg, "Minecraft", bufferFont, 1);
//		} catch (IOException e) {
//			ctx.announceCrash("HUD Init", new Exception("Could load font: /assets/fonts/minecraft.ttf", e));
//		}
//
//		this.inventoryRenderer.init();
//
//		// nullImage = TextureMissing.getMissingNvgImage(nvg);

	}

	public void render() {
//		Display display = ctx.getDisplay();
//		nvgBeginFrame(nvg, display.getWidth(), display.getHeight(), (float) display.getWidth() / display.getHeight());
//		stroke();
//		nvgEndFrame(nvg);
//
//		this.inventoryRenderer.render();

	}

	private void stroke() {
//		LocalPlayer player = ctx.getPlayer();
//
//		String posTextString = "Position: " + (int) Math.floor(player.getPlayer().getPos().x) + ", "
//				+ (int) Math.floor(player.getPlayer().getPos().y) + ", "
//				+ (int) Math.floor(player.getPlayer().getPos().z);
//		String fpsTextString = "FPS: " + Time.fps;
//
//		float fontSize = 15.0f * ctx.getDisplayScale();
//		float measuredTextLength = Math.max(measureTextWidth(fontSize, posTextString),
//				measureTextWidth(fontSize, fpsTextString));
//
//		nvgFillColor(nvg, NVGColors.TRANSPARENT_BLACK);
//		nvgBeginPath(nvg);
//		nvgRect(nvg, 0, 0, 4.0f * ctx.getDisplayScale() + measuredTextLength,
//				8.0f * ctx.getDisplayScale() + 2 * fontSize);
//		nvgFill(nvg);
//
//		nvgFontSize(nvg, fontSize);
//		nvgFontFaceId(nvg, fontId);
//		nvgFillColor(nvg, NVGColors.WHITE);
//		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
//		nvgText(nvg, 2.0f * ctx.getDisplayScale(), 2.0f * ctx.getDisplayScale(), posTextString);
//		nvgText(nvg, 2.0f * ctx.getDisplayScale(), 4.0f * ctx.getDisplayScale() + fontSize, fpsTextString);
//
//		if (!ctx.getViewRenderer().hasView()) {
//			nvgImagePattern(nvg, ctx.getWidth() / 2 - 5.0f * ctx.getDisplayScale(),
//					ctx.getHeight() / 2 - 5.0f * ctx.getDisplayScale(), 10.0f * ctx.getDisplayScale(),
//					10.0f * ctx.getDisplayScale(), 0, texCrossHolder.getResult().image(), 1.0f, paint);
//			nvgBeginPath(nvg);
//			nvgRect(nvg, ctx.getWidth() / 2 - 5.0f * ctx.getDisplayScale(),
//					ctx.getHeight() / 2 - 5.0f * ctx.getDisplayScale(), 10.0f * ctx.getDisplayScale(),
//					10.0f * ctx.getDisplayScale());
//			nvgFillPaint(nvg, paint);
//			nvgFill(nvg);
//		}

//		  { float xOffset = (float) Math.sin((Time.thisTime % 5000) / 5000.0 * Math.PI
//		  * 2) * 4; float yOffset = (float) Math.sin((Time.thisTime % 2850) / 2850.0 *
//		 Math.PI * 2) * 15;
//
//		  float paimonSize = 160.0f * ctx.getDisplayScale(); float paimonX =
//		  ctx.getWidth() - 90.0f * ctx.getDisplayScale() - paimonSize / 2 + xOffset *
//		  ctx.getDisplayScale(); float paimonY = 90.0f * ctx.getDisplayScale() -
//		  paimonSize / 2 + yOffset * ctx.getDisplayScale();
//
//		  nvgImagePattern(nvg, paimonX, paimonY, paimonSize, paimonSize, 0, texPaimonHolder.getResult().image(),
//		  1.0f, paint); nvgBeginPath(nvg); nvgRect(nvg, paimonX, paimonY, paimonSize,
//		  paimonSize); nvgFillPaint(nvg, paint); nvgFill(nvg);
//
//		  }

	}

	private float measureTextWidth(float size, String text) {
		nvgFontSize(nvg, size);
		return nvgText(nvg, 0, -10000000, text);
	}

	public void release() {
//		inventoryRenderer.release();
//
//		nvgDelete(nvg);

	}

	public CraftGameContext getContext() {
		return ctx;
	}

}
