package xueli.mcremake.registry;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector2f;

import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;
import xueli.mcremake.client.CraftGameClient;

public class TerrainTexture implements ResourceHolder {

	public static ResourceLocation TERRAIN_TEXTURE_LOCATION = new ResourceLocation("minecraft", "terrain.png");

	private final CraftGameClient ctx;
	private int textureId;

	public TerrainTexture(CraftGameClient ctx) {
		this.ctx = ctx;
	}

	@Override
	public void reload() {
		textureId = ctx.textureResource.register(TERRAIN_TEXTURE_LOCATION, true).id();
	}

	public void bind() {
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
	}

	public void unbind() {
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
	}

	public AtlasResourceHolder getUVVertex(int x, int y) {
		return new AtlasResourceHolder(new Vector2f(x / 16.0f, y / 16.0f), new Vector2f((x + 1) / 16.0f, (y + 1) / 16.0f));
	}

	public int getTextureId() {
		return textureId;
	}

}
