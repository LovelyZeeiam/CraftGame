package xueli.craftgame.client.inventory;

import java.util.ArrayList;

import xueli.craftgame.block.Tile;
import xueli.craftgame.entity.Player;
import xueli.craftgame.world.World;

public class Inventory {

	public static final int DISPLAY_SLOT_COUNT = 9;
	public static final int DISPLAY_SLOT_SIZE = 10;
	
	private World world;
	private Player player;

	public static ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();

	private InventoryItem[] itemSlots = new InventoryItem[DISPLAY_SLOT_COUNT];
	private int handInSlot = 0;
	
	public Inventory(World world, Player player) {
		this.world = world;
		this.player = player;
		
		itemSlots[0] = inventoryItems.get(0);
		
	}
	
	public int getHandInSlot() {
		return handInSlot;
	}
	
	public void setHandInSlot(int handInSlot) {
		while(handInSlot >= DISPLAY_SLOT_COUNT) {
			handInSlot -= DISPLAY_SLOT_COUNT;
		}
		while(handInSlot < 0) {
			handInSlot += DISPLAY_SLOT_COUNT;
		}
		this.handInSlot = handInSlot;
	}
	
	public void setSlot(int slot, InventoryItem item) {
		itemSlots[slot] = item;
		
	}
	
	public InventoryItem getSlot(int slot) {
		return itemSlots[slot];
	}
	
	public void onLeftClick() {
		if(itemSlots[handInSlot] != null)
			itemSlots[handInSlot].onLeftClick(world, player);
		else {
			Tile tile = world.getBlock(player.getBlock_select().getX(), player.getBlock_select().getY(), player.getBlock_select().getZ());
			tile.getListener().onLeftClick(player.getBlock_select().getX(), player.getBlock_select().getY(), player.getBlock_select().getZ(), world);
			
		}
		
	}
	
	public void onRightClick() {
		if(itemSlots[handInSlot] != null)
			itemSlots[handInSlot].onRightClick(world, player);
		
	}

}
