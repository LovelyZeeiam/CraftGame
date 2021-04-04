package xueli.game.renderer;

import static org.lwjgl.nanovg.NanoVG.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.nanovg.NVGColor;

import xueli.craftgame.CraftGame;
import xueli.game.utils.Time;

public class Toasts extends NVGRenderer {
	
	public static long TOAST_LONG_TIME = 6000;
	public static long TOAST_SHORT_TIME = 2000;
	
	private static final String font_name = "toast_font";
	
	// offset from the right top corner of the screen
	private static final float offset_x = 10.0f;
	private static final float offset_y = 10.0f;
	
	// toast size
	private static final float width = 250.0f;
	private static final float height = 50.0f;
	private static final float margin = 1.0f;
	
	// toast icon
	private static final float icon_size = 25.0f;
	private static final float icon_push_offset = 5.0f;
	
	// toast content
	private static final float content_margin = 10.0f;
	
	private static final long TOAST_FADE_IN_AND_OUT_TIME = 500;

	private static final NVGColor fine_title_color = NVGColor.create(), exception_title_color = NVGColor.create(), error_title_color = NVGColor.create(), confusion_title_color = NVGColor.create();
	private static final NVGColor fine_content_color = NVGColor.create(), exception_content_color = NVGColor.create(), error_content_color = NVGColor.create(), confusion_content_color = NVGColor.create();
	
	static {
		nvgRGBAf(0.6f, 0.6f, 1,1, fine_title_color);
		nvgRGBAf(1, 1, 0.6f,1, exception_title_color);
		nvgRGBAf(1, 0.6f, 0.6f,1, error_title_color);
		nvgRGBAf(0.6f, 0.6f, 1,1, confusion_title_color);
		
		nvgRGBAf(0.4f, 0.4f, 0.6f,0.6f, fine_content_color);
		nvgRGBAf(0.6f, 0.6f, 0.4f,0.6f, exception_content_color);
		nvgRGBAf(0.6f, 0.4f, 0.4f,0.6f, error_content_color);
		nvgRGBAf(0.4f, 0.4f, 0.6f,0.6f, confusion_content_color);
		
		
	}
	
	private LinkedList<Toast> queueToast = new LinkedList<>();
	private ArrayList<Toast> toasts = new ArrayList<>();
	
	private int maxDisplay = 0;
	
	private int tex_toast;
	private int tex_error, tex_fine, tex_confusion, tex_exception;
	
	public Toasts() {
		super();
		
		this.tex_toast = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/toast/toast_1.png", NVG_IMAGE_NEAREST | NVG_IMAGE_GENERATE_MIPMAPS);
		
		this.tex_error = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/toast/toast_error.png", NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_fine = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/toast/toast_fine.png", NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_confusion = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/toast/toast_confusion.png", NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_exception = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/toast/toast_exception.png", NVG_IMAGE_GENERATE_MIPMAPS);
		
		nvgCreateFont(nvg, font_name, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/fonts/Minecraft-Ascii.ttf");
		
		calculateScreen();
		
	}

