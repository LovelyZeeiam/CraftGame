package xueli.mcremake.registry;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector2f;

import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceIdentifier;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;
import xueli.mcremake.client.CraftGameClient;

public class TerrainTextureAtlas implements ResourceHolder {

	public static ResourceIdentifier TERRAIN_TEXTURE_LOCATION = new ResourceIdentifier("minecraft", "terrain.png");

	private final CraftGameClient ctx;
	private int textureId;

	public TerrainTextureAtlas(CraftGameClient ctx) {
		this.ctx = ctx;
		this.reload();
	}

	@Override
	public void reload() {
		// NO MORE RELOAD!
		// Just get from ReloadableResource!
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
