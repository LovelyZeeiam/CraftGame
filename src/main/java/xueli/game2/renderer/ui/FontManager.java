package xueli.game2.renderer.ui;

import static org.lwjgl.nanovg.NanoVG.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import xueli.game2.resource.Resource;
import xueli.game2.resource.submanager.render.BufferUtils;

class FontManager {

	private final Gui gui;
	
	FontManager(Gui gui) {
		this.gui = gui;
	}
	
	public int createFont(String name, Resource res) throws IOException {
		byte[] bytes = res.readAll();
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		
		int id = nvgCreateFontMem(gui.nvg, name, buffer, 0);
		
		if(id < 0) {
			throw new IOException("Can't register font: " + name);
		}
		
		return id;
	}
	
}
