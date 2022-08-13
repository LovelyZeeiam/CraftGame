package xueli.craftgame.renderer.blocks;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.Blocks;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.setting.Settings;
import xueli.game.utils.GLHelper;
import xueli.game.vector.Vector;
import xueli.game2.renderer.legacy.RenderMaster;
import xueli.game2.renderer.legacy.system.RenderSystem;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.shader.Shader;
import xueli.game2.resource.submanager.render.shader.ShaderResourceLocation;
import xueli.utils.Int2HashMap;

public class IBlockRenderer {

	public static final ResourceLocation SOLID_BLOCKS_TEXTURES = new ResourceLocation("images/blocks/");
	public static final ShaderResourceLocation SOLID_BLOCKS_SHADERS = new ShaderResourceLocation(
			new ResourceLocation("shaders/world/world.vert"),
			new ResourceLocation("shaders/world/world.frag")
	);

	private WorldRenderer manager;

	private Int2HashMap<ChunkBuffer> chunkBuffers = new Int2HashMap<>();

	private Shader shader;
	private RenderSystem renderer;

	// Just Initialize Here
	public IBlockRenderer(WorldRenderer manager) {
		this.manager = manager;

	}

	public void init() {
		CraftGameContext context = manager.getContext();
		this.shader = context.getShaderRenderResource().preRegister(SOLID_BLOCKS_SHADERS, true);
		context.getAtlasTextureResource().findAndRegister(SOLID_BLOCKS_TEXTURES, key -> true);

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

	public void draw() {
		LocalPlayer player = manager.getPlayer();
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

				GLHelper.checkGLError("BlockRenderer - Before");
				chkBuf.draw();
				GLHelper.checkGLError("BlockRenderer - Post");

			}
		}

	}

	public void release() {
		chunkBuffers.values().forEach(ChunkBuffer::release);
	}

	public WorldRenderer getManager() {
		return manager;
	}

}
