package xueli.clock;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_RIGHT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCircle;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImageMem;
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
import static org.lwjgl.nanovg.NanoVG.nvgTextLetterSpacing;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.opengl.GL11;

import xueli.game.Game;
import xueli.game.utils.NVGColors;
import xueli.utils.io.Files;

public class ClockMain extends Game {

	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd");

	private static final String USER_NAME = "LoveliZeeiam";

	private long nvg;
	private int fontId, texIcon, texBack;
	private NVGPaint paint = NVGPaint.create();

	public ClockMain() {
		super(800, 600, "Li.Clock");
	}

	@Override
	public void onCreate() {
		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			throw new RuntimeException("Couldn't init NanoVG!");
		}

		ByteBuffer bufferFont;
		try {
			bufferFont = Files.readResourcePackedInJarAndPackedToBuffer("/assets/fonts/CascadiaCode.ttf");
			this.fontId = nvgCreateFontMem(nvg, "CascadiaCode", bufferFont, 1);
		} catch (IOException e) {
			throw new RuntimeException("Could load font: /assets/fonts/CascadiaCode.ttf", e);
		}

		ByteBuffer bufferTexIcon = null;
		try {
			bufferTexIcon = Files.readResourcePackedInJarAndPackedToBuffer("/clock/icon.jpg");
			this.texIcon = nvgCreateImageMem(nvg, NVG_IMAGE_NEAREST, bufferTexIcon);
		} catch (IOException e) {
			throw new RuntimeException("Could load image: /clock/icon.jpg", e);
		}

		ByteBuffer bufferBackground = null;
		try {
			bufferBackground = Files.readResourcePackedInJarAndPackedToBuffer("/clock/Background.png");
			this.texBack = nvgCreateImageMem(nvg, NVG_IMAGE_NEAREST, bufferBackground);
		} catch (IOException e) {
			throw new RuntimeException("Could load image: /clock/Background.png", e);
		}

	}

	@Override
	public void onTick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);

		float scale = getDisplayScale();
		float width = getWidth();
		float height = getHeight();

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
		nvgFontFaceId(nvg, fontId);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
		nvgTextLetterSpacing(nvg, 12.0f * scale);
		float pointerX = nvgText(nvg, width / 2, height / 2, timeStr);

		float dateFontSize = scale * 15.0f * scale;
		nvgFontSize(nvg, dateFontSize);
		nvgFontFaceId(nvg, fontId);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_CENTER);
		nvgTextLetterSpacing(nvg, 4.0f * scale);
		nvgText(nvg, pointerX, height / 2 + clockFontSize / 2 + 5.0f * scale, dateStr);

		nvgFontSize(nvg, userNameSize);
		nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_RIGHT);
		nvgTextLetterSpacing(nvg, 2.0f * scale);
		nvgText(nvg, userIconPosX, userIconPosY, USER_NAME);

		float userNameWidth = measureTextWidth(nvg, userNameSize, USER_NAME);
		nvgImagePattern(nvg, userIconPosX - userNameWidth - marginUserNameAndIcon - 2 * iconRadius,
				userIconPosY - iconRadius, 2 * iconRadius, 2 * iconRadius, 0.0f, texIcon, 1.0f, paint);
		nvgBeginPath(nvg);
		nvgCircle(nvg, userIconPosX - userNameWidth - marginUserNameAndIcon - iconRadius, userIconPosY, iconRadius);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

		nvgImagePattern(nvg, width - backMargin - backSize, height - backMargin - backSize, backSize, backSize, 0,
				texBack, 0.6f, paint);
		nvgBeginPath(nvg);
		nvgRect(nvg, width - backMargin - backSize, height - backMargin - backSize, backSize, backSize);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

		nvgEndFrame(nvg);

	}

	@Override
	public void onRelease() {
		nvgDelete(nvg);

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
