package xueli.mcremake.registry;

import xueli.game2.registry.Registry;
import xueli.game2.registry.RegistryImplement;
import xueli.game2.registry.WritableRegistry;
import xueli.game2.resource.ResourceIdentifier;
import xueli.mcremake.client.renderer.item.ItemVertexGatherer;
import xueli.mcremake.client.renderer.world.block.BlockRendererLiquid;
import xueli.mcremake.client.renderer.world.block.BlockRendererSideTopBottom;
import xueli.mcremake.client.renderer.world.block.BlockRendererSolid;
import xueli.mcremake.client.renderer.world.block.BlockVertexGatherer;
import xueli.mcremake.core.block.BlockCollidable;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.item.ItemType;
import xueli.mcremake.core.world.biome.BiomeType;
import xueli.mcremake.registry.block.BlockCollidableSolid;
import xueli.mcremake.registry.item.ItemRendererRegularBlock;

public final class GameRegistry {
	
	// Include this tag to make block item use default renderer
	public static final ResourceIdentifier TAG_GENERIC_BLOCK = new ResourceIdentifier("generic_blocks");
	public static final ResourceIdentifier TAG_GENERIC_LIQUID = new ResourceIdentifier("generic_liquid");

	public static final Registry<BlockType> BUILTIN_BLOCK_REGISTRY;
	public static final BlockType BLOCK_STONE;
	public static final BlockType BLOCK_DIRT;
	public static final BlockType BLOCK_GRASS;
	public static final BlockType BLOCK_BEDROCK;
	public static final BlockType BLOCK_SAND;
	public static final BlockType BLOCK_GRAVEL;
	public static final BlockType BLOCK_WATER;
//	public static final BlockType BLOCK_SAND_STONE;
	
	static {
		RegistryImplement<BlockType> writable = new RegistryImplement<>();
		BLOCK_STONE = registerBlockType(writable, new ResourceIdentifier("stone"), "Stone", new BlockRendererSolid(1, 0), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_DIRT = registerBlockType(writable, new ResourceIdentifier("dirt"), "dirt", new BlockRendererSolid(2, 0), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_GRASS = registerBlockType(writable, new ResourceIdentifier("grass_block"), "Grass Block", new BlockRendererSideTopBottom(3, 0, 1, 7, 2, 0), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_BEDROCK = registerBlockType(writable, new ResourceIdentifier("bedrock"), "bedrock", new BlockRendererSolid(1, 1), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_SAND = registerBlockType(writable, new ResourceIdentifier("sand"), "sand", new BlockRendererSolid(2, 1), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_GRAVEL = registerBlockType(writable, new ResourceIdentifier("gravel"), "gravel", new BlockRendererSolid(3, 1), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		BLOCK_WATER = registerBlockType(writable, new ResourceIdentifier("water"), "water", new BlockRendererLiquid(15, 12), BlockCollidable.NONE, TAG_GENERIC_LIQUID);
		//		BLOCK_SAND_STONE = registerBlockType(writable, new ResourceIdentifier("sandstone"), "sandstone", BlockListener.NONE, new BlockRendererSolid(1, 1), new BlockCollidableSolid(), TAG_GENERIC_BLOCK);
		
		BUILTIN_BLOCK_REGISTRY = writable.freeze();
	}
	
	public static final Registry<ItemType> BUILTIN_ITEM_REGISTRY;
	public static final ItemType ITEM_BLOCK_STONE;
	public static final ItemType ITEM_BLOCK_DIRT;
	public static final ItemType ITEM_BLOCK_GRASS;
	
	static {
		RegistryImplement<ItemType> writable = new RegistryImplement<>();
		ITEM_BLOCK_STONE = registerItemType(writable, new ResourceIdentifier("stone"), "Stone", new ItemRendererRegularBlock(BLOCK_STONE));
		ITEM_BLOCK_DIRT = registerItemType(writable, new ResourceIdentifier("dirt"), "Dirt", new ItemRendererRegularBlock(BLOCK_DIRT));
		ITEM_BLOCK_GRASS = registerItemType(writable, new ResourceIdentifier("grass_block"), "Grass Block", new ItemRendererRegularBlock(BLOCK_GRASS));
		
		BUILTIN_ITEM_REGISTRY = writable.freeze();
	}
	
	public static final Registry<ResourceIdentifier> BUILTIN_ITEM_BLOCK_MAP_REGISTRY;
	
	static {
		RegistryImplement<ResourceIdentifier> writable = new RegistryImplement<>();
		writable.register(ITEM_BLOCK_STONE.namespace(), BLOCK_STONE.namespace());
		writable.register(ITEM_BLOCK_DIRT.namespace(), BLOCK_DIRT.namespace());
		writable.register(ITEM_BLOCK_GRASS.namespace(), BLOCK_GRASS.namespace());
		
		BUILTIN_ITEM_BLOCK_MAP_REGISTRY = writable.freeze();
		
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
		BIOME_RAIN_FOREST = registerBiomeType(writable, new ResourceIdentifier("rainforest"), "Rainforest");
		BIOME_SWAMPLAND = registerBiomeType(writable, new ResourceIdentifier("swampland"), "Swampland");
		BIOME_SEASONAL_FOREST = registerBiomeType(writable, new ResourceIdentifier("seasonal_forest"), "Seasonal Forest"); // Yes, inside the game there is a biome called Seasonal Forest
		BIOME_FOREST = registerBiomeType(writable, new ResourceIdentifier("forest"), "Forest");
		BIOME_ICE_DESERT = registerBiomeType(writable, new ResourceIdentifier("ice_desert"), "Ice Desert"); // But inside the game it seems to extend the FlatBiome class and it really like Builder in Java
		BIOME_SAVANNA = registerBiomeType(writable, new ResourceIdentifier("savanna"), "Savanna");
		BIOME_SHRUBLAND = registerBiomeType(writable, new ResourceIdentifier("shrubland"), "Shrubland");
		BIOME_TAIGA_FOREST = registerBiomeType(writable, new ResourceIdentifier("taiga"), "Taiga");
		BIOME_DESERT = registerBiomeType(writable, new ResourceIdentifier("desert"), "Desert");
		BIOME_PLAINS = registerBiomeType(writable, new ResourceIdentifier("plains"), "Plains");
		BIOME_TUNDRA = registerBiomeType(writable, new ResourceIdentifier("tundra"), "Tundra");
		
		BUILTIN_BIOME_REGISTRY = writable.freeze();
	}
	
	public static BlockType registerBlockType(WritableRegistry<BlockType> registry, ResourceIdentifier identify, String name, BlockVertexGatherer renderer, BlockCollidable collidable, ResourceIdentifier... tags) {
		BlockType type = new BlockType(identify, name, renderer, collidable);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}
	
//	private static ItemType registerItemFromBlock(WritableRegistry<ItemType> registry, BlockType)
	
	public static ItemType registerItemType(WritableRegistry<ItemType> registry, ResourceIdentifier identify, String name, ItemVertexGatherer renderer, ResourceIdentifier... tags) {
		ItemType type = new ItemType(identify, name, renderer);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}
	
	private static BiomeType registerBiomeType(WritableRegistry<BiomeType> registry, ResourceIdentifier identify, String name, ResourceIdentifier... tags) {
		BiomeType type = new BiomeType(identify, name);
		registry.register(identify, type);
		registry.addTag(identify, tags);
		return type;
	}
	
	public static void callForClazzLoad() {}

}
