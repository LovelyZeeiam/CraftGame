package xueli.craftgame.world.biome;

import org.lwjgl.nanovg.NVGColor;
import xueli.craftgame.world.generate.IChunkGenerator;
import xueli.gamengine.utils.Color;

import java.util.Arrays;
import java.util.Random;

public class BiomeData {

	String namespace;
	String name;

	private int[] height;
	private float noise_stretch;
	private String[] blocks = new String[4];
	byte[] map_color = new byte[3];
	byte[] water_color = new byte[3];
	byte[] leaves_color = new byte[3];
	
	Color map_color_wrapper, water_color_wrapper, leaves_color_wrapper;
	
	private IChunkGenerator generator;

	public int[] getHeight() {
		return height;
	}

	public float getNoise_stretch() {
		return noise_stretch;
	}

	public NVGColor getMap_color_nvg() {
		return map_color_wrapper.getNVGColor();
	}

	public void setGenerator(IChunkGenerator generator) {
		this.generator = generator;
	}

	public IChunkGenerator getGenerator() {
		return generator;
	}

	public String[] getBlocks() {
		return blocks;
	}

	public int getRandomHeight() {
		return (int) (new Random().nextFloat() * (height[1] - height[0]) + height[0]);
	}

	public NVGColor getWater_color_nvg() {
		return water_color_wrapper.getNVGColor();
	}

	public NVGColor getLeaves_color_nvg() {
		return leaves_color_wrapper.getNVGColor();
	}

	public String getNamespace() {
		return namespace;
	}

	@Override
	public String toString() {
		return "BiomeData{" + "name='" + name + '\'' + ", height=" + Arrays.toString(height) + ", noise_stretch="
				+ noise_stretch + ", mapcolor=" + Arrays.toString(map_color) + '}';
	}

}
