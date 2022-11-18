package xueli.mcremake.classic.client.gui.universal;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game2.renderer.legacy.BackRenderBuffer;
import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.mcremake.classic.client.CraftGameClient;
import xueli.mcremake.classic.client.renderer.gui.MyRenderBuffer2D;
import xueli.mcremake.classic.client.renderer.gui.RenderTypeTexture2D;

public class UniversalBackgroundRenderer implements ResourceHolder {

	public static final TextureResourceLocation UNIVERSAL_BACKGROUND_RESOURCE_LOCATION = new TextureResourceLocation(new ResourceLocation("minecraft", "gui/background.png"), resManager -> new UniversalBackgroundTextureLoader());
	public static final int BG_SIZE = 128;
	public static final Vector3f BG_COLOR = new Vector3f(0.3f, 0.3f, 0.3f);

	private int texUniversalBgId;

	private final CraftGameClient ctx;

	private final RenderTypeTexture2D renderType = new RenderTypeTexture2D();
	private RenderBuffer renderBuffer;

	public UniversalBackgroundRenderer(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	private BackRenderBuffer backBuf;

	public void init() {
		renderType.init();
		this.reload();

	}

	public void draw(float x, float y, float width, float height) {
		float u1 = width / BG_SIZE;
		float v1 = height / BG_SIZE;

		Vector2f leftBottom = new Vector2f(0, 0);
		Vector2f rightBottom = new Vector2f(u1, 0);
		Vector2f leftTop = new Vector2f(0, v1);
		Vector2f rightTop = new Vector2f(u1, v1);

		backBuf.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, new Vector2f(x, y), new Vector2f(x + width, y), new Vector2f(x, y + height));
		backBuf.applyToBuffer(MyRenderBuffer2D.ATTR_UV, leftTop, rightTop, leftBottom);
		backBuf.applyToBuffer(MyRenderBuffer2D.ATTR_COLOR, BG_COLOR, BG_COLOR, BG_COLOR);

		backBuf.applyToBuffer(MyRenderBuffer2D.ATTR_VERTEX, new Vector2f(x + width, y + height), new Vector2f(x + width, y), new Vector2f(x, y + height));
		backBuf.applyToBuffer(MyRenderBuffer2D.ATTR_UV, rightBottom, rightTop, leftBottom);
		backBuf.applyToBuffer(MyRenderBuffer2D.ATTR_COLOR, BG_COLOR, BG_COLOR, BG_COLOR);

	}

	public void tick() {
		backBuf.flip();

		renderType.setDisplayDimension(ctx.getWidth(), ctx.getHeight());
		renderType.render();

		backBuf = renderBuffer.createBackBuffer();

	}

	@Override
	public void reload() {
		this.texUniversalBgId = ctx.getTextureRenderResource().register(UNIVERSAL_BACKGROUND_RESOURCE_LOCATION, false);

		if(this.renderBuffer != null) {
			this.renderBuffer.release();
		}
		renderBuffer = renderType.getRenderBuffer(this.texUniversalBgId);
		backBuf = renderBuffer.createBackBuffer();

	}

}
