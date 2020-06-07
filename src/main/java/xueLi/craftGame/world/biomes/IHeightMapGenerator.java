package xueLi.craftGame.world.biomes;

import xueLi.craftGame.world.World;

public interface IHeightMapGenerator {

	public int[][] getHeightMap(int x, int z, World world);

}
