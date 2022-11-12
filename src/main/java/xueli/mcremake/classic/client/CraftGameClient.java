package xueli.mcremake.classic.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game.utils.GLHelper;
import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.renderer.legacy.engine.RenderBuffer;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureType;
import xueli.mcremake.classic.client.renderer.gui.MyRenderBuffer2D;
import xueli.mcremake.classic.client.renderer.gui.RenderTypeTexture2D;

public class CraftGameClient extends GameDisplay {

	private static TextureResourceLocation TEST_TEXTURE_LOCATION = new TextureResourceLocation(new ResourceLocation("test", "splash.jpg"), TextureType.LEGACY);

	private RenderTypeTexture2D texRenderer;

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");
		
	}

	@Override
	protected void renderInit() {
		this.texRenderer = new RenderTypeTexture2D();
		this.texRenderer.init();

		int texId = getTextureRenderResource().register(TEST_TEXTURE_LOCATION, false);

		RenderBuffer buf = this.texRenderer.getRenderBuffer(texId);
		BackRenderBuffer backBuffer = buf.createBackBuffer();

		backBuffer.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, new Vector2f(-0.5f, 0.5f), new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, -0.5f));
		backBuffer.applyToBuffer(MyRenderBuffer2D.COLOR_VERTEX, new Vector3f(0,0,0), new Vector3f(0, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 0));
		backBuffer.applyToBuffer(MyRenderBuffer2D.UV_VERTEX, new Vector2f(0, 1), new Vector2f(1, 1), new Vector2f(1, 0));
		backBuffer.flip();

	}

	@Override
	protected void render() {
		GL11.glClearColor(1,1,1,1);

		this.texRenderer.render();

		GLHelper.checkGLError("Render");

	}

	@Override
	protected void renderRelease() {
		this.texRenderer.release();
		
	}

}
