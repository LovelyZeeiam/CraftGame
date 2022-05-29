package xueli.craftgame.block;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.renderer.blocks.BlockIconGenerator;
import xueli.craftgame.renderer.blocks.BlockRenderableSolid;
import xueli.game.renderer.FrameBuffer;
import xueli.game.utils.texture.TextureAtlas;
import xueli.game.utils.texture.TextureAtlasBuilder;
import xueli.utils.clazz.ClazzUtils;
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

	// TODO: BlockRenderable
	public static BlockType BLOCK_STONE = new BlockType("cg:stone", "Stone")
			.setRenderable(new BlockRenderableSolid(blockTextureAtlas.getTextureHolder("stone")));
	public static BlockType BLOCK_BEDROCK = new BlockType("cg:bedrock", "Bedrock")
			.setRenderable(new BlockRenderableSolid(blockTextureAtlas.getTextureHolder("bedrock")));
	public static BlockType BLOCK_DIRT = new BlockType("cg:dirt", "Dirt")
			.setRenderable(new BlockRenderableSolid(blockTextureAtlas.getTextureHolder("dirt")));
	public static BlockType BLOCK_GRASS = new BlockType("cg:grass", "Grass Block")
			.setRenderable(new BlockRenderableSolid(blockTextureAtlas.getTextureHolder("grass_block_side"),
					blockTextureAtlas.getTextureHolder("grass_block_side"),
					blockTextureAtlas.getTextureHolder("grass_block_side"),
					blockTextureAtlas.getTextureHolder("grass_block_side"),
					blockTextureAtlas.getTextureHolder("grass_block_top"), blockTextureAtlas.getTextureHolder("dirt")))
	/*
	 * .setListener(new BlockListener() { public void onLookAt(int x, int y, int z,
	 * xueli.craftgame.client.LocalTicker ticker, xueli.craftgame.entity.Entity
	 * player) { System.out.println(x + ", " + y + ", " + z); }; })
	 */
	;

	public static HashMap<String, BlockType> blocks = new HashMap<>();
	public static HashMap<BlockType, FrameBuffer> blockReviews = new HashMap<>();

	static {
		registerBlocks();

	}

	private static void registerBlocks() {
		for (Field field : ClazzUtils.getAllFields(Blocks.class)) {
			if (!field.getType().isAssignableFrom(BlockType.class))
				continue;

			try {
				BlockType type = (BlockType) field.get(null);
				blocks.put(type.getNamespace(), type);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}

	}

	public static void initCallForRenderer(CraftGameContext ctx) {
		blocks.values().forEach(t -> {
			FrameBuffer frameBuffer = BlockIconGenerator.generate(t, ctx);
			blockReviews.put(t, frameBuffer);
		});

	}

}
