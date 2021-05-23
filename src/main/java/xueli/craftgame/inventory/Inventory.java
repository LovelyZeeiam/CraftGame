package xueli.craftgame.inventory;

import java.util.ArrayList;

import xueli.craftgame.entity.Player;
import xueli.craftgame.entity.PlayerPicker;
import xueli.craftgame.init.Blocks;
import xueli.game.Game;

public class Inventory {

	public static final int SLOT_NUM = 9;

	private Player player;
	private Blocks blocks;

	private ArrayList<InventoryItem> items = new ArrayList<>();
	private InventoryItem[] slots = new InventoryItem[SLOT_NUM];
	private int chosenSlotId = 0;
	
	public Inventory(Player player,Blocks blocks) {
		this.blocks = blocks;
		this.player = player;
		init();

	}

	public void init() {
		// BLOCKS INIT
		blocks.get().forEach(b -> {
			items.add(new BlockInventoryItem(b));
		});

	}

	public void tick() {
		if(Game.INSTANCE_GAME.getDisplay().isMouseGrabbed()) {
			chosenSlotId -= (int) Game.INSTANCE_GAME.getDisplay().getWheelDelta();
			chosenSlotId = Math.floorMod(chosenSlotId, SLOT_NUM);
			
		}
		
	}
	
	public void leftClick(Player player) {
		if(slots[chosenSlotId] != null) {
			slots[chosenSlotId].onLeftClick(player);
		} else {
			PlayerPicker picker = player.getPicker();
			if (picker.getSelectedBlock() != null) {
				player.getDimension().setBlock(picker.getSelectedBlock().getX(), picker.getSelectedBlock().getY(),
						picker.getSelectedBlock().getZ(), null);
			}
		}
	}
	
	public void rightClick(Player player) {
		if(slots[chosenSlotId] != null) {
			slots[chosenSlotId].onRightClick(player);
		}
	}

	public void close() {
		
	}

	public void setChosenIndex(int i) {
		this.chosenSlotId = i;
	}

	public Blocks getBlocks() {
		return blocks;
	}

	public ArrayList<InventoryItem> getItems() {
		return items;
	}

	public InventoryItem[] getSlots() {
		return slots;
	}
	
	public int getChosenSlotId() {
		return chosenSlotId;
	}
	
	public Player getPlayer() {
		return player;
	}

}
