package xueli.craftgame.event;

import xueli.craftgame.block.BlockListener;
import xueli.craftgame.block.BlockListener.Type;
import xueli.craftgame.entitytest.Entity;

public class EventBlockListener {

	private int x, y, z;
	private BlockListener.Type type;
	private Entity entity;

	public EventBlockListener(int x, int y, int z, Type type, Entity entity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.entity = entity;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public BlockListener.Type getType() {
		return type;
	}

	public Entity getEntity() {
		return entity;
	}

}
