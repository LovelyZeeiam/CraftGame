package xueli.mcremake.core.world;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.lwjgl.utils.vector.Vector2i;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.events.NewChunkEvent;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.level.worldgen.LandOrRiverLayer;
import xueli.mcremake.registry.GameRegistry;

public class WorldDimension implements WorldAccessible {

	private final CraftGameClient ctx;
	private final ConcurrentHashMap<Vector2i, Chunk> chunkMap = new ConcurrentHashMap<>();

	public WorldDimension(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	public void init() { // Maybe it should be a startup component
//		RandomChunkProvider chunkGenerator = new RandomChunkProvider(666);
		
//		for (int i = -8; i < 8; i++) {
//			for (int j = -8; j < 8; j++) {
//				final Vector2i chunkPos = new Vector2i(i, j);
//				chunkGenerator.getChunk(j, i, ctx.getAsyncExecutor())
//					.thenAcceptAsync(chunk -> {
//						chunkMap.put(chunkPos, chunk);
//						ctx.eventbus.post(new NewChunkEvent(chunkPos.x, chunkPos.y));
//					}, ctx.getMainThreadExecutor())
//					.exceptionally(e -> {
//						e.printStackTrace();
//						return null;
//					});
//			}
//		}
		
		Random random = new Random(666);
		int[][] landRiverLayer = new int[64][64];
		for(int x = 0; x < 64; x++) {
			for(int y = 0; y < 64; y++) {
				landRiverLayer[x][y] = random.nextInt(10) > 5 ? 1 : 0;
			}
		}
		landRiverLayer = LandOrRiverLayer.scaleAndMerge(landRiverLayer, 64, 64);
		landRiverLayer = LandOrRiverLayer.scaleAndMerge(landRiverLayer, 128, 128);
		LandOrRiverLayer.merge(landRiverLayer, 256, 256);
		LandOrRiverLayer.merge(landRiverLayer, 256, 256);
		LandOrRiverLayer.merge(landRiverLayer, 256, 256);
		LandOrRiverLayer.merge(landRiverLayer, 256, 256);
		
		for(int x = 0; x < 256; x++) {
			for(int y = 0; y < 256; y++) {
				landRiverLayer[x][y] *= 5;
			}
		}
		
		for(int i = 5; i > 0; i--) {
			for(int x = 0; x < 256; x++) {
				for(int y = 0; y < 256; y++) {
					if(landRiverLayer[x][y] == 0 && LandOrRiverLayer.findValue(landRiverLayer, 256, 256, x, y, i) > 0) {
						landRiverLayer[x][y] = i - 1;
					}
				}
			}
		}
		
		final int[][] finalLayer = landRiverLayer;
		
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				final Vector2i chunkPos = new Vector2i(i, j);
				CompletableFuture.supplyAsync(() -> {
					Chunk chunk = new Chunk();
					for(int x = 0; x < 16; x++) {
						for(int z = 0; z < 16; z++) {
							int height = finalLayer[chunkPos.x * 16 + x][chunkPos.y * 16 + z];
							chunk.setBlockImmediate(x, 70 + height, z, height <= 2 ? GameRegistry.BLOCK_DIRT : GameRegistry.BLOCK_GRASS);
							for(int y = 0; y < height + 70; y++) {
								chunk.setBlockImmediate(x, y, z, GameRegistry.BLOCK_DIRT);
							}
							for(int y = 73; y > 70; y--) {
								if(chunk.getBlock(x, y, z) == null)
									chunk.setBlockImmediate(x, y, z, GameRegistry.BLOCK_WATER);
							}
							
						}
					}
					
					chunk.recalcHeightMap();
					return chunk;
				}, ctx.getAsyncExecutor()).thenAcceptAsync(chunk -> {
					chunkMap.put(chunkPos, chunk);
					ctx.eventbus.post(new NewChunkEvent(chunkPos.x, chunkPos.y));
				}, ctx.getMainThreadExecutor()).exceptionally(e -> {
					e.printStackTrace();
					return null;
				});
				
			}
		}

	}

	public WorldAccessible createAccessible() {
		return this;
	}

	@Override
	public BlockType getBlock(int x, int y, int z) {
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		return chunk == null ? null : chunk.getBlock(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE));
	}

	@Override
	public CompoundMap getBlockTag(int x, int y, int z) {
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		return chunk == null ? null : chunk.getBlockTag(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE));
	}

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		if(chunk != null) {
			chunk.setBlock(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE), block);
		}

	}

	@Override
	public void modifyBlockTag(int x, int y, int z, Consumer<CompoundMap> c) {
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		if(chunk == null) return;
		chunk.modifyBlockTag(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE), c);

	}

	public Chunk getChunk(int x, int z) {
		return chunkMap.get(new Vector2i(x, z));
	}

}
