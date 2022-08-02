package xueli.craftgame.block;

import java.lang.reflect.Field;
import java.util.HashMap;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.renderer.blocks.BlockIconGenerator;
import xueli.craftgame.renderer.blocks.BlockRenderableSolid;
import xueli.game.renderer.FrameBuffer;
import xueli.game.resource.ResourceMaster;
import xueli.game.resource.TextureResourceManager;
import xueli.game.resource.texture.TextureAtlas;
import xueli.game.resource.texture.TextureAtlasBuilder;
import xueli.utils.clazz.ClazzUtils;
import xueli.utils.logger.MyLogger;

public class Blocks {

	public static final String BLOCK_ATLAS_NAMESPACE = "cg:blocks";

	private static final MyLogger LOGGER = new MyLogger();

	private static TextureResourceManager textureResourceManager;
	public static TextureAtlas blockTextureAtlas;

	static {
		CraftGameContext ctx = CraftGameContext.ctx;
		if (ctx.isClient()) {
			ResourceMaster resourceMaster = ctx.getResourceMaster();
			textureResourceManager = resourceMaster.getTextureResourceManager();
			textureResourceManager.build(BLOCK_ATLAS_NAMESPACE,
					TextureAtlasBuilder.iterateFiles("assets/images/blocks/", resourceMaster.getProvider(), true));
		}

	}

	public static BlockType BLOCK_STONE = new BlockType("cg:stone", "Stone")
			.setRenderable(new BlockRenderableSolid(textureResourceManager.addToken("stone")));
	public static BlockType BLOCK_BEDROCK = new BlockType("cg:bedrock", "Bedrock")
			.setRenderable(new BlockRenderableSolid(textureResourceManager.addToken("bedrock")));
	public static BlockType BLOCK_DIRT = new BlockType("cg:dirt", "Dirt")
			.setRenderable(new BlockRenderableSolid(textureResourceManager.addToken("dirt")));
	public static BlockType BLOCK_GRASS = new BlockType("cg:grass", "Grass Block")
			.setRenderable(new BlockRenderableSolid(textureResourceManager.addToken("grass_block_side"),
					textureResourceManager.addToken("grass_block_side"),
					textureResourceManager.addToken("grass_block_side"),
					textureResourceManager.addToken("grass_block_side"),
					textureResourceManager.addToken("grass_block_top"), textureResourceManager.addToken("dirt")));
	/*
	 * .setListener(new BlockListener() { public void onLookAt(int x, int y, int z,
	 * xueli.craftgame.client.LocalTicker ticker, xueli.craftgame.entity.Entity
	 * player) { System.out.println(x + ", " + y + ", " + z); }; })
	 */

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

	public static void clazzInitCall() {
	}

	public static void initCallForRenderer(CraftGameContext ctx) {
		blockTextureAtlas = textureResourceManager.getAtlas(BLOCK_ATLAS_NAMESPACE);
		blocks.values().forEach(t -> {
			FrameBuffer frameBuffer = BlockIconGenerator.generate(t, ctx);
			blockReviews.put(t, frameBuffer);
		});

	}

}
