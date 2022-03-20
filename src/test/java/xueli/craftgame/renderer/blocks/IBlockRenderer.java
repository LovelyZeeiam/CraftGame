package xueli.craftgame.renderer.blocks;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.Blocks;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.setting.Settings;
import xueli.game.utils.GLHelper;
import xueli.game.utils.Shader;
import xueli.game.vector.Vector;
import xueli.utils.Int2HashMap;

public class IBlockRenderer {

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

	}

	protected void setShader(String vertSource, String fragSource) {
		this.shader = Shader.getShader(vertSource, fragSource);
	}

	public void newChunkBuffer(int x, int z) {
		if (!chunkBuffers.containsKey(x, z)) {
			chunkBuffers.put(x, z, new ChunkBuffer(x, z));
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

	public void draw() {
		Vector camera = player.getCamera();
		int playerInChunkX = (int) camera.x >> 4;
		int playerInChunkZ = (int) camera.z >> 4;

		for (int x = playerInChunkX - Settings.INSTANCE.RENDER_DISTANCE; x < playerInChunkX
				+ Settings.INSTANCE.RENDER_DISTANCE; x++) {
			for (int z = playerInChunkZ - Settings.INSTANCE.RENDER_DISTANCE; z < playerInChunkZ
					+ Settings.INSTANCE.RENDER_DISTANCE; z++) {
				ChunkBuffer chkBuf = getChunkBuffer(x, z);
				if (chkBuf == null)
					continue;

				// if (!MatrixHelper.isChunkInFrustum(x, y, z))
				// continue;

				Shader.setProjectionMatrix(shader, manager.getProjMatrix());
				Shader.setViewMatrix(manager.getViewMatrix(), shader);

				shader.use();

				GLHelper.checkGLError("BlockRenderer - Before");
				Blocks.blockTextureAtlas.bind();
				chkBuf.draw();
				Blocks.blockTextureAtlas.unbind();
				GLHelper.checkGLError("BlockRenderer - Post");

				shader.unbind();

			}
		}

	}

	public void release() {
		chunkBuffers.values().forEach(buffer -> buffer.release());
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
