package xueli.craftgame.world;

import org.lwjgl.utils.vector.Vector3i;
import xueli.craftgame.client.renderer.world.WorldRenderer;
import xueli.craftgame.state.StateWorld;
import xueli.game.utils.ThreadTask;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.vector.Vector;

public class ChunkProvider extends ThreadTask {

	// private static Logger logger =
	// Logger.getLogger(ChunkProvider.class.getName());

	private Dimension dimension;

	private ChunkGenerator generator;

	private String regionPath = StateWorld.savePath + "/regions/";
	private DimensionLevel level;

	public ChunkProvider(Dimension dimension) {
		this.dimension = dimension;
		this.generator = new ChunkGenerator(dimension);
		this.level = new DimensionLevel(regionPath, dimension);

	}

	public void requireLoad(int x, int y, int z) {
		addTask(new RunnableLoad(x, y, z));

	}

	public void requireSave(int x, int y, int z) {
		addTask(new RunnableSave(x, y, z));

	}

	void load(int x, int y, int z) {
		new RunnableLoad(x, y, z).run();
	}

	void save(int x, int y, int z) {
		new RunnableSave(x, y, z).run();
	}

	private long time0 = System.currentTimeMillis();

	public void tick(Vector playerPos) {
		if (Time.thisTime - time0 > 200) {
			for (Vector3i v : dimension.chunks.keySet()) {
				int x = v.getX() << 4;
				int y = v.getY() << 4;
				int z = v.getZ() << 4;

				boolean xOutside = x > playerPos.x + (WorldRenderer.DRAW_DISTANCE << 4) + 50
						|| x < playerPos.x - (WorldRenderer.DRAW_DISTANCE << 4) - 50;
				boolean yOutside = y > playerPos.y + (WorldRenderer.DRAW_DISTANCE << 4) + 50
						|| y < playerPos.y - (WorldRenderer.DRAW_DISTANCE << 4) - 50;
				boolean zOutside = z > playerPos.z + (WorldRenderer.DRAW_DISTANCE << 4) + 50
						|| z < playerPos.z - (WorldRenderer.DRAW_DISTANCE << 4) - 50;

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
		stopThread();
		this.level.close();

	}

	private class RunnableLoad implements Runnable {
		int x, y, z;

		public RunnableLoad(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public void run() {
			// logger.info(MessageFormat.format("[World] Load: {0}, {1}, {2}", x, y, z));

			Vector3i v = new Vector3i(x, y, z);
			if (dimension.chunks.containsKey(v))
				return;

			if (level.checkChunkExist(x, y, z)) {
				dimension.chunks.put(new Vector3i(x, y, z), level.getChunk(x, y, z));
			} else {
				generator.genSuperFlat(x, y, z);
			}

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			RunnableLoad other = (RunnableLoad) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}

	}

	private class RunnableSave implements Runnable {
		int x, y, z;

		public RunnableSave(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public void run() {
			// logger.info(MessageFormat.format("[World] Save: {0}, {1}, {2}", x, y, z));

			Vector3i v = new Vector3i(x, y, z);
			Chunk c = dimension.chunks.get(v);
			if (c == null)
				return;
			level.writeChunk(c);
			c.getBuffer().postRelease();
			dimension.chunks.remove(new Vector3i(x, y, z));

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			RunnableSave other = (RunnableSave) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}

	}

}
