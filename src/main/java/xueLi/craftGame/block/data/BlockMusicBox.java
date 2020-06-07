package xueLi.craftGame.block.data;

import java.nio.FloatBuffer;
import java.util.HashMap;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.block.BlockData;
import xueLi.craftGame.entity.HitBox;
import xueLi.craftGame.utils.ALHelper;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.world.World;

public class BlockMusicBox extends BlockData {

	private static HashMap<BlockPos, ALHelper.Speaker> speakersHashMap = new HashMap<BlockPos, ALHelper.Speaker>();

	public BlockMusicBox() {
		super(3, "Music Box");
	}

	@Override
	public HitBox getHitbox() {
		return null;
	}

	@Override
	public int render(FloatBuffer buffer, int x, int y, int z, int dataValue, int face) {

		return 0;
	}

	@Override
	public void onCreate(World w, Block b) {
		ALHelper.Speaker speaker = new ALHelper.Speaker(1, 1);
		speakersHashMap.put(b.pos, speaker);

	}

	@Override
	public void onDestroy(World w, Block b) {
		ALHelper.Speaker speaker = speakersHashMap.get(b.pos);
		if (speaker == null)
			return;
		speaker.delete();
		speakersHashMap.remove(b.pos);

	}

	@Override
	public void onRightClick(World w, Block b) {

	}

}
