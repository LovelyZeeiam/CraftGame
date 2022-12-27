package xueli.mcremake.classic.client.renderer.world;

import xueli.game.vector.Vector2i;
import xueli.mcremake.classic.client.CraftGameClient;
import xueli.mcremake.classic.client.WorldEvents;
import xueli.mcremake.classic.core.world.WorldDimension;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorldRenderer {

	private final CraftGameClient ctx;
	private final WorldDimension world;

	private final ConcurrentLinkedQueue<Vector2i> chunkRebuiltList = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Vector2i> chunkRemoveList = new ConcurrentLinkedQueue<>();

	private final HashMap<Class<? extends ChunkRenderType>, ChunkRenderType> renderTypes = new HashMap<>();

	public WorldRenderer(CraftGameClient ctx) {
		this.ctx = ctx;
		this.world = ctx.getWorld();

		ctx.WorldEventBus.register(WorldEvents.NewChunkEvent.class, this::onCreateNewChunk);
		ctx.WorldEventBus.register(WorldEvents.ModifyBlockEvent.class, this::onModifyBlock);
		ctx.WorldEventBus.register(WorldEvents.UnloadChunkEvent.class, this::onRemoveChunk);

		renderTypes.put(RenderTypeSolid.class, new RenderTypeSolid());

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
			new ChunkRebuiltTask(world.getChunk(v.x, v.y), new ChunkRebuiltManager(v) {
				@Override
				@SuppressWarnings("unchecked")
				protected <T extends ChunkRenderType> T getRenderType(Class<T> clazz) {
					return (T) renderTypes.get(clazz);
				}
			}).run();
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

		for (ChunkRenderType type : renderTypes.values()) {
			type.render();
		}

	}

	public void release() {
		for (ChunkRenderType type : renderTypes.values()) {
			type.release();
		}

	}

}
