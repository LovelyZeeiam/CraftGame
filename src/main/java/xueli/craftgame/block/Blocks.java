package xueli.craftgame.block;

import xueli.craftgame.CraftGame;
import xueli.gamengine.resource.TextureAtlas;

public class Blocks {

	public static void init(long nvg, CraftGame context, TextureAtlas blockTextureAtlas) {

		context.queueRunningInMainThread.add(() -> BlockReviewGenerator.generate(nvg, context, blockTextureAtlas));

	}

}
