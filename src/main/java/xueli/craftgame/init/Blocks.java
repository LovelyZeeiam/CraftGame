package xueli.craftgame.init;

import xueli.craftgame.block.AbstractBlock;
import xueli.craftgame.block.AbstractSlab;
import xueli.craftgame.block.AbstractStair;
import xueli.craftgame.block.BlockBase;
import xueli.game.module.Modules;

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
		
		
		
	}

	public void addColorfulBlock(String namespaceTemplate, String name, String textureTemplate) {
		for (String color : COLORS) {
			add(new AbstractBlock(namespaceTemplate + color, name, textureTemplate + color));
		}
	}

	/*
	 * @SuppressWarnings("deprecation") public void searchForAllBlock() {
	 * List<Class<?>> classes =
	 * ClazzUtils.getAllAnnotatedClass(BlockDefination.class); for (Class<?> clazz :
	 * classes) { if (!BlockBase.class.isAssignableFrom(clazz)) {
	 * Logger.getLogger(getClass().getName())
	 * .warning("Oops! Found a class annotated BlockDefination but not assignable from BlockBase: "
	 * + clazz.getName()); continue; } BlockBase base = null; try { base =
	 * clazz.asSubclass(BlockBase.class).newInstance(); } catch
	 * (InstantiationException | IllegalAccessException | IllegalArgumentException |
	 * SecurityException e) { e.printStackTrace(); continue; }
	 * Logger.getLogger(getClass().getName()).info("Found block: " +
	 * base.getNamespace()); addBlock(base);
	 * 
	 * }
	 * 
	 * }
	 */

}
