package xueli.mcremake.client.renderer.world;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2i;

import xueli.game2.camera3d.BoundCamera;
import xueli.game2.camera3d.ICamera;
import xueli.game2.display.Display;
import xueli.game2.ecs.ResourceListGeneric;
import xueli.game2.math.MatrixHelper;
import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.game2.resource.ResourceHolder;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.WorldEvents;
import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.core.world.WorldDimension;

public class WorldRenderer implements ResourceHolder {

	private final CraftGameClient ctx;

	private final WorldDimension world;

	private final ConcurrentLinkedQueue<Vector2i> chunkRebuiltList = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Vector2i> chunkRemoveList = new ConcurrentLinkedQueue<>();

	private final ResourceListGeneric<ChunkRenderType> renderTypes;

	private BoundCamera camera = new BoundCamera(null);
	private Matrix4f viewMatrix, projMatrix;

	public WorldRenderer(ResourceListGeneric<ChunkRenderType> renderTypes, CraftGameClient ctx) {
		this.ctx = ctx;
		this.world = ctx.getUnsafeImmediateWorld();

		ctx.worldBus.register(WorldEvents.NewChunkEvent.class, this::onCreateNewChunk);
		ctx.worldBus.register(WorldEvents.ModifyBlockEvent.class, this::onModifyBlock);
		ctx.worldBus.register(WorldEvents.UnloadChunkEvent.class, this::onRemoveChunk);

		this.renderTypes = renderTypes;

	}

	@Override
	public void reload() {
	}

	public void setCamera(ICamera camera) {
		this.camera.setCamera(camera);
	}

	public void onCreateNewChunk(WorldEvents.NewChunkEvent event) {
		chunkRebuiltList.add(new Vector2i(event.x(), event.z()));
	}

	public void onModifyBlock(WorldEvents.ModifyBlockEvent event) {
		Vector2i inChunkPos = new Vector2i();
		Vector2i chunkPos = Chunk.toChunkPos(event.x(), event.z(), inChunkPos);
		chunkRebuiltList.add(chunkPos);

		if(inChunkPos.x == 0) {
			chunkRebuiltList.add(new Vector2i(chunkPos.x - 1, chunkPos.y));
		} else if(inChunkPos.x == Chunk.CHUNK_SIZE - 1) {
			chunkRebuiltList.add(new Vector2i(chunkPos.x + 1, chunkPos.y));
		}

		if(inChunkPos.y == 0) {
			chunkRebuiltList.add(new Vector2i(chunkPos.x, chunkPos.y - 1));
		} else if(inChunkPos.y == Chunk.CHUNK_SIZE - 1) {
			chunkRebuiltList.add(new Vector2i(chunkPos.x, chunkPos.y + 1));
		}


	}

	public void onRemoveChunk(WorldEvents.UnloadChunkEvent event) {
		chunkRemoveList.add(new Vector2i(event.x(), event.z()));
	}

	private void doTasks() {
		Vector2i v;
		v = chunkRebuiltList.poll();
		if(v != null) {
			for (ChunkRenderType type : renderTypes.values()) {
				RenderBuffer buf = type.getRenderBuffer(v);
				buf.clear();
			}
			
			Chunk chunk = world.getChunk(v.x, v.y);
			if(chunk != null) {
				ChunkRenderBuildManager manager = new ChunkRenderBuildManager(v) {
					@Override
					public <T extends ChunkRenderType> T getRenderType(Class<T> clazz) {
						return renderTypes.get(clazz);
					}
				};
				new ChunkRebuiltTask(v.x, v.y, chunk, manager).run();
				manager.flip();
			}
		}

		v = chunkRemoveList.poll();
		if(v != null) {
			for (ChunkRenderType type : renderTypes.values()) {
				type.releaseRenderBuffer(v);
			}
		}

	}

	public void render() {
		this.doTasks();

		Display display = ctx.getDisplay();

		this.viewMatrix = camera.getCameraMatrix();
		this.projMatrix = MatrixHelper.perspective(display.getWidth(), display.getHeight(), 110.0f, 0.01f, 999999.9f);

		for (ChunkRenderType type : renderTypes.values()) {
			type.applyMatrix("viewMatrix", viewMatrix);
			type.applyMatrix("projMatrix", projMatrix);
			type.render();
		}

	}

	public void release() {
		for (Object type : renderTypes.values()) {
			((ChunkRenderType) type).release();
		}

	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public Matrix4f getProjMatrix() {
		return projMatrix;
	}

}
