package xueli.mcremake.classic.client.renderer.world;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector2f;
import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureTypeLegacy;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;
import xueli.mcremake.classic.client.CraftGameClient;

public class TerrainTexture implements ResourceHolder {

	public static TextureResourceLocation TERRAIN_TEXTURE_LOCATION = new TextureResourceLocation(new ResourceLocation("minecraft", "terrain.png"), new TextureTypeLegacy());

	private final CraftGameClient ctx;
	private int textureId;

	public TerrainTexture(CraftGameClient ctx) {
		this.ctx = ctx;
	}

	@Override
	public void reload() {
		textureId = ctx.getTextureRenderResource().register(TERRAIN_TEXTURE_LOCATION, true);
	}

	public void bind() {
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
	}

	public void unbind() {
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
	}

	public AtlasResourceHolder getUVVertex(int x, int y) {
		return new AtlasResourceHolder(new Vector2f(x / 16.0f, y / 16.0f), new Vector2f((x + 1) / 16.0f, (y + 1) / 16.0f), textureId);
	}

	public int getTextureId() {
		return textureId;
	}

}
