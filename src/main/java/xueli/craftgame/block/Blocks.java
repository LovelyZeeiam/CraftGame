package xueli.craftgame.block;

import xueli.craftgame.CraftGame;
import xueli.craftgame.client.inventory.BlockInventoryItem;
import xueli.craftgame.client.inventory.Inventory;
import xueli.gamengine.resource.TextureAtlas;

public class Blocks {

	public static void init(long nvg, CraftGame context, TextureAtlas blockTextureAtlas) {
		// 方块监听
		// TODO: 半砖补全！
		BlockResource.blockDatas.forEach((namespace, data) -> {
			if (namespace.endsWith(".slab")) {
				String originNamespace = namespace.substring(0, namespace.length() - 4);

			}

		});

		// 生成方块预览图
		context.queueRunningInMainThread.add(() -> BlockReviewGenerator.generate(nvg, context, blockTextureAtlas));
		BlockResource.blockDatas.forEach((s, b) -> Inventory.inventoryItems.add(new BlockInventoryItem(b)));

	}
	
	public static void release() {
		BlockResource.blockDatas.clear();
		
	}

}
