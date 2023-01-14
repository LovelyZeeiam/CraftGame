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
	
	// Include this tag to make block item use default renderer
	public static final ResourceLocation TAG_GENERIC_BLOCK = new ResourceLocation("generic_blocks");
	
	public static final Registry<BlockType> BUILTIN_BLOCK_REGISTRY;
	public static final BlockType BLOCK_STONE;
	public static final BlockType BLOCK_DIRT;
	public static final BlockType BLOCK_GRASS;
	
	static {
		RegistryImplement<BlockType> writable = new RegistryImplement<>();
		BLOCK_STONE = registerBlockType(writable, new ResourceLocation("stone"), "Stone", BlockListener.NONE, new BlockRendererSolid(1, 0), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_DIRT = registerBlockType(writable, new ResourceLocation("dirt"), "dirt", BlockListener.NONE, new BlockRendererSolid(2, 0), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_GRASS = registerBlockType(writable, new ResourceLocation("grass_block"), "Grass Block", BlockListener.NONE, new BlockRendererSideTopBottom(3, 0, 1, 7, 2, 0), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		
		BUILTIN_BLOCK_REGISTRY = writable.freeze();
	}
	
	public static final Registry<ItemType> BUILTIN_ITEM_REGISTRY;
	public static final ItemType ITEM_BLOCK_STONE;
	public static final ItemType ITEM_BLOCK_DIRT;
	public static final ItemType ITEM_BLOCK_GRASS;
	
	static {
		RegistryImplement<ItemType> writable = new RegistryImplement<>();
		ITEM_BLOCK_STONE = registerItemType(writable, new ResourceLocation("stone"), "Stone", new ItemListenerGenericBlock(BLOCK_STONE), new ItemRendererRegularBlock(BLOCK_STONE));
		ITEM_BLOCK_DIRT = registerItemType(writable, new ResourceLocation("dirt"), "Dirt", new ItemListenerGenericBlock(BLOCK_DIRT), new ItemRendererRegularBlock(BLOCK_DIRT));
		ITEM_BLOCK_GRASS = registerItemType(writable, new ResourceLocation("grass_block"), "Grass Block", new ItemListenerGenericBlock(BLOCK_GRASS), new ItemRendererRegularBlock(BLOCK_GRASS));
		
		BUILTIN_ITEM_REGISTRY = writable.freeze();
	}
	
	private static BlockType registerBlockType(WritableRegistry<BlockType> registry, ResourceLocation identify, String name, BlockListener blockListener, BlockVertexGatherer renderer, BlockCollidable collidable, ResourceLocation... tags) {
		BlockType type = new BlockType(identify, name, blockListener, renderer, collidable);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}
	
//	private static ItemType registerItemFromBlock(WritableRegistry<ItemType> registry, BlockType)
	
	private static ItemType registerItemType(WritableRegistry<ItemType> registry, ResourceLocation identify, String name, ItemListener listener, ItemVertexGatherer renderer, ResourceLocation... tags) {
		ItemType type = new ItemType(identify, name, listener, renderer);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}

	public static void callForClazzLoad() {}

}
