package xueli.clock;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import xueli.game2.display.Display;
import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.ui.Gui;
import xueli.game2.renderer.ui.Overlay;
import xueli.game2.resource.ResourceLocation;

public class ClockMain extends GameDisplay {

	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd");

	private static final String USER_NAME = "LoveliZeeiam";
	
	private final ResourceLocation fontLocation = new ResourceLocation("clock", "fonts/CascadiaCode.ttf"); 
	private final ResourceLocation iconLocation = new ResourceLocation("clock", "images/icon.jpg");
	private final ResourceLocation backgroundLocation = new ResourceLocation("clock", "images/background.png");

	public ClockMain() {
		super(800, 600, "Li.Clock");
	}

	@Override
	public void renderInit() {
		this.overlayManager.setOverlay(new Overlay() {
			private int fontId;
			private int texIcon, texBack;
			
			@Override
			public void init(Gui guiManager) {
			}
			
			@Override
			public void reload(Gui guiManager) {
				try {
					this.fontId = guiManager.registerFont("GAME", resourceManager.getResource(fontLocation));
					this.texIcon = guiManager.registerImage(textureResource.register(iconLocation, true));
					this.texBack = guiManager.registerImage(textureResource.register(backgroundLocation, true));
				} catch (IOException e) {
					announceCrash("Reload", e);
				}
				
				
			}

			@Override
			public void render(Gui guiManager) {
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

				guiManager.setColor(Color.WHITE);

				float clockFontSize = scale * 50.0f * scale;
				guiManager.setTextLetterSpacing(3.0f * scale);
				float pointerX = guiManager.drawFont(width / 2, height / 2, clockFontSize, timeStr, fontId, Gui.FontAlign.MIDDLE, Gui.FontAlign.CENTER);

				float dateFontSize = scale * 15.0f * scale;
				guiManager.drawFont(pointerX, height / 2 + clockFontSize / 2 + 5.0f * scale, dateFontSize, dateStr, fontId, Gui.FontAlign.TOP, Gui.FontAlign.CENTER);
				guiManager.drawFont(userIconPosX, userIconPosY, userNameSize, USER_NAME, fontId, Gui.FontAlign.MIDDLE, Gui.FontAlign.RIGHT);

				float userNameWidth = guiManager.measureTextWidth(userNameSize, USER_NAME, fontId);
				guiManager.drawImageCircle(userIconPosX - userNameWidth - marginUserNameAndIcon - iconRadius, userIconPosY, iconRadius, texIcon, 0, 1);

				guiManager.drawImage(width - backMargin - backSize, height - backMargin - backSize, backSize, backSize, 1, texBack);

			}

			@Override
			public void release(Gui guiManager) {

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
