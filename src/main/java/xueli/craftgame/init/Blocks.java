package xueli.craftgame.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockDefination;
import xueli.craftgame.block.blocks.BlockDirt;
import xueli.craftgame.block.blocks.BlockGrass;
import xueli.craftgame.block.blocks.BlockStone;
import xueli.utils.clazz.ClazzUtils;

public class Blocks {

	private HashMap<String, BlockBase> blocks = new HashMap<>();
	private ArrayList<BlockBase> blockIndices = new ArrayList<>();

	public Blocks() {
		
	}
	
	/**
	 * After creating new block, it is a must to register block here!
	 */
	public void init() {
		addBlock(new BlockDirt());
		addBlock(new BlockGrass());
		addBlock(new BlockStone());
		
		
	}

	public void addBlock(BlockBase base) {
		if (!base.checkInvaildBlockbase()) {
			Logger.getLogger(getClass().getName()).warning("Not an invaild BlockBase class: " + base.toString());
			return;
		}
		blocks.put(base.getNamespace(), base);
		blockIndices.add(base);
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
	
	public int size() {
		return blocks.size();
	}

}
