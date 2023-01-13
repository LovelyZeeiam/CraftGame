package xueli.mcremake.registry;

import xueli.game2.registry.Registry;
import xueli.game2.registry.RegistryImplement;
import xueli.game2.registry.WritableRegistry;
import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.client.renderer.item.ItemVertexGatherer;
import xueli.mcremake.client.renderer.world.BlockVertexGatherer;
import xueli.mcremake.core.block.BlockCollidable;
import xueli.mcremake.core.block.BlockListener;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.item.ItemListener;
import xueli.mcremake.core.item.ItemType;

public final class GameRegistry {

	public static Registry<BlockType> BUILTIN_BLOCK_REGISTRY;
	public static BlockType BLOCK_STONE;
	public static BlockType BLOCK_DIRT;
	public static BlockType BLOCK_GRASS;
	
	static {
		RegistryImplement<BlockType> writable = new RegistryImplement<>();
		BLOCK_STONE = registerBlockType(writable, new ResourceLocation("stone"), "Stone", BlockListener.NONE, new BlockRendererSolid(1, 0), new BlockCollidableSolid());
		BLOCK_DIRT = registerBlockType(writable, new ResourceLocation("dirt"), "dirt", BlockListener.NONE, new BlockRendererSolid(2, 0), new BlockCollidableSolid());
		BLOCK_GRASS = registerBlockType(writable, new ResourceLocation("grass_block"), "Grass Block", BlockListener.NONE, new BlockRendererSideTopBottom(3, 0, 1, 7, 2, 0), new BlockCollidableSolid());
		
		BUILTIN_BLOCK_REGISTRY = writable.freeze();
	}
	
	public static Registry<ItemType> BUILTIN_ITEM_REGISTRY;
	public static ItemType ITEM_BLOCK_STONE;
	public static ItemType ITEM_BLOCK_DIRT;
	public static ItemType ITEM_BLOCK_GRASS;
	
	static {
		RegistryImplement<ItemType> writable = new RegistryImplement<>();
		ITEM_BLOCK_STONE = registerItemType(writable, new ResourceLocation("stone"), "Stone", ItemListener.NONE, new ItemRendererRegularBlock(BLOCK_STONE));
		ITEM_BLOCK_DIRT = registerItemType(writable, new ResourceLocation("dirt"), "Dirt", ItemListener.NONE, new ItemRendererRegularBlock(BLOCK_DIRT));
		ITEM_BLOCK_GRASS = registerItemType(writable, new ResourceLocation("grass_block"), "Grass Block", ItemListener.NONE, new ItemRendererRegularBlock(BLOCK_GRASS));
		
		BUILTIN_ITEM_REGISTRY = writable.freeze();
	}
	
	private static BlockType registerBlockType(WritableRegistry<BlockType> registry, ResourceLocation identify, String name, BlockListener blockListener, BlockVertexGatherer renderer, BlockCollidable collidable) {
		BlockType type = new BlockType(identify, name, blockListener, renderer, collidable);
		registry.register(identify, type);
		return type;
	}
	
	private static ItemType registerItemType(WritableRegistry<ItemType> registry, ResourceLocation namespace, String name, ItemListener listener, ItemVertexGatherer renderer) {
		ItemType type = new ItemType(namespace, name, listener, renderer);
		registry.register(namespace, type);
		return type;
	}

	public static void callForClazzLoad() {}

}
