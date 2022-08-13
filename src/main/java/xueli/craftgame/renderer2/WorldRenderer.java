package xueli.craftgame.renderer2;

import org.lwjgl.utils.vector.Matrix4f;
import xueli.craftgame.CraftGameContext;
import xueli.craftgame.event.EventLoadChunk;
import xueli.craftgame.event.EventRemoveChunk;
import xueli.craftgame.event.EventSetBlock;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.world.World;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.vector.Vector2i;
import xueli.game2.display.Display;
import xueli.game2.lifecycle.LifeCycle;
import xueli.utils.Int2HashMap;

import static org.lwjgl.opengl.GL11.*;

public class WorldRenderer implements LifeCycle {

	private CraftGameContext ctx;
	private Display display;
	private World world;
	private LocalPlayer player;

	private Int2HashMap<ChunkRenderer> chunks = new Int2HashMap<>();

	public WorldRenderer(LocalPlayer player, CraftGameContext ctx) {
		this.player = player;
		this.ctx = ctx;
		this.world = ctx.getWorld();
		this.display = ctx.getDisplay();

	}

	@Override
	public void init() {

	}

	private Matrix4f projMatrix, viewMatrix;

	@Override
	public void tick() {
		this.projMatrix = MatrixHelper.perspecive(display.getWidth(), display.getHeight(), player.getFov(), 0.01f, 99999999.0f);
		this.viewMatrix = MatrixHelper.player(player.getCamera());

		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);



		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);

	}

	public void onNewChunk(EventLoadChunk event) {
		int x = event.getX();
		int z = event.getZ();
		ChunkRenderer chunkRenderer = new ChunkRenderer(x, z, this);
		chunkRenderer.init();
		callRebuiltChunkRenderList(x, z);
		chunks.put(x, z, chunkRenderer);

	}

	public void onMeshShouldRebuilt(EventSetBlock event) {
		int x = event.getX();
		int z = event.getZ();

		Vector2i chunkPos = World.getLocatedChunkPos(x, z);
		callRebuiltChunkRenderList(chunkPos.x, chunkPos.y);

		// Check the border
		int inChunkX = x - (chunkPos.x << 4);
		int inChunkZ = z - (chunkPos.y << 4);

		if (inChunkX == 0) {
			callRebuiltChunkRenderList(chunkPos.x - 1, chunkPos.y);
		}
		if (inChunkX == 15) {
			callRebuiltChunkRenderList(chunkPos.x + 1, chunkPos.y);
		}
		if (inChunkZ == 0) {
			callRebuiltChunkRenderList(chunkPos.x, chunkPos.y - 1);
		}
		if (inChunkZ == 15) {
			callRebuiltChunkRenderList(chunkPos.x, chunkPos.y + 1);
		}

	}

	private void callRebuiltChunkRenderList(int x, int y) {
		ChunkRenderer chunkRenderer = chunks.get(x, y);
		if(chunkRenderer == null) return;
		chunkRenderer.reportRebuilt();

	}

	public void onRemoveChunk(EventRemoveChunk event) {
		int x = event.getX();
		int z = event.getZ();
		ChunkRenderer chunkRenderer = chunks.remove(x, z);
		if(chunkRenderer != null) {
			chunkRenderer.release();
		}

	}

	public Matrix4f getProjMatrix() {
		return projMatrix;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	@Override
	public void release() {
		chunks.values().forEach(ChunkRenderer::release);
		chunks.clear();

	}

	public World getWorld() {
		return world;
	}

}
