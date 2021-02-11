package xueli.craftgame.item;

import java.util.HashMap;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.client.inventory.Inventory;
import xueli.craftgame.client.inventory.ItemInventoryItem;
import xueli.craftgame.item.data.ItemFirework;

public class Items {

	private static HashMap<String, Item> items = new HashMap<String, Item>();

	public static void init(WorldLogic logic) {
		register(new ItemFirework(), logic);

	}

	private static void register(ItemData data, WorldLogic logic) {
		Item item = new Item(data, logic);
		items.put(data.getNamespace(), item);
		Inventory.inventoryItems.add(new ItemInventoryItem(item));

	}

	public static void release(WorldLogic logic) {
		items.forEach((s, i) -> i.release(logic));
		Inventory.inventoryItems.clear();

	}

}
