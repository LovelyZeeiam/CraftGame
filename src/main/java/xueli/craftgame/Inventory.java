package xueli.craftgame;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.init.Blocks;
import xueli.game.Game;
import xueli.game.display.Display;

public class Inventory {
	
	private Blocks blocks;
	private int i = 0;
	
	public Inventory(Blocks blocks) {
		this.blocks = blocks;
		
	}
	
	public void tick() {
		Display display = Game.INSTANCE_GAME.getDisplay();
		
		this.i += (int) display.getWheelDelta();
		i = Math.floorMod(i, blocks.size());
		
	}
	
	public BlockBase getChosenBase() {
		return blocks.getBaseById(i);
	}

}
