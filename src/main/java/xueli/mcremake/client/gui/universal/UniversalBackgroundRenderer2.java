package xueli.mcremake.client.gui.universal;

import java.awt.Color;
import java.util.Random;

import xueli.game2.renderer.ui.Gui;
import xueli.game2.resource.ResourceIdentifier;
import xueli.mcremake.client.CraftGameClient;

public class UniversalBackgroundRenderer2 {

	public static final ResourceIdentifier UNIVERSAL_BACKGROUND_RESOURCE_LOCATION = new ResourceIdentifier("minecraft", "gui/background.png");
	public static final Color TOP_COLOR = new Color(0, 0, 0, 0.7f);

	public static final int SIZE = 128;

	private static final int[] randX = new int[1000];
	private static final int[] randY = new int[1000];

	static {
		Random random = new Random();
		for (int i = 0; i < randX.length; i++) {
			randX[i] = random.nextInt(4);
		}
		for (int i = 0; i < randY.length; i++) {
			randY[i] = random.nextInt(4);
		}
	}

	private int imageId;

	public void draw(Gui gui, int x, int y, int width, int height) {
		float sizeX = SIZE;
		int xRepeatCount = 0;

//		gui.scissor(x, y, width, height);

		for (float px = x; px < x + width; px += SIZE) {
			if(px + SIZE > x + width) {
				sizeX = x + width - px;
			}

			int yRepeatCount = 0;
			float sizeY = SIZE;

			int xRandVal = randX[xRepeatCount % randX.length];

			for (float py = y; py < y + height; py += SIZE) {
				if(py + SIZE > y + height) {
					sizeY = y + height - py;
				}

				int yRandVal = randY[yRepeatCount % randY.length];
				int thisRandVal = (xRandVal + yRandVal) % 4;

				switch (thisRandVal) {
					case 0 -> gui.setTexturedPaint(px, py, SIZE, SIZE, 0, 1.0f, imageId);
					case 1 -> gui.setTexturedPaint(px + SIZE, py, SIZE, SIZE, (float) (Math.PI / 2), 1.0f, imageId);
					case 2 -> gui.setTexturedPaint(px + SIZE, py + SIZE, SIZE, SIZE, (float) (Math.PI), 1.0f, imageId);
					case 3 -> gui.setTexturedPaint(px, py + SIZE, SIZE, SIZE, (float) (Math.PI * 3 / 2), 1.0f, imageId);
				}
				gui.drawFilledRect(px, py, sizeX, sizeY, Gui.FillType.PAINT);

				yRepeatCount++;
			}

			xRepeatCount++;
		}

//		gui.scissorReset();

		gui.setColor(TOP_COLOR);
		gui.drawFilledRect(x, y, width, height, Gui.FillType.COLOR);

	}

	public void reload(CraftGameClient ctx) {
		imageId = ctx.getGuiManager().registerImage(ctx.textureResource.register(UNIVERSAL_BACKGROUND_RESOURCE_LOCATION, false));
		
	}

}
