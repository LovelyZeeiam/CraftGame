package xueli.mcremake.registry;

import java.awt.Color;
import java.util.HashMap;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.resource.ReloadableResourceTicket;
import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceIdentifier;
import xueli.game2.resource.submanager.render.texture.Texture;
import xueli.mcremake.client.renderer.gui.MyRenderBuffer2D;
import xueli.mcremake.client.renderer.gui.RenderTypeTexture2D;

/**
 * A easy font renderer
 * TODO: Later we should support custom font separator and try rendering a middle aligned text in one step
 */
public class MojanglesFont implements ResourceHolder {

	private static final ResourceIdentifier FONT_TEXTURE_LOCATION = new ResourceIdentifier("minecraft", "font/default.png");
	
	private static final int[] CHAR_SIZES = {
		3, 8, 8, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 8, 8, 8, 
		7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 7, 7, 7, 8, 8, 8, 
		1, 1, 4, 5, 5, 5, 5, 2, 4, 4, 4, 5, 1, 5, 1, 5, 
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 4, 5, 4, 5, 
		6, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 5, 5, 5, 5, 5, 
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 3, 5, 5, 
		2, 5, 5, 5, 5, 5, 4, 5, 5, 1, 5, 4, 2, 5, 5, 5, 
		5, 5, 5, 5, 3, 5, 5, 5, 5, 5, 5, 4, 1, 4, 6, 5, 
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 2, 5, 5, 
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3, 5, 
		5, 2, 5, 5, 5, 5, 5, 5, 5, 6, 5, 5, 5, 1, 5, 5, 
		7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
		8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
		8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 
		7, 6, 6, 7, 6, 7, 7, 7, 6, 7, 7, 6, 8, 8, 5, 6, 
		6, 6, 6, 6, 8, 5, 6, 7, 7, 8, 8, 8, 7, 6, 8, 1, 
	};

	private final GameDisplay ctx;

	private RenderTypeTexture2D renderer = new RenderTypeTexture2D();
	private ReloadableResourceTicket<Texture> fontTexture;
	
	public MojanglesFont(GameDisplay ctx) {
		this.ctx = ctx;
		this.fontTexture = ctx.textureResource.register(FONT_TEXTURE_LOCATION, true);
		
	}
	
	@Override
	public void reload() {
		
	}
	
	private HashMap<RenderBuffer, BackRenderBuffer> buffers = new HashMap<>();
	
	public float measureWidth(float x, float y, float size, float separateRatio, String str) {
		float width = 0;
		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);
			// Only support ASCII
			c = c >= 256 ? 0 : c;
			width += size * CHAR_SIZES[c] / 8.0f;
			width += size * separateRatio;
		}
		return width;
	}
	
	public void drawFont(float x, float y, float size, float separateRatio, String str, Color color) {
		RenderBuffer buffer = renderer.getRenderBuffer(fontTexture.get().id());
		BackRenderBuffer backBuffer = buffers.computeIfAbsent(buffer, RenderBuffer::createBackBuffer);

		float colorR = color.getRed() / 255.0f;
		float colorG = color.getGreen() / 255.0f;
		float colorB = color.getBlue() / 255.0f;
		Vector3f colorVector = new Vector3f(colorR, colorG, colorB);

		float charXPointer = x;
		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);
			// Only support ASCII
			c = c >= 256 ? 0 : c;

			int xInTex = c % 16;
			int yInTex = c / 16;

			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, new Vector2f(charXPointer, y), new Vector2f(charXPointer + size, y), new Vector2f(charXPointer, y + size));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_UV, new Vector2f(xInTex / 16.0f, yInTex / 16.0f), new Vector2f((xInTex + 1) / 16.0f, yInTex / 16.0f), new Vector2f(xInTex / 16.0f, (yInTex + 1)/ 16.0f));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_COLOR, colorVector, colorVector, colorVector);

			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, new Vector2f(charXPointer + size, y + size), new Vector2f(charXPointer + size, y), new Vector2f(charXPointer, y + size));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_UV, new Vector2f((xInTex + 1) / 16.0f, (yInTex + 1) / 16.0f), new Vector2f((xInTex + 1) / 16.0f, yInTex / 16.0f), new Vector2f(xInTex / 16.0f, (yInTex + 1)/ 16.0f));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_COLOR, colorVector, colorVector, colorVector);
			
			charXPointer += size * CHAR_SIZES[c] / 8.0f;
			charXPointer += size * separateRatio;
			
		}

	}

	public void tick() {
		buffers.values().forEach(BackRenderBuffer::flip);

		renderer.setDisplayDimension(ctx.getWidth(), ctx.getHeight());
		renderer.render();

		buffers.clear();

	}

	public void release() {
		renderer.release();

	}

}
