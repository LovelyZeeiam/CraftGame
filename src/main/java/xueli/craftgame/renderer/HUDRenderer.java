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
import xueli.game2.renderer.ui.NanoVGContext;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.font.FontRenderResource;
import xueli.game2.resource.submanager.render.texture.TextureRenderResource;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureType;
import xueli.utils.io.Files;

public class HUDRenderer implements IGameRenderer {

	private static final TextureResourceLocation PAIMON_TEXTURE_LOCATION = new TextureResourceLocation(new ResourceLocation("images/hud/paimon.png"), TextureType.NVG);
	private static final TextureResourceLocation CROSS_TEXTURE_LOCATION = new TextureResourceLocation(new ResourceLocation("images/hud/cross.png"), TextureType.NVG);
	private static final TextureResourceLocation BORDER_TEXTURE_LOCATION = new TextureResourceLocation(new ResourceLocation("images/hud/border.png"), TextureType.NVG);
	private static final ResourceLocation FONT_LOCATION = new ResourceLocation("fonts/minecraft.ttf");

	private CraftGameContext ctx;

	long nvg;
	NVGPaint paint;

	private int texCrossHolder, texPaimonHolder;
	private int fontId;

	private HUDInventoryRenderer inventoryRenderer;

	public HUDRenderer(CraftGameContext ctx) {
		this.ctx = ctx;
		this.inventoryRenderer = new HUDInventoryRenderer(this);

	}

	public void init() {
		this.nvg = NanoVGContext.INSTANCE.getNvg();
		this.paint = NanoVGContext.INSTANCE.getPaint();

		TextureRenderResource textureManager = ctx.getTextureRenderResource();
		this.texPaimonHolder = textureManager.preRegister(PAIMON_TEXTURE_LOCATION, false);
		this.texCrossHolder = textureManager.preRegister(CROSS_TEXTURE_LOCATION, true);
		FontRenderResource fontManager = ctx.getFontResource();
		this.fontId = fontManager.preRegister(FONT_LOCATION, true);

		this.inventoryRenderer.init();

	}

	public void render() {
		Display display = ctx.getDisplay();
		nvgBeginFrame(nvg, display.getWidth(), display.getHeight(), (float) display.getWidth() / display.getHeight());
		stroke();
		nvgEndFrame(nvg);

		this.inventoryRenderer.render();

	}

	private void stroke() {
		LocalPlayer player = ctx.getPlayer();

		String posTextString = "Position: " + (int) Math.floor(player.getPlayer().getPos().x) + ", "
				+ (int) Math.floor(player.getPlayer().getPos().y) + ", "
				+ (int) Math.floor(player.getPlayer().getPos().z);
		String fpsTextString = "FPS: " + Time.fps;

		float fontSize = 15.0f * ctx.getDisplayScale();
		nvgFontFaceId(nvg, fontId);
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

		if (!ctx.getViewRenderer().hasView()) {
			nvgImagePattern(nvg, ctx.getWidth() / 2.0f - 5.0f * ctx.getDisplayScale(),
					ctx.getHeight() / 2.0f - 5.0f * ctx.getDisplayScale(), 10.0f * ctx.getDisplayScale(),
					10.0f * ctx.getDisplayScale(), 0, texCrossHolder, 1.0f, paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, ctx.getWidth() / 2.0f - 5.0f * ctx.getDisplayScale(),
					ctx.getHeight() / 2.0f - 5.0f * ctx.getDisplayScale(), 10.0f * ctx.getDisplayScale(),
					10.0f * ctx.getDisplayScale());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);
		}

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
		inventoryRenderer.release();

		nvgDelete(nvg);

	}

	public CraftGameContext getContext() {
		return ctx;
	}

}
