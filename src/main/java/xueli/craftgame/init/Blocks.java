package xueli.craftgame.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockDefination;
import xueli.craftgame.block.blocks.AbstractBlock;
import xueli.utils.clazz.ClazzUtils;

public class Blocks {

	private static final String[] COLORS = {
		"black", "blue", "brown", "cyan", "gray", "green", "light_blue", "lime", "magenta", "orange", "pink", "purple", "red", "silver", "white", "yellow"
	};
	
	private HashMap<String, BlockBase> blocks = new HashMap<>();
	private ArrayList<BlockBase> blockIndices = new ArrayList<>();

	public Blocks() {
		
	}
	
	/**
	 * After creating new block, it is a must to register block here!
	 */
	public void init() {
		addBlock(new AbstractBlock("craftgame:dirt", "Dirt", "cg:dirt"));
		addBlock(new AbstractBlock("craftgame:grass", "Grass Block", "cg:grass_block_side", "cg:grass_block_side", "cg:grass_block_side","cg:grass_block_side", "cg:grass_block_top", "cg:dirt"));
		addBlock(new AbstractBlock("craftgame:stone", "Stone", "cg:stone"));
		addBlock(new AbstractBlock("craftgame:brick", "Brick", "cg:brick"));
		addBlock(new AbstractBlock("craftgame:lapis_block", "Lapis Block", "cg:lapis_block"));
		addBlock(new AbstractBlock("craftgame:iron_block", "Iron Block", "cg:iron_block"));
		addBlock(new AbstractBlock("craftgame:diamond_block", "Diamond Block", "cg:diamond_block"));
		addBlock(new AbstractBlock("craftgame:gold_block", "Gold Block", "cg:gold_block"));
		addBlock(new AbstractBlock("craftgame:emerald_block", "Emerald Block", "cg:emerald_block"));
		addBlock(new AbstractBlock("craftgame:redstone_block", "Redstone Block", "cg:redstone_block"));
		addColorfulBlock("craftgame:wool_colored_", "Colored Wool", "cg:wool_colored_");
		addColorfulBlock("craftgame:hardened_clay_stained_", "Colored Hardened Clay", "cg:hardened_clay_stained_");
		
		
		
	}

	public void addBlock(BlockBase base) {
		if (!base.checkInvaildBlockbase()) {
			Logger.getLogger(getClass().getName()).warning("Not an invaild BlockBase class: " + base.toString());
			return;
		}
		blocks.put(base.getNamespace(), base);
		blockIndices.add(base);
	}
	
	public void addColorfulBlock(String namespaceTemplate, String name, String textureTemplate) {
		for (String color : COLORS) {
			addBlock(new AbstractBlock(namespaceTemplate + color, name, textureTemplate + color));
		}
	}

	public BlockBase getBlockBase(String namespace) {
		BlockBase base = blocks.get(namespace);
		if (base == null) {
			Logger.getLogger(getClass().getName()).warning("Found no BlockBase named: " + namespace);
			return null;
		}
		return base;
	}
	
	public BlockBase getBaseById(int i) {
		return blockIndices.get(i);
	}

	@SuppressWarnings("deprecation")
	public void searchForAllBlock() {
		List<Class<?>> classes = ClazzUtils.getAllAnnotatedClass(BlockDefination.class);
		for (Class<?> clazz : classes) {
			if (!BlockBase.class.isAssignableFrom(clazz)) {
				Logger.getLogger(getClass().getName())
						.warning("Oops! Found a class annotated BlockDefination but not assignable from BlockBase: "
								+ clazz.getName());
				continue;
			}
			BlockBase base = null;
			try {
				base = clazz.asSubclass(BlockBase.class).newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
				e.printStackTrace();
				continue;
			}
			Logger.getLogger(getClass().getName()).info("Found block: " + base.getNamespace());
			addBlock(base);

		}

	}
	
	public ArrayList<BlockBase> get() {
		return blockIndices;
	}
	
	public int size() {
		return blocks.size();
	}

}
