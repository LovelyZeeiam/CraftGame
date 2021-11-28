package xueli.craftgame.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.craftgame.block.BlockBase;

public class Tile {

	private BlockBase base;

	CompoundMap tags = new CompoundMap();

	public Tile(BlockBase base) {
		this.base = base;

	}

	public BlockBase getBase() {
		return base;
	}

	public CompoundMap getTags() {
		return tags;
	}

}