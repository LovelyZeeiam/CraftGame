package xueli.mcremake.client.renderer.world;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2i;

import xueli.game2.camera3d.BoundCamera;
import xueli.game2.camera3d.ICamera;
import xueli.game2.display.Display;
import xueli.game2.resource.ResourceHolder;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.WorldEvents;
import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.core.world.WorldDimension;

public class WorldRenderer implements ResourceHolder {

	private final CraftGameClient ctx;

	private final TerrainTexture terrainTexture;

	private final WorldDimension world;

	private final ConcurrentLinkedQueue<Vector2i> chunkRebuiltList = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Vector2i> chunkRemoveList = new ConcurrentLinkedQueue<>();

	private final HashMap<Class<? extends ChunkRenderType>, ChunkRenderType> renderTypes = new HashMap<>();

	private BoundCamera camera = new BoundCamera(null);
	private Matrix4f viewMatrix, projMatrix;

	public WorldRenderer(CraftGameClient ctx) {
		this.ctx = ctx;
		this.world = ctx.getUnsafeImmediateWorld();
		this.terrainTexture = new TerrainTexture(ctx);

		ctx.WorldEventBus.register(WorldEvents.NewChunkEvent.class, this::onCreateNewChunk);
		ctx.WorldEventBus.register(WorldEvents.ModifyBlockEvent.class, this::onModifyBlock);
		ctx.WorldEventBus.register(WorldEvents.UnloadChunkEvent.class, this::onRemoveChunk);

		renderTypes.put(RenderTypeSolid.class, new RenderTypeSolid(this));

	}

	@Override
	public void reload() {
		this.terrainTexture.reload();

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
			Chunk chunk = world.getChunk(v.x, v.y);
			if(chunk != null) {
				ChunkRenderBuildManager manager = new ChunkRenderBuildManager(v, this) {
					@Override
					@SuppressWarnings("unchecked")
					protected <T extends ChunkRenderType> T getRenderType(Class<T> clazz) {
						return (T) renderTypes.get(clazz);
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
		this.projMatrix = getPerspectiveMatrix(display.getWidth(), display.getHeight(), 110.0f, 0.01f, 999999.9f);

		for (ChunkRenderType type : renderTypes.values()) {
			type.applyMatrix("viewMatrix", viewMatrix);
			type.applyMatrix("projMatrix", projMatrix);
			type.render();
		}

	}

	public void release() {
		for (ChunkRenderType type : renderTypes.values()) {
			type.release();
		}

	}

	private Matrix4f getPerspectiveMatrix(float width, float height, float fov, float near, float far) {
		Matrix4f projectionMatrix = new Matrix4f();

		float ratio = width / height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2F))) * ratio);
		float x_scale = y_scale / ratio;
		float frustum_length = far - near;

		projectionMatrix.setIdentity();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -(far + near) / frustum_length;
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * far * near) / frustum_length);
		projectionMatrix.m33 = 0;

		return projectionMatrix;
	}

	public TerrainTexture getTerrainTexture() {
		return terrainTexture;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public Matrix4f getProjMatrix() {
		return projMatrix;
	}

}
