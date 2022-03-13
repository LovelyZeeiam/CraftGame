package xueli.craftgame.world;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.flowpowered.nbt.CompoundMap;
import com.google.common.eventbus.Subscribe;

import xueli.craftgame.block.BlockType;
import xueli.craftgame.event.EventSetBlock;
import xueli.utils.Int2HashMap;

public class World {

	private WorldIO provider;
	private CopyOnWriteArrayList<Future<SubChunk>> requireChunkFutures = new CopyOnWriteArrayList<>();

	private Int2HashMap<SubChunk> chunks = new Int2HashMap<>();

	public World() {
		this.provider = new WorldIO(this);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				requireChunk(i, j);
			}
		}

	}

	private void requireChunk(int x, int z) {
		requireChunkFutures.add(provider.getChunk(x, z));
	}

	public SubChunk getChunk(int x, int z) {
		return chunks.get(x, z);
	}

	public BlockType getBlock(int x, int y, int z) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return null;
		return chunk.grid[x - (chunk.getChunkX() << 4)][y][z - (chunk.getChunkZ() << 4)];
	}

	public CompoundMap getBlockTag(int x, int y, int z) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return null;
		return chunk.tags[x - (chunk.getChunkX() << 4)][y][z - (chunk.getChunkZ() << 4)];
	}

	public void setBlockTag(int x, int y, int z, CompoundMap tag) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return;
		chunk.tags[x - (chunk.getChunkX() << 4)][y][z - (chunk.getChunkZ() << 4)] = tag;
	}

	public void setBlock(int x, int y, int z, BlockType tile) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return;

		int inchunkX = x - (chunk.getChunkX() << 4);
		int inchunkZ = z - (chunk.getChunkZ() << 4);

		chunk.grid[inchunkX][y][inchunkZ] = tile;
		refreshHeightMap(inchunkX, y, inchunkZ, tile, chunk);

	}

	public void setBlock(int x, int y, int z, BlockType tile, CompoundMap tag) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return;

		int inchunkX = x - (chunk.getChunkX() << 4);
		int inchunkZ = z - (chunk.getChunkZ() << 4);

		chunk.grid[inchunkX][y][inchunkZ] = tile;
		chunk.tags[inchunkX][y][inchunkZ] = tag;

		refreshHeightMap(inchunkX, y, inchunkZ, tile, chunk);

	}

	private void refreshHeightMap(int inchunkX, int y, int inchunkZ, BlockType tile, SubChunk chunk) {
		if (y > chunk.heightmap[inchunkX][inchunkZ] && tile != null) {
			chunk.heightmap[inchunkX][inchunkZ] = y;
		}
		if (y == chunk.heightmap[inchunkX][inchunkZ] && tile == null) {
			for (int i = chunk.heightmap[inchunkX][inchunkZ]; i >= 0; i--) {
				chunk.heightmap[inchunkX][inchunkZ] = i;
				if (chunk.grid[inchunkX][i][inchunkZ] != null)
					break;
			}
		}
	}

	public void tick() {
		provider.tick();

		for (Future<SubChunk> future : requireChunkFutures) {
			if (future.isDone()) {
				SubChunk chunk = null;
				try {
					chunk = future.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}

				chunks.put(chunk.getChunkX(), chunk.getChunkZ(), chunk);
				requireChunkFutures.remove(future);
			} else if (future.isCancelled()) {
				requireChunkFutures.remove(future);
			}
		}

	}

	public void release() {
		this.provider.release();

	}

	@Subscribe
	public void onSetBlock(EventSetBlock event) {
		switch (event.getEventType()) {
		case BLOCK -> setBlock(event.getX(), event.getY(), event.getZ(), event.getBlock());
		case TAG -> setBlockTag(event.getX(), event.getY(), event.getZ(), event.getTag());
		case BLOCK_TAG -> setBlock(event.getX(), event.getY(), event.getZ(), event.getBlock(), event.getTag());
		}

		provider.postEvent(event);

	}

	public WorldIO getProvider() {
		return provider;
	}

}
