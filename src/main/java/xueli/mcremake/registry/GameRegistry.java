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
import xueli.mcremake.core.world.biome.BiomeType;
import xueli.mcremake.registry.block.BlockCollidableSolid;
import xueli.mcremake.registry.block.BlockRendererSideTopBottom;
import xueli.mcremake.registry.block.BlockRendererSolid;
import xueli.mcremake.registry.item.ItemListenerGenericBlock;
import xueli.mcremake.registry.item.ItemRendererRegularBlock;

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
	
	public static final Registry<BiomeType> BUILTIN_BIOME_REGISTRY;
	public static final BiomeType BIOME_FOREST;
	public static final BiomeType BIOME_SEASONAL_FOREST;
	public static final BiomeType BIOME_RAIN_FOREST;
	public static final BiomeType BIOME_TAIGA_FOREST;
	public static final BiomeType BIOME_SWAMPLAND;
	public static final BiomeType BIOME_SAVANNA;
	public static final BiomeType BIOME_SHRUBLAND;
	public static final BiomeType BIOME_DESERT;
	public static final BiomeType BIOME_ICE_DESERT;
	public static final BiomeType BIOME_PLAINS;
	public static final BiomeType BIOME_TUNDRA;
	
	static {
		RegistryImplement<BiomeType> writable = new RegistryImplement<>();
		BIOME_RAIN_FOREST = registerBiomeType(writable, new ResourceLocation("rainforest"), "Rainforest");
		BIOME_SWAMPLAND = registerBiomeType(writable, new ResourceLocation("swampland"), "Swampland");
		BIOME_SEASONAL_FOREST = registerBiomeType(writable, new ResourceLocation("seasonal_forest"), "Seasonal Forest"); // Yes, inside the game there is a biome called Seasonal Forest
		BIOME_FOREST = registerBiomeType(writable, new ResourceLocation("forest"), "Forest");
		BIOME_ICE_DESERT = registerBiomeType(writable, new ResourceLocation("ice_desert"), "Ice Desert"); // But inside the game it seems to extend the FlatBiome class and it really like Builder in Java
		BIOME_SAVANNA = registerBiomeType(writable, new ResourceLocation("savanna"), "Savanna");
		BIOME_SHRUBLAND = registerBiomeType(writable, new ResourceLocation("shrubland"), "Shrubland");
		BIOME_TAIGA_FOREST = registerBiomeType(writable, new ResourceLocation("taiga"), "Taiga");
		BIOME_DESERT = registerBiomeType(writable, new ResourceLocation("desert"), "Desert");
		BIOME_PLAINS = registerBiomeType(writable, new ResourceLocation("plains"), "Plains");
		BIOME_TUNDRA = registerBiomeType(writable, new ResourceLocation("tundra"), "Tundra");
		
		BUILTIN_BIOME_REGISTRY = writable.freeze();
	}
	
	public static BlockType registerBlockType(WritableRegistry<BlockType> registry, ResourceLocation identify, String name, BlockListener blockListener, BlockVertexGatherer renderer, BlockCollidable collidable, ResourceLocation... tags) {
		BlockType type = new BlockType(identify, name, blockListener, renderer, collidable);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}
	
//	private static ItemType registerItemFromBlock(WritableRegistry<ItemType> registry, BlockType)
	
	public static ItemType registerItemType(WritableRegistry<ItemType> registry, ResourceLocation identify, String name, ItemListener listener, ItemVertexGatherer renderer, ResourceLocation... tags) {
		ItemType type = new ItemType(identify, name, listener, renderer);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}
	
	private static BiomeType registerBiomeType(WritableRegistry<BiomeType> registry, ResourceLocation identify, String name, ResourceLocation... tags) {
		BiomeType type = new BiomeType(identify, name);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}

	public static void callForClazzLoad() {}

}
