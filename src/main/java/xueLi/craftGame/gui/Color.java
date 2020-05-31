package xueLi.craftGame.gui;

import org.lwjgl.nanovg.NVGColor;

public class Color {
	
	private static NVGColor colour;
	
	static {
		colour = NVGColor.create();
	}

	public static NVGColor rgba(int r, int g, int b, int a) {
		colour.r(r / 255.0f);
		colour.g(g / 255.0f);
		colour.b(b / 255.0f);
		colour.a(a / 255.0f);
	
		return colour;
	}
	
	

}
