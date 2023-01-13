package xueli.game2.renderer.ui;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

import xueli.game2.resource.submanager.render.texture.Texture;

class ImageManager {
	
	private final Gui gui;
	
	ImageManager(Gui gui) {
		this.gui = gui;
	}
	
	public int createImage(Texture tex) {
		return nvglCreateImageFromHandle(gui.nvg, tex.id(), tex.width(), tex.height(), NVG_IMAGE_NEAREST);
	}
	
	
}
