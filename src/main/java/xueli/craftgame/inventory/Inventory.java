package xueli.craftgame.inventory;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.init.Blocks;

public class Inventory {

	private Blocks blocks;
	private int i = 0;

	

	public Inventory(Blocks blocks) {
		this.blocks = blocks;

		

	}

	public void tick() {
		
		
	}

	public void close() {
		
		
	}

	public BlockBase getChosenBase() {
		return blocks.getById(i);
	}

	public void setChosenIndex(int i) {
		this.i = i;
	}
	
	public Blocks getBlocks() {
		return blocks;
	}

}
