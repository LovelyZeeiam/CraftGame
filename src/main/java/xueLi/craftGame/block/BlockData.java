package xueLi.craftGame.block;

import java.nio.FloatBuffer;
import java.util.HashMap;

import xueLi.craftGame.entity.HitBox;
import xueLi.craftGame.utils.BlockPos;

public abstract class BlockData {

	public static HashMap<Integer, BlockData> datas = new HashMap<Integer, BlockData>();

	public static BlockData getData(int id) {
		return datas.get(id);
	}

	private int id;
	private String name;

	public BlockData(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// 这个是碰撞箱用的
	public static final HitBox defaultHitbox = new HitBox(0f, 0f, 0f, 1f, 1f, 1f);

	public abstract HitBox getHitbox();

	public HitBox getHitBoxWithPos(BlockPos pos) {
		HitBox box = getHitbox();
		box.x1 += pos.getX();
		box.x2 += pos.getX();
		box.y1 += pos.getY();
		box.y2 += pos.getY();
		box.z1 += pos.getZ();
		box.z2 += pos.getZ();
		return box;
	}

	public HitBox getHitBoxWithPos(int x, int y, int z) {
		HitBox box = getHitbox();
		box.x1 += x;
		box.x2 += x;
		box.y1 += y;
		box.y2 += y;
		box.z1 += z;
		box.z2 += z;
		return box;
	}

	// 渲染方式 返回顶点数量
	public abstract int render(FloatBuffer buffer, int x, int y, int z, int dataValue, int face);

}
