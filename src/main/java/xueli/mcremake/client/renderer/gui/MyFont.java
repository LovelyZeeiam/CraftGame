package xueli.mcremake.client.renderer.gui;

import java.awt.Color;
import java.util.HashMap;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureTypeLegacy;
import xueli.mcremake.client.CraftGameClient;

@Deprecated
@SuppressWarnings("unused")
class MyFont {

	private static final TextureResourceLocation FONT_TEXTURE_LOCATION = new TextureResourceLocation(new ResourceLocation("minecraft", "font/default.png"), new TextureTypeLegacy());
	
	private static final float DEFAULT_FONT_SIZE = 8;
	private static final float DIGIT_FONT_SIZE = 6;

	private final CraftGameClient ctx;

	private RenderTypeTexture2D renderer = new RenderTypeTexture2D();

	public MyFont(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	public void init() {
	}

	private HashMap<RenderBuffer, BackRenderBuffer> buffers = new HashMap<>();

	public void drawFont(float x, float y, float size, String str, Color color) {
		RenderBuffer buffer = renderer.getRenderBuffer(ctx.textureResource.register(FONT_TEXTURE_LOCATION, true));
		BackRenderBuffer backBuffer = buffers.computeIfAbsent(buffer, RenderBuffer::createBackBuffer);

		float colorR = color.getRed() / 255.0f;
		float colorG = color.getGreen() / 255.0f;
		float colorB = color.getBlue() / 255.0f;
		Vector3f colorVector = new Vector3f(colorR, colorG, colorB);

		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);

			int xInTex = c % 16;
			int yInTex = c / 16;

			float charX = x + i * size;

			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, new Vector2f(charX, y), new Vector2f(charX + size, y), new Vector2f(charX, y + size));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_UV, new Vector2f(xInTex / 16.0f, yInTex / 16.0f), new Vector2f((xInTex + 1) / 16.0f, yInTex / 16.0f), new Vector2f(xInTex / 16.0f, (yInTex + 1)/ 16.0f));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_COLOR, colorVector, colorVector, colorVector);

			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, new Vector2f(charX + size, y + size), new Vector2f(charX + size, y), new Vector2f(charX, y + size));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_UV, new Vector2f((xInTex + 1) / 16.0f, (yInTex + 1) / 16.0f), new Vector2f((xInTex + 1) / 16.0f, yInTex / 16.0f), new Vector2f(xInTex / 16.0f, (yInTex + 1)/ 16.0f));
			backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_COLOR, colorVector, colorVector, colorVector);


		}

//		System.out.println("==");

	}

//	private void internalDraw(BackRenderBuffer buf, Vector2f pos, Vector2f uv, Vector3f color) {
//		buf.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, pos);
//		buf.applyToBuffer(MyRenderBuffer2D.ATTR_UV, uv);
//		buf.applyToBuffer(MyRenderBuffer2D.ATTR_COLOR, color);
//
//	}

	public void tick() {
		buffers.values().forEach(BackRenderBuffer::flip);
//		GLHelper.checkGLError("Draw Font - Flip");

		renderer.setDisplayDimension(ctx.getWidth(), ctx.getHeight());
//		GLHelper.checkGLError("Draw Font - Dimension Set");
		renderer.render();
//		GLHelper.checkGLError("Draw Font - Render");

		this.buffers.clear();

	}

	public void release() {
		renderer.release();

	}

}
