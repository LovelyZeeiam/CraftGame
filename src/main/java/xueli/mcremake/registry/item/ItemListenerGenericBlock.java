package xueli.mcremake.registry.item;

import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.item.ItemListener;

public class ItemListenerGenericBlock implements ItemListener {
	
	private final BlockType block;
	
	public ItemListenerGenericBlock(BlockType block) {
		this.block = block;
	}
	
	@Override
	public void onItemAttack(CraftGameClient ctx) {
		
	}
	
	@Override
	public void onItemUse(CraftGameClient ctx) {
		
	}
	
}
