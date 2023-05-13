package xueli.mcremake.client.player;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3i;

import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.core.entity.PickResult;

public class AttackButtonHandler extends FunctionalKeyHandler {
	
	public AttackButtonHandler(CraftGameClient ctx) {
		super(ctx, ctx.state.keyBindings.getKeyBinding(GLFW.GLFW_MOUSE_BUTTON_LEFT));
		
	}
	
	private int blockBreakCooldown = 0;
	
	@Override
	protected void functionStart(CraftGameClient ctx) {
		this.doAttack(ctx);
	}

	@Override
	protected void functionContinue(CraftGameClient ctx) {
		// TODO: In creative mode, when the player is faster, its break cooldown should be faster. So is it when placing blocks.
		// TODO: When we can aim at an entity, the block break cooldown should not be updated.
		blockBreakCooldown += ctx.timer.getNumShouldTick(); // TODO: should use tick
		if(blockBreakCooldown > 6) {
			blockBreakCooldown = 0;
			this.doAttack(ctx);
		}
		
	}
	
	private void doAttack(CraftGameClient ctx) {
		PickResult pick = ctx.state.player.pickResult;
		Vector3i pickBlock = pick.blockPos();
		ctx.state.world.setBlock(pickBlock.x, pickBlock.y, pickBlock.z, null);
		
	}

	@Override
	protected void functionEnd(CraftGameClient ctx) {
		blockBreakCooldown = 0;
		
	}
	
}
