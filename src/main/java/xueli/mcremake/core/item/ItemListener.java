package xueli.mcremake.core.item;

import xueli.mcremake.client.CraftGameClient;

// This listener should only work in client, so this is actually client side
public interface ItemListener {
	
	public static final ItemListener NONE = new ItemListener() {};
	
	default public void onItemAttack(CraftGameClient ctx) {
		var pickResult = ctx.getPickResult();
		if(pickResult == null) return;
		var pickBlockPos = pickResult.blockPos();
		ctx.getWorld().setBlock(pickBlockPos.x, pickBlockPos.y, pickBlockPos.z, null);
		
	}
	
	default public void onItemUse(CraftGameClient ctx) {
		
	}
	
}
