package xueli.mcremake.registry;

import xueli.game2.registry.Registry;
import xueli.game2.registry.RegistryImplement;
import xueli.game2.registry.WritableRegistry;
import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.client.renderer.world.BlockRenderer;
import xueli.mcremake.core.block.BlockCollidable;
import xueli.mcremake.core.block.BlockListener;
import xueli.mcremake.core.block.BlockType;

public final class GameRegistry {

	public static Registry<BlockType> BUILTIN_BLOCK_REGISTRY;
	public static BlockType STONE;

	static {
		RegistryImplement<BlockType> writable = new RegistryImplement<>();
		STONE = registerBlockType(writable, new ResourceLocation("stone"), "Stone", BlockListener.NONE, new BlockRendererSolid(1, 0), new BlockCollidableSolid());
		BUILTIN_BLOCK_REGISTRY = writable.freeze();
	}

	private static BlockType registerBlockType(WritableRegistry<BlockType> registry, ResourceLocation identify, String name, BlockListener blockListener, BlockRenderer renderer, BlockCollidable collidable) {
		BlockType type = new BlockType(identify, name, blockListener, renderer, collidable);
		registry.register(identify, type);
		return type;
	}

	public static void callForClazzLoad() {}

}
