package xueLi.craftGame.initer;

import static xueLi.craftGame.block.BlockData.datas;

import xueLi.craftGame.block.data.*;

public class Blocks {

	public static void init() {
		datas.put(1, new BlockStone());
		datas.put(2, new BlockGrass());
		datas.put(3, new BlockMusicBox());

	}

}
