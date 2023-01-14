package xueli.mcremake.client.player;

import org.lwjgl.glfw.GLFW;

import xueli.mcremake.client.CraftGameClient;

public class UseButtonHandler extends FunctionalKeyHandler {

	public UseButtonHandler(CraftGameClient ctx) {
		super(ctx, () -> ctx.mouseBindings.getKeyBinding(GLFW.GLFW_MOUSE_BUTTON_RIGHT));
	}
	
	private int blockPlaceCooldown = 0;

	@Override
	protected void functionStart() {
		this.doUse();
	}

	@Override
	protected void functionContinue() {
		blockPlaceCooldown += ctx.timer.getNumShouldTick();
		if(blockPlaceCooldown > 6) {
			blockPlaceCooldown = 0;
			this.doUse();
		}
		
	}
	
	private void doUse() {
		var itemListener = ctx.getCurrentItemType().listener();
		if(itemListener != null) {
			itemListener.onItemUse(ctx);
		}
	}

	@Override
	protected void functionEnd() {
		blockPlaceCooldown = 0;
		
	}
	
}
