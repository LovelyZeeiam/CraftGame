package xueLi.craftGame.world.biomes;

import xueLi.craftGame.utils.NoiseGenerator;
import xueLi.craftGame.world.Chunk;
import xueLi.craftGame.world.World;

public class BiomePlane extends Biome {

	private NoiseGenerator generator;

	public BiomePlane() {
		super("Plane");
		generator = new NoiseGenerator();

	}

	@Override
	public int[][] getHeightMap(int x, int z, World world) {
		return generator.generate(x * 16, z * 16, Chunk.size, Chunk.size);
	}

}
