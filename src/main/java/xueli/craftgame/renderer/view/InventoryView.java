package xueli.craftgame.renderer.view;

import org.lwjgl.glfw.GLFW;

import xueli.craftgame.event.EventRaiseGUI;
import xueli.craftgame.renderer.GameViewRenderer;
import xueli.game.resource.NVGImage;
import xueli.game.resource.ResourceHolder;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureRenderResource;

public class InventoryView extends IngameView {

	private int imageSidebarBuildingBlock, imageSidebarClose, imageSidebarDecoration, imageSidebarPlants;

	public InventoryView(GameViewRenderer master) {
		super(master);

	}

	@Override
	public void init(long nvg) {
		TextureRenderResource textureManager = getMaster().getContext().getTextureRenderResource();

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
