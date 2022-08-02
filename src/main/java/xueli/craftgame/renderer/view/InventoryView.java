package xueli.craftgame.renderer.view;

import org.lwjgl.glfw.GLFW;

import xueli.craftgame.event.EventRaiseGUI;
import xueli.craftgame.renderer.GameViewRenderer;
import xueli.game.resource.NVGImage;
import xueli.game.resource.ResourceHolder;

public class InventoryView extends IngameView {

	private ResourceHolder<NVGImage> imageSidebarBuildingBlock, imageSidebarClose, imageSidebarDecoration,
			imageSidebarPlants;

	public InventoryView(GameViewRenderer master) {
		super(master);

	}

	@Override
	public void init(long nvg) {
		imageSidebarBuildingBlock = imageResourceManager.addToken("inv.sidebar.building_block",
				"images/inventory/building_block.png");
		imageSidebarClose = imageResourceManager.addToken("inv.sidebar.close", "images/inventory/building_block.png");
		imageSidebarDecoration = imageResourceManager.addToken("inv.sidebar.decoration",
				"images/inventory/building_block.png");
		imageSidebarPlants = imageResourceManager.addToken("inv.sidebar.plants", "images/inventory/building_block.png");

	}

	@Override
	protected void stroke(long nvg) {
		if (inputHolder.isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE) || inputHolder.isKeyDownOnce(GLFW.GLFW_KEY_E)) {
			getMaster().getContext().submitEvent(new EventRaiseGUI(null));
		}

	}

	@Override
	public void release(long nvg) {
		super.release(nvg);

	}

	@Override
	public void onSize(long nvg, int width, int height) {
		super.onSize(nvg, width, height);
	}

}
