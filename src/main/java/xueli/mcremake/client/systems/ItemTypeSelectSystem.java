package xueli.mcremake.client.systems;

import org.lwjgl.glfw.GLFW;

import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.IGameSystem;
import xueli.mcremake.registry.GameRegistry;

public class ItemTypeSelectSystem implements IGameSystem {
	
	@Override
	public void update(CraftGameClient ctx) {
		if(ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_1)) {
			ctx.state.selectedItemType = GameRegistry.ITEM_BLOCK_DIRT;
		}
		if(ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_2)) {
			ctx.state.selectedItemType = GameRegistry.ITEM_BLOCK_GRASS;
		}
		if(ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_3)) {
			ctx.state.selectedItemType = GameRegistry.ITEM_BLOCK_STONE;
		}
		if(ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_4)) {
			ctx.state.selectedItemType = GameRegistry.ITEM_BLOCK_BEDROCK;
		}
		if(ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_5)) {
			ctx.state.selectedItemType = GameRegistry.ITEM_BLOCK_SAND;
		}
		if(ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_6)) {
			ctx.state.selectedItemType = GameRegistry.ITEM_BLOCK_GRAVEL;
		}
	}
	
}
