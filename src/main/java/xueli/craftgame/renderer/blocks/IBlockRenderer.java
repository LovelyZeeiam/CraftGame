package xueli.craftgame.renderer.blocks;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.Blocks;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.setting.Settings;
import xueli.game.utils.GLHelper;
import xueli.game.vector.Vector;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.shader.Shader;
import xueli.utils.Int2HashMap;

public class IBlockRenderer {

	public static final ResourceLocation ATLAS_LOCATION = new ResourceLocation("images/blocks/");

	private CraftGameContext ctx;
	private WorldRenderer manager;
	private LocalPlayer player;

	private Shader shader;
	private Int2HashMap<ChunkBuffer> chunkBuffers = new Int2HashMap<>();

	// Just Initialize Here
	public IBlockRenderer(WorldRenderer manager) {
		this.manager = manager;
		this.ctx = manager.getContext();
		this.player = manager.getPlayer();

		ctx.getAtlasTextureResource().findAndRegister(ATLAS_LOCATION, path -> true);

	}

	protected void setShader(String vertSource, String fragSource) {
		this.shader = Shader.getShader(vertSource, fragSource);
	}

	public void newChunkBuffer(int x, int z) {
		if (!chunkBuffers.containsKey(x, z)) {
			chunkBuffers.put(x, z, new ChunkBuffer(x, z, this));
		}

	}

	public void removeChunkBuffer(int x, int z) {
		ChunkBuffer buffer = chunkBuffers.get(x, z);
		if (buffer == null)
			return;

		buffer.release();

	}

	public ChunkBuffer getChunkBuffer(int chunkX, int chunkZ) {
		return chunkBuffers.get(chunkX, chunkZ);
	}

	public void draw(int x, int z) {
		ChunkBuffer chkBuf = getChunkBuffer(x, z);
		if (chkBuf == null)
			return;

		// if (!MatrixHelper.isChunkInFrustum(x, y, z))
		// continue;

		Shader.setProjectionMatrix(shader, manager.getProjMatrix());
		Shader.setViewMatrix(manager.getViewMatrix(), shader);

		shader.bind();

		GLHelper.checkGLError("BlockRenderer - Before");
		chkBuf.draw();
		GLHelper.checkGLError("BlockRenderer - Post");

		shader.unbind();

	}

	public void release() {
		chunkBuffers.values().forEach(ChunkBuffer::release);
	}

	public WorldRenderer getManager() {
		return manager;
	}

	public Shader getShader() {
		return shader;
	}

	public CraftGameContext getContext() {
		return ctx;
	}

}
