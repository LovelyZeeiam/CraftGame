package xueli.craftgame.item;

import xueli.craftgame.CraftGame;
import xueli.craftgame.block.data.LeftClick;
import xueli.craftgame.entity.Player;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.Texture;

public abstract class ItemData {

	private final String namespace;

	public ItemData(String namespace) {
		this.namespace = namespace;

	}

	public abstract Texture getTexture(CraftGame game);

	public String getNamespace() {
		return namespace;
	}

	public abstract String getItemName();

	public abstract LeftClick onLeftClick(World world, Player player);

	public abstract void onRightClick(World world, Player player);

}
