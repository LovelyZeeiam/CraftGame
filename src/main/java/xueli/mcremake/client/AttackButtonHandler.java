package xueli.mcremake.client;

import org.lwjgl.glfw.GLFW;

public class AttackButtonHandler extends FunctionalKeyHandler {
	
	public AttackButtonHandler(CraftGameClient ctx) {
		super(ctx, () -> ctx.mouseBindings.getKeyBinding(GLFW.GLFW_MOUSE_BUTTON_LEFT));
	}
	
	private int blockBreakCooldown = 0;
	
	@Override
	protected boolean functionStart() {
		return this.breakBlock();
	}

	@Override
	protected boolean functionContinue() {
		// TODO: In creative mode, when the player is faster, its break cooldown should be faster. So is it when placing blocks.
		blockBreakCooldown += ctx.timer.getNumShouldTick();
		if(blockBreakCooldown > 6) {
			blockBreakCooldown = 0;
			return this.breakBlock();
		}
		
		return false;
	}
	
	private boolean breakBlock() {
		var pickResult = ctx.getPickResult();
		if(pickResult == null) return false;
		var pickBlockPos = pickResult.blockPos();
		ctx.getWorld().setBlock(pickBlockPos.x, pickBlockPos.y, pickBlockPos.z, null);
		return true;
	}

	@Override
	protected boolean functionEnd() {
		blockBreakCooldown = 0;
		return false;
	}
	
}
