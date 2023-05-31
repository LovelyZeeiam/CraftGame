package xueli.mcremake.client.player;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3i;

import xueli.game2.resource.ResourceIdentifier;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.core.entity.PickResult;
import xueli.mcremake.registry.GameRegistry;

public class UseButtonHandler extends FunctionalKeyHandler {

	public UseButtonHandler(CraftGameClient ctx) {
		super(ctx, ctx.state.keyBindings.getKeyBinding(GLFW.GLFW_MOUSE_BUTTON_RIGHT));
	}

	private int blockPlaceCooldown = 0;

	@Override
	protected void functionStart(CraftGameClient ctx) {
		this.doUse(ctx);
	}

	@Override
	protected void functionContinue(CraftGameClient ctx) {
		blockPlaceCooldown += ctx.timer.getNumShouldTick();
		if (blockPlaceCooldown > 6) {
			blockPlaceCooldown = 0;
			this.doUse(ctx);
		}

	}

	private void doUse(CraftGameClient ctx) {
		PickResult pick = ctx.state.player.pickResult;
		if (pick != null) {
			Vector3i pickBlock = pick.placePos();
			ResourceIdentifier itemBlock = GameRegistry.BUILTIN_ITEM_BLOCK_MAP_REGISTRY
					.getByName(ctx.state.selectedItemType.namespace());
			if (itemBlock != null) {
				ctx.state.world.setBlock(pickBlock.x, pickBlock.y, pickBlock.z,
						GameRegistry.BUILTIN_BLOCK_REGISTRY.getByName(itemBlock));
			}
		}

	}

	@Override
	protected void functionEnd(CraftGameClient ctx) {
		blockPlaceCooldown = 0;

	}

}
