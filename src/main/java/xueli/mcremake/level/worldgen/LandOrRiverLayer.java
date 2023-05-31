package xueli.mcremake.level.worldgen;

import java.util.HashMap;
import java.util.Random;

import org.lwjgl.utils.vector.Vector2i;

import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.registry.GameRegistry;
import xueli.utils.collection.CollectionHelper;

public class LandOrRiverLayer {

	private static final int LAYER_MAP_INITIAL_SIZE = 8;
	private static final int SCALE_TIMES = 5;
	private static final int CHUNK_POS_TO_LAYER_OFFSET = 4;
	private static final int LAYER_TO_CHUNK_COUNT = 16;

	private final long seed;

	private final HashMap<Vector2i, int[][]> layerMap = new HashMap<>();

	public LandOrRiverLayer(long seed) {
		this.seed = seed;

	}

	public void fillLandOrRiver(int x, int z, Chunk chunk) {
		int[][] chunkMap = getLayerMap(x, z);
//		System.out.println(CollectionHelper.toString(chunkMap));

		for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
			for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
				chunk.setBlockImmediate(i, 70, j,
						chunkMap[i][j] == 0 ? GameRegistry.BLOCK_STONE : GameRegistry.BLOCK_DIRT);
			}
		}

	}

	private int[][] getLayerMap(int x, int z) {
		synchronized (this.layerMap) {
			int[][] map = layerMap.get(new Vector2i(x, z));
			if (map != null)
				return map;
			this.genLayerMap(x >> CHUNK_POS_TO_LAYER_OFFSET, z >> CHUNK_POS_TO_LAYER_OFFSET);
			return layerMap.get(new Vector2i(x, z));
		}
	}

	private void genLayerMap(int offsetX, int offsetZ) {
		Random random = new Random(this.seed);

		int size = LAYER_MAP_INITIAL_SIZE;
		int[][] layerMap = new int[size][size];
		// First: Spread random spot
		for (int x = 0; x < LAYER_MAP_INITIAL_SIZE; x++) {
			for (int y = 0; y < LAYER_MAP_INITIAL_SIZE; y++) {
				layerMap[x][y] = random.nextInt(10) * 6 / 10;
			}
		}

		// Second: Scale for times
		for (int i = 0; i < SCALE_TIMES; i++) {
			layerMap = scaleAndMerge(layerMap, size, size);
			size *= 2;
		}
//		this.merge(layerMap, size, size);
//		this.merge(layerMap, size, size);

		System.out.println(CollectionHelper.toString(layerMap));

		// Last: copy data and store
		// I know there are memory costs but I will fix it la-la-la-later!
		for (int x = 0; x < LAYER_TO_CHUNK_COUNT; x++) {
			for (int z = 0; z < LAYER_TO_CHUNK_COUNT; z++) {
				int[][] chunkMap = new int[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
				for (int x1 = 0; x1 < Chunk.CHUNK_SIZE; x1++) {
					for (int z1 = 0; z1 < Chunk.CHUNK_SIZE; z1++) {
						chunkMap[x1][z1] = layerMap[x * LAYER_TO_CHUNK_COUNT + x1][z * LAYER_TO_CHUNK_COUNT + z1];
//						System.out.println((x * LAYER_TO_CHUNK_COUNT + x1) + ", " + (z * LAYER_TO_CHUNK_COUNT + z1));

					}
//					System.out.println(x * LAYER_TO_CHUNK_COUNT + x1);
				}
				this.layerMap.put(new Vector2i((offsetX << CHUNK_POS_TO_LAYER_OFFSET) + x,
						(offsetZ << CHUNK_POS_TO_LAYER_OFFSET) + z), chunkMap);
//				System.out.println(new Vector2i((offsetX << CHUNK_POS_TO_LAYER_OFFSET) + x, (offsetZ << CHUNK_POS_TO_LAYER_OFFSET) + z));
			}
		}
//		System.out.println("====");

	}

	public static int[][] scaleAndMerge(int[][] original, int xSize, int ySize) {
		int newXSize = xSize << 1;
		int newYSize = ySize << 1;

		int[][] dest = new int[newXSize][newYSize];
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				dest[x * 2][y * 2] = original[x][y];
				dest[x * 2 + 1][y * 2] = original[x][y];
				dest[x * 2][y * 2 + 1] = original[x][y];
				dest[x * 2 + 1][y * 2 + 1] = original[x][y];
//				System.out.print(original[x][y] + " ");
			}
//			System.out.println();
		}
//		System.out.println("==");
		merge(dest, newXSize, newYSize);
//		System.out.println(CollectionHelper.toString(dest));
		return dest;
	}

	public static void merge(int[][] dest, int xSize, int ySize) {
		int[][] scores = new int[xSize][ySize];
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				scores[x][y] = calcScore(dest, xSize, ySize, x, y);
			}
		}

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				if (scores[x][y] > 4)
					dest[x][y] = 1;
				if (scores[x][y] < 4)
					dest[x][y] = 0;
			}
		}

	}

	public static int calcScore(int[][] dest, int xSize, int ySize, int x, int y) {
		int score = 0;
		if (x != 0) {
			score += dest[x - 1][y];
			if (y != 0)
				score += dest[x - 1][y - 1];
			if (y != ySize - 1)
				score += dest[x - 1][y + 1];
		}
		if (x != xSize - 1) {
			score += dest[x + 1][y];
			if (y != 0)
				score += dest[x + 1][y - 1];
			if (y != ySize - 1)
				score += dest[x + 1][y + 1];
		}
		if (y != 0)
			score += dest[x][y - 1];
		if (y != ySize - 1)
			score += dest[x][y + 1];

		return score;
	}

	public static int findValue(int[][] dest, int xSize, int ySize, int x, int y, int val) {
		int score = 0;
		if (x != 0) {
			score += dest[x - 1][y] == val ? 1 : 0;
			if (y != 0)
				score += dest[x - 1][y - 1] == val ? 1 : 0;
			if (y != ySize - 1)
				score += dest[x - 1][y + 1] == val ? 1 : 0;
		}
		if (x != xSize - 1) {
			score += dest[x + 1][y] == val ? 1 : 0;
			if (y != 0)
				score += dest[x + 1][y - 1] == val ? 1 : 0;
			if (y != ySize - 1)
				score += dest[x + 1][y + 1] == val ? 1 : 0;
		}
		if (y != 0)
			score += dest[x][y - 1] == val ? 1 : 0;
		if (y != ySize - 1)
			score += dest[x][y + 1] == val ? 1 : 0;

		return score;
	}

}
