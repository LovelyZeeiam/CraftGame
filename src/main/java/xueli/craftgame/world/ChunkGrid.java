package xueli.craftgame.world;

import java.util.ArrayList;
import java.util.List;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.IntTag;
import com.flowpowered.nbt.ListTag;
import com.flowpowered.nbt.StringTag;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.block.Tile;
import xueli.craftgame.interfaces.Saveable;

public class ChunkGrid implements Saveable {

	private int[][][] states;
	private ArrayList<Tile> tiles = new ArrayList<>();

	public ChunkGrid() {
		this.states = new int[Chunk.size][Chunk.height][Chunk.size];
		this.tiles.add(0, null);

	}

	public void setBlock(int x, int y, int z, Tile tile) {
		int tileID = -1;
		if (tiles.contains(tile)) {
			tileID = tiles.indexOf(tile);
		} else {
			tileID = tiles.size();
			this.tiles.add(tiles.size(), tile);
		}

		this.states[x][y][z] = tileID;

	}

	public Tile getBlock(int x, int y, int z) {
		int state = this.states[x][y][z];
		return tiles.get(state);
	}

	@Override
	public CompoundTag getSaveData(WorldLogic logic) {
		CompoundMap rootTag = new CompoundMap();

		rootTag.put(new IntTag("version", 0));
		ArrayList<CompoundTag> tilesTag = new ArrayList<>();
		for (Tile tile : tiles) {
			if (tile == null) {
				// 空气方块
				CompoundMap airTag = new CompoundMap();
				airTag.put(new StringTag("namespace", "air"));
				tilesTag.add(new CompoundTag("", airTag));
			} else
				tilesTag.add(tile.getSaveData(logic));
		}
		rootTag.put(new ListTag<>("blockStates", CompoundTag.class, tilesTag));

		// 用到一种简单的压缩算法
		ArrayList<IntTag> saveData = new ArrayList<IntTag>();
		
		int stateToSave = -1;
		int stateRepeatNum = 0;
		
		for (int m = 0; m < Chunk.height; m++) {
			for (int i = 0; i < Chunk.size; i++) {
				for (int q = 0; q < Chunk.size; q++) {
					int state = states[i][m][q];
					
					if(stateToSave == state) {
						stateRepeatNum++;
						
					} else {
						saveData.add(new IntTag("", stateToSave));
						saveData.add(new IntTag("", stateRepeatNum));
						
						stateToSave = state;
						stateRepeatNum = 1;
						
					}
					
				}
			}
		}
		
		saveData.add(new IntTag("", stateToSave));
		saveData.add(new IntTag("", stateRepeatNum));
		
		rootTag.put(new ListTag<IntTag>("grid", IntTag.class, saveData));

		return new CompoundTag("", rootTag);
	}

	@Override
	public void setSaveData(CompoundTag data, WorldLogic logic) {
		CompoundMap rootTag = data.getValue();
		int version = (Integer) rootTag.get("version").getValue();
		if (version == 0) {
			List<CompoundTag> tilesTag = ((ListTag<CompoundTag>) rootTag.get("blockStates")).getValue();
			for (CompoundTag tag : tilesTag) {
				CompoundMap statesRoot = tag.getValue();
				String namespace = (String) statesRoot.get("namespace").getValue();
				if (!namespace.equals("air"))
					tiles.add(new Tile(tag, logic));
			}

			List<IntTag> gridCompressList = (List<IntTag>) rootTag.get("grid").getValue();
			
			int pointer = 0;
			int allCount = Chunk.size * Chunk.size * Chunk.height;
			
			int compressListCount = 0;
			
			while(pointer < allCount) {
				int state = gridCompressList.get(compressListCount).getValue();
				int repeatCount = gridCompressList.get(compressListCount + 1).getValue();
				compressListCount += 2;
				
				for(int i = 0; i < repeatCount; i++) {
					int y = pointer / Chunk.size / Chunk.size;
					int x = (pointer - y * Chunk.size * Chunk.size) / Chunk.size;
					int z = pointer - y * Chunk.size * Chunk.size - x * Chunk.size;
					
					states[x][y][z] = state;
					pointer++;
				}
				
			}

		}

	}

}
