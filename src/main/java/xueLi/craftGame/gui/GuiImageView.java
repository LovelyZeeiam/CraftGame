package xueLi.craftGame.gui;

import xueLi.craftGame.inputListener.KeyEvent;
import xueLi.craftGame.inputListener.MouseButtonEvent;

import java.io.File;
import java.util.HashMap;

import org.lwjgl.nanovg.NVGPaint;

import static org.lwjgl.nanovg.NanoVG.*;

import static xueLi.craftGame.gui.GUIRenderer.nvg;

public class GuiImageView extends GUIWidget {

	private static HashMap<String, Integer> imagesMap = new HashMap<String, Integer>();

	private int image;

	private static NVGPaint paint;

	static {
		paint = NVGPaint.create();

	}

	public GuiImageView(String path, float x, float y, WidgetAlignment alignment, float width, float height) {
		super(x, y, alignment, width, height);
		this.image = loadImage(path);

	}

	private static int loadImage(String path) {
		if (imagesMap.containsKey(path))
			return imagesMap.get(path);
		File file = new File(path);
		if (!file.exists()) {
			System.err.println("Can't find image: " + path);
			return -1;
		}

		int pointer = nvgCreateImage(nvg, path, NVG_IMAGE_NEAREST);

		if (pointer == -1) {
			System.err.println("Can't load image: " + path);
			return -1;
		}
		imagesMap.put(path, pointer);
		return pointer;
	}

	@Override
	public void render() {
		nvgImagePattern(nvg, x, y, width, height, 0, image, 1, paint);
		nvgBeginPath(nvg);
		nvgRoundedRect(nvg, x, y, width, height, 0);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

	}

	@Override
	public void onKeyEvent(KeyEvent event) {

	}

	@Override
	public void onMouseButtonEvent(MouseButtonEvent event) {

	}

}
