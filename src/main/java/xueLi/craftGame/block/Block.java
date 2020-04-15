package xueLi.craftGame.block;

import java.nio.FloatBuffer;

import xueLi.craftGame.block.data.*;
import xueLi.craftGame.entity.HitBox;

import static xueLi.craftGame.block.BlockData.datas;

public class Block {
	
	public static void init() {
		datas.put(1, new BlockStone());
		datas.put(2, new BlockGrass());
		
	}

	private BlockData data;
	
	public int dataValue = 0;

	public Block(int id) {
		this.data = BlockData.getData(id);
	}
	
	public Block(BlockData data) {
		this.data = data;
	}
	
	public Block(int id,int dataValue) {
		this.data = BlockData.getData(id);
		this.dataValue = dataValue;
	}
	
	public Block(BlockData data,int dataValue) {
		this.data = data;
		this.dataValue = dataValue;
	}
	
	public int getDrawData(FloatBuffer buffer,int x,int y,int z,int face) {
		return this.data.render(buffer, x, y, z, dataValue, face);
	}
	
	public String getName() {
		return data.getName();
	}
	
	public int getID() {
		return data.getId();
	}

	public HitBox getHitbox(int x, int y, int z) {
		return data.getHitBoxWithPos(x,y,z);
	}
	
	
	
	

}
