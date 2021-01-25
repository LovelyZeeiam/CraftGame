package xueli.craftgame.block;

import xueli.craftgame.CraftGame;
import xueli.gamengine.resource.TextureAtlas;

public class Blocks {

	public static void init(long nvg, CraftGame context, TextureAtlas blockTextureAtlas) {
		// 方块监听
		BlockResource.blockDatas.forEach((namespace, data) -> {
			if(namespace.endsWith(".slab")) {
				String originNamespace = namespace.substring(0, namespace.length() - 4);
				System.out.println(namespace + ": " + originNamespace);

			}
		});

		// 生成方块预览图
		context.queueRunningInMainThread.add(() -> BlockReviewGenerator.generate(nvg, context, blockTextureAtlas));

	}

}
