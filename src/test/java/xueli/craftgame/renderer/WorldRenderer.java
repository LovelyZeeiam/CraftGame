package xueli.craftgame.renderer;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.eventbus.Subscribe;

import xueli.craftgame.CraftGameTest;
import xueli.craftgame.event.EventChunkMeshShouldRebuilt;
import xueli.craftgame.world.World;
import xueli.game.renderer.FrameBuffer;
import xueli.game.renderer.ScreenQuadRenderer;
import xueli.utils.Int2HashMap;

public class WorldRenderer {

	private CraftGameTest ctx;
	private World world;

	private ExecutorService chunkRebuiltExecutor = Executors.newWorkStealingPool();
	private Int2HashMap<Integer> glListMap = new Int2HashMap<>();

	public WorldRenderer(CraftGameTest ctx) {
		this.ctx = ctx;
		this.world = ctx.getWorld();

	}

	private FrameBuffer frameBuffer;
	private ScreenQuadRenderer scrQuadRenderer;

	public void init() {
		this.frameBuffer = new FrameBuffer();
		this.scrQuadRenderer = new ScreenQuadRenderer();

	}

	public void render() {
		frameBuffer.use();

		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		frameBuffer.unbind();

		scrQuadRenderer.render(frameBuffer.getTbo_image());

	}

	public void onSize(int width, int height) {
		this.frameBuffer.resize(width, height);
	}

	public void release() {

	}

	@Subscribe
	public void onMeshShouldRebuilt(EventChunkMeshShouldRebuilt event) {
		chunkRebuiltExecutor.execute(() -> rebuiltChunkRenderList(event));
	}

	private void rebuiltChunkRenderList(EventChunkMeshShouldRebuilt event) {

	}

}
