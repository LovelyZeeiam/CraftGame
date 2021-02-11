package xueli.craftgame.item.data;

import xueli.craftgame.CraftGame;
import xueli.craftgame.block.data.LeftClick;
import xueli.craftgame.entity.Player;
import xueli.craftgame.item.ItemData;
import xueli.craftgame.particles.ParticleFirework;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.Texture;
import xueli.gamengine.utils.resource.SoundManager;

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
		return LeftClick.DESTROY_BLOCK_WHEN_LEFT_CLICK;
	}

	@Override
	public void onRightClick(World world, Player player) {
		SoundManager.play("item.yalimasinei", 1.0f, 1.0f);
		world.getWorldLogic().getParticleManager().addParticle(new ParticleFirework(world.getWorldLogic().getCg(), player.getLast_time_ray_end()));
		
	}

}
