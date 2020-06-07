package xueLi.craftGame.block;

import java.nio.FloatBuffer;

import xueLi.craftGame.entity.HitBox;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.world.World;

public class Block {

	private BlockData data;

	public int dataValue = 0;

	public BlockPos pos;

	public Block(int id) {
		this.data = BlockData.getData(id);
	}

	public Block(BlockData data) {
		this.data = data;
	}

	public Block(int id, int dataValue) {
		this.data = BlockData.getData(id);
		this.dataValue = dataValue;
	}

	public Block(BlockData data, int dataValue) {
		this.data = data;
		this.dataValue = dataValue;
	}

	public int getDrawData(FloatBuffer buffer, int x, int y, int z, int face) {
		return this.data.render(buffer, x, y, z, dataValue, face);
	}

	public String getName() {
		return data.getName();
	}

	public int getID() {
		return data.getId();
	}

	public void onRightClick(World world) {
		data.onRightClick(world, this);
	}

	public void onDestroy(World world) {
		data.onDestroy(world, this);
	}

	public void onCreate(World world) {
		data.onCreate(world, this);
	}

	public HitBox getHitbox(int x, int y, int z) {
		return data.getHitBoxWithPos(x, y, z);
	}

}
