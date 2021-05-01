package xueli.craftgame.world;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.logging.Logger;

import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.renderer.world.WorldRenderer;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.vector.Vector;

public class ChunkProvider {

	private static Logger logger = Logger.getLogger(ChunkProvider.class.getName());

	private Dimension dimension;

	private ChunkGenerator generator;
	private LinkedList<Operation> operations = new LinkedList<>();

	private String regionPath = ".cg/saves/Test/regions/";
	private DimensionLevel level;

	public ChunkProvider(Dimension dimension) {
		this.dimension = dimension;
		this.generator = new ChunkGenerator(dimension);
		this.level = new DimensionLevel(regionPath, dimension);

	}

	public void requireLoad(int x, int y, int z) {
		Operation operation = new Operation(OperationType.LOAD, x, y, z);
		if (operations.indexOf(operation) < 0)
			operations.add(operation);

	}

	public void requireSave(int x, int y, int z) {
		Operation operation = new Operation(OperationType.SAVE, x, y, z);
		if (operations.indexOf(operation) < 0)
			operations.add(operation);

	}

	private void load(int x, int y, int z) {
		logger.fine(MessageFormat.format("[World] Load: {0}, {1}, {2}", x, y, z));
		if (level.checkChunkExist(x, y, z)) {
			this.dimension.chunks.put(new Vector3i(x, y, z), level.getChunk(x, y, z));
		} else {
			this.dimension.chunks.put(new Vector3i(x, y, z), generator.genChunk(x, y, z));
		}

	}

	private void save(int x, int y, int z) {
		logger.fine(MessageFormat.format("[World] Save: {0}, {1}, {2}", x, y, z));
		this.level.writeChunk(this.dimension.chunks.get(new Vector3i(x, y, z)));
		this.dimension.chunks.remove(new Vector3i(x, y, z));

	}

	private long time0 = System.currentTimeMillis();

	public void tick(Vector playerPos) {
		Operation operation = null;
		if ((operation = operations.poll()) != null) {
			switch (operation.type) {
			case LOAD: {
				load(operation.x, operation.y, operation.z);
				break;
			}
			case SAVE: {
				save(operation.x, operation.y, operation.z);
				break;
			}
			default:
				break;
			}
		}

		if (Time.thisTime - time0 > 1000) {
			for (Vector3i v : dimension.chunks.keySet()) {
				int x = v.getX() << 4;
				int y = v.getY() << 4;
				int z = v.getZ() << 4;

				boolean xOutside = x > playerPos.x + (WorldRenderer.DRAW_DISTANCE << 4) + 100
						|| x < playerPos.x - (WorldRenderer.DRAW_DISTANCE << 4) - 100;
				boolean yOutside = y > playerPos.y + (WorldRenderer.DRAW_DISTANCE << 4) + 100
						|| y < playerPos.y - (WorldRenderer.DRAW_DISTANCE << 4) - 100;
				boolean zOutside = z > playerPos.z + (WorldRenderer.DRAW_DISTANCE << 4) + 100
						|| z < playerPos.z - (WorldRenderer.DRAW_DISTANCE << 4) - 100;

				if (xOutside || yOutside || zOutside) {
					requireSave(v.getX(), v.getY(), v.getZ());
				}

			}
			time0 = Time.thisTime;

		}

		int playerInChunkX = (int) playerPos.x >> 4;
		int playerInChunkY = (int) playerPos.y >> 4;
		int playerInChunkZ = (int) playerPos.z >> 4;

		for (int x = playerInChunkX - WorldRenderer.DRAW_DISTANCE; x < playerInChunkX
				+ WorldRenderer.DRAW_DISTANCE; x++) {
			for (int y = playerInChunkY - WorldRenderer.DRAW_DISTANCE; y < playerInChunkY
					+ WorldRenderer.DRAW_DISTANCE; y++) {
				for (int z = playerInChunkZ - WorldRenderer.DRAW_DISTANCE; z < playerInChunkZ
						+ WorldRenderer.DRAW_DISTANCE; z++) {
					if (!MatrixHelper.isChunkInFrustum(x, y, z))
						continue;
					Chunk chunk = dimension.getChunk(x, y, z);
					if (chunk == null) {
						requireLoad(x, y, z);

					}

				}
			}
		}

	}

	public void release() {
		this.level.close();

	}

	private static class Operation {
		private OperationType type;
		private int x, y, z;

		public Operation(OperationType type, int x, int y, int z) {
			this.type = type;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Operation other = (Operation) obj;
			if (type != other.type)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}

	}

	private static enum OperationType {
		LOAD, SAVE;
	}

}
