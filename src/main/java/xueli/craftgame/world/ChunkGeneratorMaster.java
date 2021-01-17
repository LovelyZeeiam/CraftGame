package xueli.craftgame.world;

import xueli.craftgame.block.Tile;
import xueli.craftgame.world.biome.BiomeData;
import xueli.craftgame.world.biome.BiomeResource;
import xueli.craftgame.world.biome.Biomes;
import xueli.craftgame.world.generate.SimplexNoise;
import xueli.gamengine.utils.MathUtils;

import java.util.HashMap;
import java.util.Random;

public class ChunkGeneratorMaster {

	private World world;

	private int seed;

	public ChunkGeneratorMaster(World world) {
		this.world = world;
		seed = new Random().nextInt();

	}

	public Chunk superflat(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkZ, world, BiomeResource.biomes.get("craftgame:plain"));
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				for (int y = 0; y < 4; y++) {
					Tile block = new Tile("stone");
					// block.pos = new BlockPos(x, y, z);
					chunk.blockState[x][y][z] = block;

				}

				Tile block = new Tile("grass_block");
				// block.pos = new BlockPos(x, 4, z);
				chunk.blockState[x][4][z] = block;

				chunk.heightMap[x][z] = 4;
			}
		}
		return chunk;
	}

	private HashMap<Long, BiomeData> chunkBiome = new HashMap<>();
	private HashMap<Long, Integer> heightMaps = new HashMap<>();

	private static final int SEA_LEVEL = 64;

	private float generateChunkCornerHeight(int chunkX, int chunkZ) {
		if(heightMaps.containsKey(MathUtils.vert2ToLong(chunkX, chunkZ)))
			return heightMaps.get(MathUtils.vert2ToLong(chunkX, chunkZ));
		double perlinNoise = (SimplexNoise.noise((chunkX * 23521.0) % seed, (chunkZ * 1232521.0) % seed) + 1.0) * 0.5;
		int allCount = BiomeResource.biomes.size();
		int chosenId = (int) (allCount * perlinNoise);
		if(chosenId >= allCount) chosenId = allCount - 1;

		BiomeData[] datas = new BiomeData[allCount];
		BiomeResource.biomes.values().toArray(datas);
		BiomeData data = datas[chosenId];

		int randomHeight = data.getRandomHeight();

		heightMaps.put(MathUtils.vert2ToLong(chunkX, chunkZ), randomHeight);
		chunkBiome.put(MathUtils.vert2ToLong(chunkX, chunkZ), data);

		return randomHeight;
	}

	public Chunk normal(int chunkX, int chunkZ) {
		// 生成区块的最小的一个角的高度
		float y1 = generateChunkCornerHeight(chunkX, chunkZ);
		float y2 = generateChunkCornerHeight(chunkX + 1,chunkZ);
		float y3 = generateChunkCornerHeight(chunkX, chunkZ + 1);
		float y4 = generateChunkCornerHeight(chunkX + 1, chunkZ + 1);

		BiomeData biome = chunkBiome.get(MathUtils.vert2ToLong(chunkX, chunkZ));

		// 初始化区块
		Chunk chunk = new Chunk(chunkX, chunkZ, world, biome);

		// 放置方块
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				float v1 = MathUtils.interpolate(y1, y2, (float) x / Chunk.size);
				float v2 = MathUtils.interpolate(y3, y4, (float) x / Chunk.size);

				int y_max = (int) MathUtils.interpolate(v1, v2, (float) z / Chunk.size);

				int bedrock_height = (int) (Math.random()* 6 + 2);
				int dirt_height = (int) (Math.random() * 4 + 2);

				for(int y = 0;y < bedrock_height; y++) {
					Tile block = new Tile(biome.getBlocks()[3]);
					chunk.blockState[x][y][z] = block;

				}

				for (int y = bedrock_height; y < y_max - dirt_height; y++) {
					Tile block = new Tile(biome.getBlocks()[2]);
					chunk.blockState[x][y][z] = block;

				}

				for(int y = y_max - dirt_height;y < y_max;y++){
					Tile block = new Tile(biome.getBlocks()[1]);
					chunk.blockState[x][y][z] = block;

				}

				Tile block = new Tile(biome.getBlocks()[0]);
				chunk.blockState[x][y_max][z] = block;

				chunk.heightMap[x][z] = y_max;

				while (chunk.heightMap[x][z] < SEA_LEVEL) {
					chunk.setBlock(x,chunk.heightMap[x][z]+1,z,new Tile("craftgame:water"));
				}

			}
		}

		// 区块生成之后
		if(biome.getGenerator() != null)
			biome.getGenerator().postGenerate(world, chunk);

		return chunk;
	}

}
