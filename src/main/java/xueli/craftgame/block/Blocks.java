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

	private static TextureResourceManager textureResourceManager = null;

	static {

	}

	public static BlockType BLOCK_STONE = new BlockType("cg:stone", "Stone")
			.setRenderable(new BlockRenderableSolid("stone"));
	public static BlockType BLOCK_BEDROCK = new BlockType("cg:bedrock", "Bedrock")
			.setRenderable(new BlockRenderableSolid("bedrock"));
	public static BlockType BLOCK_DIRT = new BlockType("cg:dirt", "Dirt")
			.setRenderable(new BlockRenderableSolid("dirt"));
	public static BlockType BLOCK_GRASS = new BlockType("cg:grass", "Grass Block")
			.setRenderable(new BlockRenderableSolid("grass_block_side", "grass_block_side", "grass_block_side", "grass_block_side", "grass_block_top", "dirt"));

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
		blocks.values().forEach(t -> {
//			t.getRenderable().init(ctx.getWorldRenderer());
			FrameBuffer frameBuffer = BlockIconGenerator.generate(t, ctx);
			blockReviews.put(t, frameBuffer);
		});
	}

}
