package xueli.craftgame.block;

import xueli.craftgame.entity.Entity;
import xueli.craftgame.world.World;

public interface IBlockListener {

	public void onLookAt(int x, int y, int z, World world, Entity player);

	public void onPlaced(int x, int y, int z, World world, Entity player);

	public void onLeftClick(int x, int y, int z, World world, Entity player);

	public void onRightClick(int x, int y, int z, World world, Entity player);

}
