package xueli.craftgame.item.data;

import xueli.craftgame.CraftGame;
import xueli.craftgame.block.data.LeftClick;
import xueli.craftgame.entity.Player;
import xueli.craftgame.item.ItemData;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.Texture;

public class ItemFirework extends ItemData {

	public ItemFirework() {
		super("craftgame:firework");

	}

	@Override
	public Texture getTexture(CraftGame game) {
		return game.getTextureManager().getTexture("item.firework");
	}

	@Override
	public String getItemName() {
		return "Firework";
	}

	@Override
	public LeftClick onLeftClick(World world, Player player) {
		world.getWorldLogic().getCg().timerQueue.addQueue(1000, () -> System.out.println("Oops! Feels painful!"));
		world.getWorldLogic().getCg().timerQueue.addQueue(2000, () -> System.out.println("Oops! Feels painful!"));
		world.getWorldLogic().getCg().timerQueue.addQueue(3000, () -> System.out.println("Oops! Feels painful!"));

		return LeftClick.DONT_DESTROY_BLOCK_WHEN_LEFT_CLICK;
	}

	@Override
	public void onRightClick(World world, Player player) {
		System.out.println("Hey! Do you wanna celebrate this Spring Festival?");

	}

}
