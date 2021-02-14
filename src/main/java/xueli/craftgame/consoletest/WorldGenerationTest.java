package xueli.craftgame.consoletest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import xueli.craftgame.block.BlockResource;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.craftgame.world.biome.BiomeResource;
import xueli.gamengine.resource.LangManager;
import xueli.gamengine.utils.store.Color;
import xueli.utils.Logger;

public class WorldGenerationTest implements Runnable {

	private LangManager lang;

	@Override
	public void run() {
		Logger.info("World generation test.");

		lang = new LangManager("res/");
		lang.loadLang();
		lang.setLang("zh-ch.lang");

		BlockResource blockRes = new BlockResource("res/", lang);
		blockRes.load();

		BiomeResource biomeRes = new BiomeResource("res/", lang);
		biomeRes.load();

		World world = new World();
		world.requireGenChunkSync(0, 0);
		Chunk chunk = world.getChunk(0, 0);
		chunk.generateMap();
		Color[][] map = chunk.getColorMap();

		BufferedImage image = new BufferedImage(Chunk.size, Chunk.size, BufferedImage.TYPE_3BYTE_BGR);
		for (int u = 0; u < Chunk.size; u++) {
			for (int v = 0; v < Chunk.size; v++) {
				byte[] color = map[u][v].getColor();
				image.setRGB(v, u, color[2] << 16 | color[1] << 8 | color[0]);
			}
		}

		try {
			ImageIO.write(image, "png", new File("map.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		world.close();

	}

}
