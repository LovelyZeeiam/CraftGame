package xueli.craftgame.inventory;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.init.Blocks;
import xueli.game.Game;
import xueli.game.display.Display;

public class Inventory {

	private Blocks blocks;
	private int i = 0;

	private InventoryWindow window;

	public Inventory(Blocks blocks) {
		this.blocks = blocks;

		this.window = new InventoryWindow(blocks, this);
		this.window.setVisible(true);

	}

	public void tick() {
		Display display = Game.INSTANCE_GAME.getDisplay();

		this.i += (int) display.getWheelDelta();
		i = Math.floorMod(i, blocks.size());

	}

	public void close() {
		this.window.dispose();

	}

	public BlockBase getChosenBase() {
		return blocks.getById(i);
	}

	public void setChosenIndex(int i) {
		this.i = i;
	}

}
