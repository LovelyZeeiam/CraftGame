package xueli.mcremake.client.player;

import org.lwjgl.glfw.GLFW;

import xueli.mcremake.client.CraftGameClient;

public class AttackButtonHandler extends FunctionalKeyHandler {
	
	public AttackButtonHandler(CraftGameClient ctx) {
		super(ctx, () -> ctx.mouseBindings.getKeyBinding(GLFW.GLFW_MOUSE_BUTTON_LEFT));
	}
	
	private int blockBreakCooldown = 0;
	
	@Override
	protected void functionStart() {
		this.doAttack();
	}

	@Override
	protected void functionContinue() {
		// TODO: In creative mode, when the player is faster, its break cooldown should be faster. So is it when placing blocks.
		// TODO: When we can aim at an entity, the block break cooldown should not be updated.
		blockBreakCooldown += ctx.timer.getNumShouldTick();
		if(blockBreakCooldown > 6) {
			blockBreakCooldown = 0;
			this.doAttack();
		}
		
	}
	
	private void doAttack() {
		var itemListener = ctx.getCurrentItemType().listener();
		if(itemListener != null) {
			itemListener.onItemAttack(ctx);
		}
	}

	@Override
	protected void functionEnd() {
		blockBreakCooldown = 0;
		
	}
	
}
