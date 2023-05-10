package xueli.mcremake.core.item;

import xueli.mcremake.client.CraftGameClient;

// This listener should only work in client, so this is actually client side
public interface ItemListener {
	
	public static final ItemListener NONE = new ItemListener() {};
	
	default public void onItemAttack(CraftGameClient ctx) {
		
	}
	
	default public void onItemUse(CraftGameClient ctx) {
		
	}
	
}
