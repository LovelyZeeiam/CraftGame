package xueli.craftgame.init;

import xueli.craftgame.block.*;
import xueli.game.module.Modules;
import xueli.game.vector.Vector3b;

public class Blocks extends Modules<BlockBase> {

	private static final String[] COLORS = { "black", "blue", "brown", "cyan", "gray", "green", "light_blue", "lime",
			"magenta", "orange", "pink", "purple", "red", "silver", "white", "yellow" };

	public Blocks() {

	}

	/**
	 * After creating new block, it is a must to register block here!
	 */
	@Override
	public void init() {
		add(new AbstractBlock("craftgame:dirt", "Dirt", "cg:dirt"));
		add(new AbstractBlock("craftgame:grass", "Grass Block", "cg:grass_block_side", "cg:grass_block_side",
				"cg:grass_block_side", "cg:grass_block_side", "cg:grass_block_top", "cg:dirt"));
		add(new AbstractBlock("craftgame:stone", "Stone", "cg:stone"));
		add(new AbstractBlock("craftgame:brick", "Brick", "cg:brick"));
		add(new AbstractBlock("craftgame:lapis_block", "Lapis Block", "cg:lapis_block"));
		add(new AbstractBlock("craftgame:iron_block", "Iron Block", "cg:iron_block"));
		add(new AbstractBlock("craftgame:diamond_block", "Diamond Block", "cg:diamond_block"));
		add(new AbstractBlock("craftgame:gold_block", "Gold Block", "cg:gold_block"));
		add(new AbstractBlock("craftgame:emerald_block", "Emerald Block", "cg:emerald_block"));
		add(new AbstractBlock("craftgame:redstone_block", "Redstone Block", "cg:redstone_block"));
		addColorfulBlock("craftgame:wool_colored_", "Colored Wool", "cg:wool_colored_");
		addColorfulBlock("craftgame:hardened_clay_stained_", "Colored Hardened Clay", "cg:hardened_clay_stained_");
		add(new AbstractBlock("craftgame:oak_plank", "Oak Plank", "cg:planks_oak"));
		add(new AbstractStair("craftgame:oak_stair", "Oak Stair", "cg:planks_oak"));
		add(new AbstractSlab("craftgame:oak_slab", "Oak Slab", "cg:planks_oak"));
		addColorfulAlphaBlock("craftgame:stained_glass_", "Colored Glass", "cg:glass_");
		add(new AbstractPlant("craftgame:plant_grass", "Grass", "cg:double_plant_grass_carried"));
		add(new AbstractPlant("craftgame:flower_allium", "Allium", "cg:flower_allium"));
		add(new AbstractPlant("craftgame:flower_blue_orchid", "Blue Orchid", "cg:flower_blue_orchid"));
		add(new AbstractPlant("craftgame:flower_cornflower", "Cornflower", "cg:flower_cornflower"));
		add(new AbstractPlant("craftgame:flower_dandelion", "Dandelion", "cg:flower_dandelion"));
		add(new AbstractPlant("craftgame:flower_houstonia", "Houstonia", "cg:flower_houstonia"));
		add(new AbstractPlant("craftgame:flower_lily_of_the_valley", "Lily of the valley",
				"cg:flower_lily_of_the_valley"));
		add(new AbstractPlant("craftgame:flower_rose", "Rose", "cg:flower_rose"));
		add(new AbstractPlant("craftgame:flower_rose_blue", "Blue Rose", "cg:flower_rose_blue"));
		add(new AbstractBlock("craftgame:log_oak", "Oak Log", "cg:log_oak", "cg:log_oak", "cg:log_oak", "cg:log_oak",
				"cg:log_oak_top", "cg:log_oak_top"));
		add(new AbstractAlphaBlock("craftgame:leaves_oak", "Oak Leaves", "cg:leaves_oak_carried"));
		add(new AbstractBlock("craftgame:obsidian", "Obsidian", "cg:obsidian"));
		add(new AbstractBlock("craftgame:netherrack", "Netherrack", "cg:netherrack"));
		add(new AbstractBlock("craftgame:nether_brick", "Nether Brick", "cg:nether_brick"));
		add(new AbstractBlock("craftgame:red_nether_brick", "Red Nether Brick", "cg:red_nether_brick"));
		add(new AbstractLightBlock("craftgame:glowstone", "Glowstone", new Vector3b((byte) 12, (byte) 4, (byte) 13),
				"cg:glowstone"));
		add(new AbstractBlock("craftgame:sand", "Sand", "cg:sand"));
		add(new AbstractBlock("craftgame:red_sand", "Red Sand", "cg:red_sand"));
		add(new AbstractPlant("craftgame:sapling_oak", "Oak Sapling", "cg:sapling_oak"));

	}

	public void addColorfulBlock(String namespaceTemplate, String name, String textureTemplate) {
		for (String color : COLORS) {
			add(new AbstractBlock(namespaceTemplate + color, name, textureTemplate + color));
		}
	}

	public void addColorfulAlphaBlock(String namespaceTemplate, String name, String textureTemplate) {
		for (String color : COLORS) {
			add(new AbstractAlphaBlock(namespaceTemplate + color, name, textureTemplate + color));
		}
	}

	@Override
	public void add(BlockBase t) {
		super.add(t);
	}

}
