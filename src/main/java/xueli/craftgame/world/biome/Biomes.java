package xueli.craftgame.world.biome;

import xueli.craftgame.world.generate.GeneratorForest;
import xueli.craftgame.world.generate.GeneratorRiver;

public class Biomes {

	public static void init() {
		BiomeResource.biomes.get("craftgame:forest").setGenerator(new GeneratorForest());
		BiomeResource.biomes.get("craftgame:river").setGenerator(new GeneratorRiver());

	}

}
