package xueli.craftgame.item;

import java.util.HashMap;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.BlockItem;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.block.Blocks;

public class Items {

	public static HashMap<BlockType, ItemType> blockItems = new HashMap<>();
	public static HashMap<String, ItemType> items = new HashMap<>();

	public static void initCallForRenderer(CraftGameContext ctx) {
		for (BlockType block : Blocks.blocks.values()) {
			BlockItem item = new BlockItem(block);
			items.put(block.getNamespace(), item);
			blockItems.put(block, item);
		}

	}

}
