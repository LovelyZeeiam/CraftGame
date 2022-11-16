package xueli.clock;

import org.lwjgl.nanovg.NVGPaint;
import xueli.game2.display.Display;
import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.ui.MyGui;
import xueli.game2.renderer.ui.Overlay;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureType;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockMain extends GameDisplay {

	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd");

	private static final String USER_NAME = "LoveliZeeiam";
	
	private final ResourceLocation fontLocation = new ResourceLocation("clock", "fonts/CascadiaCode.ttf"); 
	private final TextureResourceLocation iconLocation = new TextureResourceLocation(new ResourceLocation("clock", "images/icon.jpg"), TextureType.NVG);
	private final TextureResourceLocation backgroundLocation = new TextureResourceLocation(new ResourceLocation("clock", "images/background.png"), TextureType.NVG);

	public ClockMain() {
		super(800, 600, "Li.Clock");
	}

	@Override
	public void renderInit() {
		fontResource.register(fontLocation, true);
		textureResource.register(iconLocation, true);
		textureResource.register(backgroundLocation, true);

		this.getOverlayManager().setOverlay(new Overlay() {
			@Override
			public void init() {
			}

			@Override
			public void tick() {
				Display display = getDisplay();
				float scale = display.getDisplayScale();
				float width = display.getWidth();
				float height = display.getHeight();

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

				MyGui guiManager = getGuiManager();
				guiManager.begin();
				guiManager.setColor(Color.WHITE);

				float clockFontSize = scale * 50.0f * scale;
				int fontId = fontResource.register(fontLocation, true);
				guiManager.setTextLetterSpacing(3.0f * scale);
				float pointerX = guiManager.drawFont(width / 2, height / 2, clockFontSize, timeStr, fontId, MyGui.FontAlign.MIDDLE, MyGui.FontAlign.CENTER);

				float dateFontSize = scale * 15.0f * scale;
				guiManager.drawFont(pointerX, height / 2 + clockFontSize / 2 + 5.0f * scale, dateFontSize, dateStr, fontId, MyGui.FontAlign.TOP, MyGui.FontAlign.CENTER);
				guiManager.drawFont(userIconPosX, userIconPosY, userNameSize, USER_NAME, fontId, MyGui.FontAlign.MIDDLE, MyGui.FontAlign.RIGHT);

				float userNameWidth = guiManager.measureTextWidth(userNameSize, USER_NAME, fontId);
				int texIcon = textureResource.register(iconLocation, true);
				guiManager.drawImageCircle(userIconPosX - userNameWidth - marginUserNameAndIcon - iconRadius, userIconPosY, iconRadius, texIcon, 0, 1);

				int texBack = textureResource.register(backgroundLocation, true);
				guiManager.drawImage(width - backMargin - backSize, height - backMargin - backSize, backSize, backSize, 1, texBack);

			}

			@Override
			public void release() {

			}
		});

	}

	@Override
	public void render() {

	}

	@Override
	public void renderRelease() {
	}

	public static void main(String[] args) {
		new ClockMain().run();
	}

}
