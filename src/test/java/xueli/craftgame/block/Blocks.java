package xueli.craftgame.block;

import java.io.File;

import xueli.game.utils.texture.TextureAtlas;
import xueli.game.utils.texture.TextureAtlasBuilder;
import xueli.utils.io.Files;
import xueli.utils.logger.MyLogger;

public class Blocks {

	private static final MyLogger LOGGER = new MyLogger();

	public static TextureAtlas blockTextureAtlas;

	static {
		String blockResourceFolder = Files.getResourcePackedInJar("assets/images/blocks/").getPath();
		blockTextureAtlas = TextureAtlas.generateAtlas(TextureAtlasBuilder.iterate(new File(blockResourceFolder)));

		LOGGER.info("Texture atlas created: " + blockTextureAtlas.getWidth() + "x" + blockTextureAtlas.getHeight());

	}

	public static BlockType BLOCK_STONE = new BlockType("cg:stone", "Stone");

	public static void initCall() {
	}

}
