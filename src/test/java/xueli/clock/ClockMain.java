package xueli.clock;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_RIGHT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCircle;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextLetterSpacing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.opengl.GL11;

import xueli.game.utils.NVGColors;
import xueli.game2.display.Display;
import xueli.game2.display.IGameRenderer;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureType;
import xueli.game2.renderer.ui.NanoVGContext;

public class ClockMain extends IGameRenderer {

	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd");

	private static final String USER_NAME = "LoveliZeeiam";
	
	private long nvg;
	private NVGPaint paint;
	
	private final ResourceLocation fontLocation = new ResourceLocation("clock", "fonts/CascadiaCode.ttf"); 
	private final TextureResourceLocation iconLocation = new TextureResourceLocation(new ResourceLocation("clock", "images/icon.jpg"), TextureType.NVG);
	private final TextureResourceLocation backgroundLocation = new TextureResourceLocation(new ResourceLocation("clock", "images/background.png"), TextureType.NVG);

	public ClockMain() {
		super(800, 600, "Li.Clock");
	}

	@Override
	public void renderInit() {
		this.nvg = NanoVGContext.INSTANCE.getNvg();
		this.paint = NanoVGContext.INSTANCE.getPaint();

		fontResource.preRegister(fontLocation, true);

	}

	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
		
		Display display = getDisplay();
		float scale = display.getDisplayScale();
		float width = display.getWidth();
		float height = display.getHeight();
		
		GL11.glViewport(0, 0, (int)width, (int)height);

		float userIconPosX = width - 30.0f * scale;
		float userIconPosY = 30.0f * scale;
		float userNameSize = 18.0f * scale;
		float iconRadius = 18.0f * scale;
		float marginUserNameAndIcon = 6.0f * scale;

		float backMargin = 20.0f * scale;
		float backSize = 280.0f * scale;

		Date date = new Date();
		String dateStr = dateFormat.format(date);
		String timeStr = timeFormat.format(date);

		nvgBeginFrame(nvg, width, height, width / height);
		
		float clockFontSize = scale * 50.0f * scale;
		nvgFontSize(nvg, clockFontSize);
		fontResource.registerAndBind(fontLocation, true);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
		nvgTextLetterSpacing(nvg, 12.0f * scale);
		float pointerX = nvgText(nvg, width / 2, height / 2, timeStr);

		float dateFontSize = scale * 15.0f * scale;
		nvgFontSize(nvg, dateFontSize);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_CENTER);
		nvgTextLetterSpacing(nvg, 4.0f * scale);
		nvgText(nvg, pointerX, height / 2 + clockFontSize / 2 + 5.0f * scale, dateStr);

		nvgFontSize(nvg, userNameSize);
		nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_RIGHT);
		nvgTextLetterSpacing(nvg, 2.0f * scale);
		nvgText(nvg, userIconPosX, userIconPosY, USER_NAME);

		float userNameWidth = measureTextWidth(nvg, userNameSize, USER_NAME);
		int texIcon = textureResource.registerAndBind(iconLocation, true);
		nvgImagePattern(nvg, userIconPosX - userNameWidth - marginUserNameAndIcon - 2 * iconRadius,
				userIconPosY - iconRadius, 2 * iconRadius, 2 * iconRadius, 0.0f, texIcon, 1.0f, paint);
		nvgBeginPath(nvg);
		nvgCircle(nvg, userIconPosX - userNameWidth - marginUserNameAndIcon - iconRadius, userIconPosY, iconRadius);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

		int texBack = textureResource.registerAndBind(backgroundLocation, true);
		nvgImagePattern(nvg, width - backMargin - backSize, height - backMargin - backSize, backSize, backSize, 0,
				texBack, 0.6f, paint);
		nvgBeginPath(nvg);
		nvgRect(nvg, width - backMargin - backSize, height - backMargin - backSize, backSize, backSize);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

		nvgEndFrame(nvg);

	}

	@Override
	public void renderRelease() {
		
	}

	private float measureTextWidth(long nvg, float size, String text) {
		nvgFontSize(nvg, size);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT);
		return nvgText(nvg, 0, -10000000, text);
	}

	public static void main(String[] args) {
		new ClockMain().run();
	}

}
