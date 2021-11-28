package xueli.craftgame.inventory;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3i;
import xueli.craftgame.block.BlockBase;
import xueli.craftgame.entity.Player;
import xueli.craftgame.entity.PlayerPicker;
import xueli.craftgame.init.Blocks;
import xueli.game.Game;

import java.util.ArrayList;

public class Inventory {

	public static final int SLOT_NUM = 9;

	private Player player;
	private Blocks blocks;

	private ArrayList<InventoryItem> items = new ArrayList<>();
	private InventoryItem[] slots = new InventoryItem[SLOT_NUM];
	private int chosenSlotId = 0;

	public Inventory(Player player, Blocks blocks) {
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
		if (Game.INSTANCE_GAME.getDisplay().isMouseGrabbed()) {
			chosenSlotId -= (int) Game.INSTANCE_GAME.getDisplay().getWheelDelta();
			chosenSlotId = Math.floorMod(chosenSlotId, SLOT_NUM);

			for (int key = GLFW.GLFW_KEY_1; key <= GLFW.GLFW_KEY_9; key++) {
				if (Game.INSTANCE_GAME.getDisplay().isKeyDownOnce(key)) {
					chosenSlotId = key - GLFW.GLFW_KEY_1;
					return;
				}
			}

			Vector3i playerRayEndBlock = player.getPicker().getSelectedBlock();
			if (Game.INSTANCE_GAME.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
					&& playerRayEndBlock != null) {
				BlockBase playerChosenBase = player.getDimension()
						.getBlock(playerRayEndBlock.getX(), playerRayEndBlock.getY(), playerRayEndBlock.getZ())
						.getBase();
				int chosenSlot = getWhatTheMostPreferredSlotToAddOnMiddleClickBlock(chosenSlotId, playerChosenBase);
				slots[chosenSlot] = items.get(items.indexOf(new BlockInventoryItem(playerChosenBase)));
				chosenSlotId = chosenSlot;
			}

		}

	}

	private int getWhatTheMostPreferredSlotToAddOnMiddleClickBlock(int pre, BlockBase base) {
		for (int i = 0; i < SLOT_NUM; i++) {
			if (slots[i] == null)
				return i;
			else if (slots[i] instanceof BlockInventoryItem && ((BlockInventoryItem) slots[i]).get().equals(base))
				return i;
		}
		return pre;
	}

	public void leftClick(Player player) {
		if (slots[chosenSlotId] != null) {
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
		if (slots[chosenSlotId] != null) {
			slots[chosenSlotId].onRightClick(player);
		}
	}

	public void close() {

	}

	public InventoryItem findBlockItem(String namespace) {
		return items.get(items.indexOf(new BlockInventoryItem(new BlockBase(namespace))));
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
