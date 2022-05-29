package xueli.craftgame.event;

import com.flowpowered.nbt.CompoundMap;

import xueli.craftgame.block.BlockType;
import xueli.craftgame.entity.Entity;

public class EventSetBlock {

	private Type eventType;
	private int x, y, z;
	private BlockType block;
	private CompoundMap tag;
	private Entity entity = null;

	public EventSetBlock(int x, int y, int z, BlockType block, Entity entity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.eventType = Type.BLOCK;
		this.entity = entity;
	}

	public EventSetBlock(int x, int y, int z, CompoundMap tag, Entity entity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tag = tag;
		this.eventType = Type.TAG;
		this.entity = entity;
	}

	public EventSetBlock(int x, int y, int z, BlockType block, CompoundMap tag, Entity entity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.tag = tag;
		this.eventType = Type.BLOCK_TAG;
		this.entity = entity;
	}

	public Type getEventType() {
		return eventType;
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

	public BlockType getBlock() {
		return block;
	}

	public CompoundMap getTag() {
		return tag;
	}

	public Entity getEntity() {
		return entity;
	}

	public enum Type {
		BLOCK, TAG, BLOCK_TAG
	}

}
