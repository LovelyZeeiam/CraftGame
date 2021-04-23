package xueli.craftgame.block;

import java.util.ArrayList;

public class Tile {

	private BlockBase base;

	private ArrayList<String> tags = new ArrayList<>();

	public Tile(BlockBase base) {
		this.base = base;

	}

	public BlockBase getBase() {
		return base;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

}
