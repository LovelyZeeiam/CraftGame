package xueli.mcremake.client;

import org.lwjgl.glfw.GLFW;

import xueli.mcremake.registry.GameRegistry;

public class UseButtonHandler extends FunctionalKeyHandler {

	public UseButtonHandler(CraftGameClient ctx) {
		super(ctx, () -> ctx.mouseBindings.getKeyBinding(GLFW.GLFW_MOUSE_BUTTON_RIGHT));
	}
	
	private int blockPlaceCooldown = 0;

	@Override
	protected boolean functionStart() {
		return this.setBlock();
	}

	@Override
	protected boolean functionContinue() {
		blockPlaceCooldown += ctx.timer.getNumShouldTick();
		if(blockPlaceCooldown > 6) {
			blockPlaceCooldown = 0;
			return this.setBlock();
		}
		
		return false;
	}
	
	private boolean setBlock() {
		var pickResult = ctx.getPickResult();
		if(pickResult == null) return false;
		var pickBlockPos = pickResult.placePos();
		ctx.getWorld().setBlock(pickBlockPos.x, pickBlockPos.y, pickBlockPos.z, GameRegistry.BLOCK_STONE);
		return true;
	}

	@Override
	protected boolean functionEnd() {
		blockPlaceCooldown = 0;
		return false;
	}
	
	
	
}
