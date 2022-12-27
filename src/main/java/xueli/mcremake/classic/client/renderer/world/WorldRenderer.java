package xueli.mcremake.classic.client.renderer.world;

import org.lwjgl.utils.vector.Matrix4f;
import xueli.game.vector.Vector2i;
import xueli.game2.display.Display;
import xueli.game2.resource.ResourceHolder;
import xueli.mcremake.classic.client.CraftGameClient;
import xueli.mcremake.classic.client.WorldEvents;
import xueli.mcremake.classic.core.world.WorldDimension;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorldRenderer implements ResourceHolder {

	private final CraftGameClient ctx;

	private final TerrainTexture terrainTexture;

	private final WorldDimension world;

	private final ConcurrentLinkedQueue<Vector2i> chunkRebuiltList = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Vector2i> chunkRemoveList = new ConcurrentLinkedQueue<>();

	private final HashMap<Class<? extends ChunkRenderType>, ChunkRenderType> renderTypes = new HashMap<>();

	public WorldRenderer(CraftGameClient ctx) {
		this.ctx = ctx;
		this.world = ctx.getWorld();
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

	public void onCreateNewChunk(WorldEvents.NewChunkEvent event) {
		chunkRebuiltList.add(new Vector2i(event.x(), event.z()));
	}

	public void onModifyBlock(WorldEvents.ModifyBlockEvent event) {
		chunkRebuiltList.add(new Vector2i(event.x(), event.z()));
	}

	public void onRemoveChunk(WorldEvents.UnloadChunkEvent event) {
		chunkRemoveList.add(new Vector2i(event.x(), event.z()));
	}

	private void doTasks() {
		Vector2i v;
		v = chunkRebuiltList.poll();
		if(v != null) {
			ChunkRenderBuildManager manager = new ChunkRenderBuildManager(v, this) {
				@Override
				@SuppressWarnings("unchecked")
				protected <T extends ChunkRenderType> T getRenderType(Class<T> clazz) {
					return (T) renderTypes.get(clazz);
				}
			};
			new ChunkRebuiltTask(v.x, v.y, world.getChunk(v.x, v.y), manager).run();
			manager.flip();
		}

		v = chunkRemoveList.poll();
		if(v != null) {
			for (ChunkRenderType type : renderTypes.values()) {
				type.releaseRenderBuffer(v);
			}
		}

	}

	public void render(float partialTick) {
		this.doTasks();

		Display display = ctx.getDisplay();

		Matrix4f viewMatrix = ctx.getPlayer().getViewMatrix(partialTick);
		Matrix4f projMatrix = getPerspectiveMatrix(display.getWidth(), display.getHeight(), 110.0f, 0.01f, 999999.9f);

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

}
