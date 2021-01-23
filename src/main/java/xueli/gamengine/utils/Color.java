package xueli.gamengine.utils;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

public class Color {
	
	private byte[] color;
	private NVGColor colorNVG;
	
	public Color(byte[] color) {
		this.color = color;
		
	}
	
	public NVGColor getNVGColor() {
		if(this.colorNVG == null) {
			this.colorNVG = NVGColor.create();
			if (color.length == 4)
				NanoVG.nvgRGBA(color[0],color[1],color[2],color[3], colorNVG);
			else if (color.length == 3)
				NanoVG.nvgRGB(color[0],color[1],color[2], colorNVG);
			else
				return null;
			
		}
		return this.colorNVG;
	}

	public byte[] getColor() {
		return color;
	}

}