	@Override
	public void stroke() {
		if(toasts.size() < maxDisplay && !queueToast.isEmpty()) {
				Toast toast = queueToast.poll();
				toast.setStartTime();
				toasts.add(toast);
			
		}
		
		ArrayList<Toast> deathToasts = new ArrayList<>();
		
		for (Toast toast : toasts) {
			// Time from being born
			double aliveTime = Time.thisTime - toast.startTime;
			
			if(aliveTime > (TOAST_FADE_IN_AND_OUT_TIME * 2 + toast.aliveTime)) {
				deathToasts.add(toast);
				continue;
				
			}
			
			float fade_progress = 0.0f;
			
			if(aliveTime < TOAST_FADE_IN_AND_OUT_TIME) {
				// fade in
				fade_progress = (float) Math.sin((aliveTime / TOAST_FADE_IN_AND_OUT_TIME) * (Math.PI / 2));
			} else if(aliveTime > TOAST_FADE_IN_AND_OUT_TIME + toast.aliveTime) {
				// fade out
				fade_progress = (float) Math.cos(((aliveTime - TOAST_FADE_IN_AND_OUT_TIME - toast.aliveTime) / TOAST_FADE_IN_AND_OUT_TIME) * (Math.PI / 2));
			} else {
				fade_progress = 1.0f;
			}
			
			nvgTranslate(nvg, game.getWidth() - (width + offset_x) * game.getDisplayScale() * fade_progress, offset_y * game.getDisplayScale() + (margin + height) * game.getDisplayScale() * toast.index);
			
			float icon_margin = (height - icon_size) * game.getDisplayScale() / 2;
			
			float text_size = 10.0f * game.getDisplayScale();
			
			// background
			{
				nvgImagePattern(nvg, 0, 0, width * game.getDisplayScale(), height * game.getDisplayScale(), 0, tex_toast, 1, paint);
				nvgBeginPath(nvg);
				nvgRect(nvg, 0, 0, width * game.getDisplayScale(), height * game.getDisplayScale());
				nvgFillPaint(nvg, paint);
				nvgFill(nvg);
				
			}
			
			// icon
			{
				float real_size = icon_size * game.getDisplayScale();
				
				nvgImagePattern(nvg, icon_margin, icon_margin, real_size, real_size, 0, getIconTexture(toast.type), 1, paint);
				nvgBeginPath(nvg);
				nvgRect(nvg, icon_margin, icon_margin, real_size, real_size);
				nvgFillPaint(nvg, paint);
				nvgFill(nvg);
				
			}
			
			// title
			{
				nvgFontSize(nvg, text_size);
				nvgFontFace(nvg, font_name);
				nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
				nvgFillColor(nvg, getTitleColor(toast.type));
				nvgText(nvg, (icon_margin + icon_size + icon_push_offset) * game.getDisplayScale(), content_margin * game.getDisplayScale(), toast.title);
				
			}
			
			// message
			{
				nvgFontSize(nvg, text_size);
				nvgFontFace(nvg, font_name);
				nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_CENTER);
				nvgFillColor(nvg, getContentColor(toast.type));
				nvgTextBox(nvg, (icon_margin + icon_size + icon_push_offset) * game.getDisplayScale(), height * game.getDisplayScale() / 5 * 3, width * game.getDisplayScale() - 2 * icon_margin - icon_size * game.getDisplayScale() - 2 * content_margin , toast.message);
				
			}
			
			nvgResetTransform(nvg);
				
		}
		
		toasts.removeAll(deathToasts);
		
	}
	
	private int getIconTexture(Type type) {
		switch (type) {
		case ERROR:
			return tex_error;
		case EXCEPTION:
			return tex_exception;
		case FINE:
			return tex_fine;
		case CONFUSION:
			return tex_confusion;
		default:
			return tex_fine;
		}
	}
	
	private NVGColor getTitleColor(Type type) {
		switch (type) {
		case ERROR:
			return error_title_color;
		case EXCEPTION:
			return exception_title_color;
		case FINE:
			return fine_title_color;
		case CONFUSION:
			return confusion_title_color;
		default:
			return fine_title_color;
		}
	}
	
	private NVGColor getContentColor(Type type) {
		switch (type) {
		case ERROR:
			return error_content_color;
		case EXCEPTION:
			return exception_content_color;
		case FINE:
			return fine_content_color;
		case CONFUSION:
			return confusion_content_color;
		default:
			return fine_content_color;
		}
	}
	
	@Override
	public void size(int w, int h) {
		super.size(w, h);
		
		calculateScreen();
		
	}
	
	// Calculate how many slot the screen is able to display
	private void calculateScreen() {
		this.maxDisplay = (int) ((game.getHeight() - 2 * Toasts.offset_y * game.getDisplayScale()) / (Toasts.margin + Toasts.height) / game.getDisplayScale());
		
	}
	
	private int lastAssigned = -1;
	public int assignToastSlot() {
		lastAssigned++;
		if(lastAssigned >= maxDisplay || toasts.isEmpty())
			lastAssigned = 0;
		return lastAssigned;
	}
	
	public void submit(String title, String message, Type type) {
		queueToast.add(new Toast(title, message, type, TOAST_SHORT_TIME + TOAST_FADE_IN_AND_OUT_TIME * 2));
		
	}
	
	private class Toast {
		
		public String title, message;
		private Type type;
		public long aliveTime;
		
		public long startTime;
		
		public int index;

		public Toast(String title, String message, Type type, long aliveTime) {
			this.title = title;
			this.message = message;
			this.aliveTime = aliveTime;
			this.type = type;
			
			this.index = assignToastSlot();
			
		}
		
		public void setStartTime() {
			this.startTime = Time.thisTime;
		}
		
	}
	
	public static enum Type {
		ERROR, EXCEPTION, FINE, CONFUSION;
		
	}

}
