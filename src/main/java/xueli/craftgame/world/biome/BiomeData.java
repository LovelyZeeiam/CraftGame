package xueli.craftgame.world.biome;

import org.lwjgl.nanovg.NVGColor;
import xueli.craftgame.world.generate.IChunkGenerator;

import java.util.Arrays;
import java.util.Random;

public class BiomeData {

    String name;

    private int[] height;
    private float noise_stretch;
    private String[] blocks = new String[4];
    byte[] mapcolor = new byte[3];

    NVGColor mapcolor_nvg = NVGColor.create();
    private IChunkGenerator generator;

    public int[] getHeight() {
        return height;
    }

    public float getNoise_stretch() {
        return noise_stretch;
    }

    public NVGColor getMapcolor_nvg() {
        return mapcolor_nvg;
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

    @Override
    public String toString() {
        return "BiomeData{" +
                "name='" + name + '\'' +
                ", height=" + Arrays.toString(height) +
                ", noise_stretch=" + noise_stretch +
                ", mapcolor=" + Arrays.toString(mapcolor) +
                '}';
    }

}
