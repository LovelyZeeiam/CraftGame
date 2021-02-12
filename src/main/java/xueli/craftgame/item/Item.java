package xueli.craftgame.item;

import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import xueli.craftgame.WorldLogic;
import xueli.gamengine.resource.Texture;

public class Item {

	private ItemData data;
	private int nvgTexture;

	public Item(ItemData data, WorldLogic logic) {
		this.data = data;

		Texture texture = data.getTexture(logic.getCg());
		this.nvgTexture = NanoVGGL3.nvglCreateImageFromHandle(logic.getNvg(), texture.id, texture.width, texture.height,
				NanoVG.NVG_IMAGE_NEAREST);

	}

	public int getNvgTexture() {
		return nvgTexture;
	}

	public ItemData getData() {
		return data;
	}

	public void release(WorldLogic logic) {
		

	}

}
