package xueLi.craftGame.gui;

import xueLi.craftGame.utils.Display;
import xueLi.craftGame.utils.GLHelper;

import java.io.IOException;
import java.util.HashMap;

import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.nanovg.NanoVG.*;

public class GUIRenderer {

	public static GUI currentGui;

	public static long nvg;
	private static HashMap<String, Integer> fontsHashMap = new HashMap<String, Integer>();

	public static boolean init() throws IOException {
		nvg = nvgCreate(NVG_STENCIL_STROKES);
		if (nvg == 0) {
			System.err.println("Can't init NanoVG xD I'm not convinced that you play this game without a menu :}");
			return false;
		}

		loadFontFromTTF("System", "res/fonts/simhei.ttf");

		return true;
	}

	public static void loadFontFromTTF(String name, String path) throws IOException {
		int font = nvgCreateFont(nvg, name, path);
		if (font == -1) {
			System.err.println("Can't load font: " + path);
		} else {
			fontsHashMap.put(name, font);
		}
	}

	public static void setGUI(GUI gui) {
		currentGui = gui;
		if (gui == null)
			Display.setSubtitie(null);
		else
			Display.setSubtitie(gui.title);
	}

	public static void render() {
		if (currentGui != null) {
			GLHelper.disableDepthTest();
			GLHelper.enableBlendAlpha();
			GLHelper.enableStencilTest();
			nvgBeginFrame(nvg, Display.d_width, Display.d_height, 1);
			currentGui.render();
			nvgEndFrame(nvg);
			GLHelper.disableStencilTest();
			GLHelper.disableBlendAlpha();
			GLHelper.enableDepthTest();
		}
	}

	public static void sizedUpdate(float width, float height) {
		if(currentGui != null)
			currentGui.sizedUpdate(width, height);
		
	}

}
