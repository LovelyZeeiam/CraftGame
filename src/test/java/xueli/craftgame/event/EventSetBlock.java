package xueli.craftgame.event;

import com.flowpowered.nbt.CompoundMap;

import xueli.craftgame.block.BlockType;

public class EventSetBlock {

	private Type eventType;
	private int x, y, z;
	private BlockType block;
	private CompoundMap tag;

	public EventSetBlock(int x, int y, int z, BlockType block) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.eventType = Type.BLOCK;
	}

	public EventSetBlock(int x, int y, int z, CompoundMap tag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tag = tag;
		this.eventType = Type.TAG;
	}

	public EventSetBlock(int x, int y, int z, BlockType block, CompoundMap tag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.tag = tag;
		this.eventType = Type.BLOCK_TAG;
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

	public enum Type {
		BLOCK, TAG, BLOCK_TAG
	}

}
