package xueli.craftgame.entitytest.item;

import xueli.craftgame.player.LocalPlayer;

public interface ItemRenderable {

	public void renderInit();

	public void render(ItemStack stack, LocalPlayer player);

}
