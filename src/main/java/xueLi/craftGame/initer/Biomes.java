package xueLi.craftGame.initer;

import static xueLi.craftGame.world.biomes.Biome.biomesHashMap;

import xueLi.craftGame.world.biomes.*;

public class Biomes {

	public static void init() {
		biomesHashMap.put(0, new BiomePlane());

	}

}
