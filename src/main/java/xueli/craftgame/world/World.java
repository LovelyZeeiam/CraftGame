package xueli.craftgame.world;

import java.util.concurrent.CopyOnWriteArrayList;

import com.flowpowered.nbt.CompoundMap;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.event.EventLoadChunk;
import xueli.craftgame.event.EventRequireChunk;
import xueli.craftgame.event.EventSetBlock;
import xueli.game.vector.Vector2i;
import xueli.utils.Int2HashMap;

public class World {

	private CraftGameContext ctx;

	private CopyOnWriteArrayList<EventRequireChunk> requireChunkFutures = new CopyOnWriteArrayList<>();

	private Int2HashMap<SubChunk> chunks = new Int2HashMap<>();

	public World(CraftGameContext ctx) {
		this.ctx = ctx;

	}

	// TODO: FOR LIMITED WORLD
	public void chunkInit() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				requireChunk(i, j);
			}
		}
	}

	private void requireChunk(int x, int z) {
		EventRequireChunk require = new EventRequireChunk(x, z);
		ctx.submitEventToMainTicker(require);
		requireChunkFutures.add(require);
	}

	public SubChunk getChunk(int x, int z) {
		return chunks.get(x, z);
	}

	public BlockType getBlock(int x, int y, int z) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return null;
		try {
			return chunk.grid[x - (chunk.getChunkX() << 4)][y][z - (chunk.getChunkZ() << 4)];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public CompoundMap getBlockTag(int x, int y, int z) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return null;
		try {
			return chunk.tags[x - (chunk.getChunkX() << 4)][y][z - (chunk.getChunkZ() << 4)];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setBlockTag(int x, int y, int z, CompoundMap tag) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return;
		try {
			chunk.tags[x - (chunk.getChunkX() << 4)][y][z - (chunk.getChunkZ() << 4)] = tag;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
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

	public boolean canOperateBlock(int x, int y, int z) {
		SubChunk chunk = chunks.get(x >> 4, z >> 4);
		if (chunk == null)
			return false;
		if (y < 0)
			return false;
		return true;
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

	public static Vector2i getLocatedChunkPos(int x, int z) {
		return new Vector2i(x >> 4, z >> 4);
	}

	public boolean hasChunk(int x, int z) {
		return chunks.containsKey(x, z);
	}

	public void tick() {
		for (EventRequireChunk future : requireChunkFutures) {
			if (future.isDone()) {
				SubChunk chunk = future.getValue();
				if (chunk == null)
					continue;

				chunks.put(chunk.getChunkX(), chunk.getChunkZ(), chunk);
				ctx.submitEvent(new EventLoadChunk(chunk.getChunkX(), chunk.getChunkZ()));
				requireChunkFutures.remove(future);
			}
		}

	}

	public void release() {
	}

	public void onSetBlock(EventSetBlock event) {
		switch (event.getEventType()) {
		case BLOCK -> setBlock(event.getX(), event.getY(), event.getZ(), event.getBlock());
		case TAG -> setBlockTag(event.getX(), event.getY(), event.getZ(), event.getTag());
		case BLOCK_TAG -> setBlock(event.getX(), event.getY(), event.getZ(), event.getBlock(), event.getTag());
		}

	}

	public CraftGameContext getContext() {
		return ctx;
	}

}
