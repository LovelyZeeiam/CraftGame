package xueli.craftgame.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Matrix4f;
import xueli.craftgame.CraftGameContext;
import xueli.craftgame.event.EventLoadChunk;
import xueli.craftgame.event.EventRemoveChunk;
import xueli.craftgame.event.EventSetBlock;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.renderer.blocks.BlockBorderRenderer;
import xueli.craftgame.renderer.blocks.ChunkBuilder;
import xueli.craftgame.renderer.blocks.IBlockRenderer;
import xueli.utils.ExecutorThisThread;
import xueli.craftgame.world.World;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.vector.Vector2i;
import xueli.game2.display.Display;
import xueli.game2.renderer.legacy.RenderMaster;
import xueli.utils.Elegance;
import xueli.utils.Int2HashMap;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

public class WorldRenderer implements IGameRenderer {

	private CraftGameContext ctx;
	private World world;

	private LocalPlayer player;
	private Matrix4f projMatrix, viewMatrix;

	private Vector<Vector2i> queueChunkRebuildList = new Vector<>();
	private ExecutorService chunkRebuiltExecutor = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
				Thread thread = new Thread(r);
				thread.setUncaughtExceptionHandler((t, e) -> e.printStackTrace());
				return thread;
			});

	private ExecutorThisThread threadSafeExecutor = new ExecutorThisThread();

	public WorldRenderer(LocalPlayer player, CraftGameContext ctx) {
		this.ctx = ctx;
		this.player = player;
		this.world = ctx.getWorld();

	}

	private RenderMaster renderer = new RenderMaster();

//	private FrameBuffer frameBuffer;
//	private ScreenQuadRenderer scrQuadRenderer;

	// Renderers
	public static final int RENDERER_CUBE = 0;
	private ArrayList<IBlockRenderer> blockRenderers = Elegance.make(new ArrayList<IBlockRenderer>(), list -> {
		list.add(RENDERER_CUBE, new IBlockRenderer(this));
		return list;
	});

	private BlockBorderRenderer borderRenderer;

	private Int2HashMap<ReadWriteLock> chunkBufferGenLocks = new Int2HashMap<>();

	public void init() {
//		this.frameBuffer = new FrameBuffer();
//		this.scrQuadRenderer = new ScreenQuadRenderer();

		blockRenderers.forEach(IBlockRenderer::init);

		this.borderRenderer = new BlockBorderRenderer(this);
		this.borderRenderer.init();

	}

	public IBlockRenderer rendererCube() {
		return blockRenderers.get(RENDERER_CUBE);
	}

	// TODO: FRUSTUM CULLING
	public void render() {
		threadSafeExecutor.peekAndRunAll();

		Display display = ctx.getDisplay();
		this.projMatrix = MatrixHelper.perspecive(display.getWidth(), display.getHeight(), player.getFov(), 0.01f,
				99999999.0f);
		this.viewMatrix = MatrixHelper.player(player.getCamera());

//		frameBuffer.use();
		glEnable(GL11.GL_CULL_FACE);
		glEnable(GL11.GL_DEPTH_TEST);

		blockRenderers.forEach(IBlockRenderer::draw);

		borderRenderer.render();

		glDisable(GL11.GL_DEPTH_TEST);
		glDisable(GL11.GL_CULL_FACE);

//		frameBuffer.unbind();

//		scrQuadRenderer.render(frameBuffer.getTbo_image());

	}

//	@Override
//	public void onSize(int width, int height) {
//		this.frameBuffer.resize(width, height);
//	}

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

	private void callRebuiltChunkRenderList(int x, int z) {
		if (!world.hasChunk(x, z))
			return;
		if (queueChunkRebuildList.contains(new Vector2i(x, z)))
			return;
		// System.out.println(x + ", " + z);
		queueChunkRebuildList.add(new Vector2i(x, z));
		chunkRebuiltExecutor.execute(() -> {
			queueChunkRebuildList.remove(new Vector2i(x, z));

			// TODO: BUG DOESN'T WORK
			// WHEN FAST MODIFY THE BLOCK CAUSING TWO BUFFER GENERATOR TO WORK AT ONE TIME
			// A BUFFEROVERFLOWEXCEPTION WILL BE THROWN
			ReadWriteLock lock = chunkBufferGenLocks.get(x, z);
			lock.writeLock().lock();
			try {
				new ChunkBuilder(x, z, world, this).run();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.writeLock().unlock();
			}

		});

	}

	public void onNewChunk(EventLoadChunk event) {
		int x = event.getX();
		int z = event.getZ();
		threadSafeExecutor.execute(() -> {
			blockRenderers.forEach(r -> {
				r.newChunkBuffer(x, z);
			});
			chunkBufferGenLocks.put(x, z, new ReentrantReadWriteLock());
		});

	}

	public void onRemoveChunk(EventRemoveChunk event) {
		int x = event.getX();
		int z = event.getZ();
		threadSafeExecutor.execute(() -> {
			blockRenderers.forEach(r -> {
				r.removeChunkBuffer(x, z);
			});
		});

	}

	public World getWorld() {
		return world;
	}

	public LocalPlayer getPlayer() {
		return player;
	}

	public Matrix4f getProjMatrix() {
		return projMatrix;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public ArrayList<IBlockRenderer> getBlockRenderers() {
		return blockRenderers;
	}

	public void release() {
		chunkRebuiltExecutor.shutdownNow();

		// TODO
		blockRenderers.forEach(IBlockRenderer::release);

	}

	public CraftGameContext getContext() {
		return ctx;
	}

}
