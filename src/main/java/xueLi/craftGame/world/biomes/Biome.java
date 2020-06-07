package xueLi.craftGame.world.biomes;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector4f;

public abstract class Biome implements IHeightMapGenerator {

	public static HashMap<Integer, Biome> biomesHashMap = new HashMap<Integer, Biome>();

	public String name;
	public Vector4f skyColor;
	public boolean isSkyColorChangeWithWeatherOrTime;

	public Biome(String name) {
		this.name = name;
		this.isSkyColorChangeWithWeatherOrTime = true;

	}

	public Biome(String name, Vector4f skyColor, boolean isSkyColorChangeWithWeatherOrTime) {
		this.name = name;
		this.skyColor = skyColor;
		this.isSkyColorChangeWithWeatherOrTime = isSkyColorChangeWithWeatherOrTime;

	}

}
