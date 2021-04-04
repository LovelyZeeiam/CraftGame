package xueli.game.renderer.widgets;

import static org.lwjgl.nanovg.NanoVG.*;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;

import xueli.game.Game;
import xueli.game.display.DisplayUtils;
import xueli.game.renderer.Toasts.Type;
import xueli.utils.eval.EvalableFloat;

public class ListVertical extends IWidget {
	
	private static NVGColor wheel_color = NVGColor.create(), wheel_clicked_color = NVGColor.create();
	
	private static EvalableFloat margin = new EvalableFloat("7.0 * scale");
	private static EvalableFloat wheel_width = new EvalableFloat("10.0 * scale");
	
	static {
		nvgRGBAf(0.8f, 0.8f, 0.8f, 0.8f, wheel_color);
		nvgRGBAf(0.8f, 0.8f, 0.5f, 0.8f, wheel_clicked_color);
		
	}
	
	private EvalableFloat entry_height;
	private EvalableFloat entry_width;
	
	private EvalableFloat max_height;
	private float wheel_offset;
	
	private ArrayList<IWidget> entries_boundary = new ArrayList<>();
	private ArrayList<IListEntry> entries = new ArrayList<>();
	
	public ListVertical(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat entry_height, EvalableFloat visibleAreaHeight, ArrayList<IListEntry> entries) {
		super(x, y, width, visibleAreaHeight);
		this.entries = entries;
		this.entry_height = entry_height;
		
		this.entry_width = new EvalableFloat(this.width.getExpression() + " - (" + wheel_width.getExpression() + ")");
		
		calculateBoundaries();
		calculateMaxHeight();
		
		
	}
	
	public void addElement(IListEntry entry) {
		entries_boundary.add(calculateBoundary(entries.size()));
		entries.add(entry);
		calculateMaxHeight();
		
	}
	
	public void setElement(int i, IListEntry entry) {
		entries.set(i, entry);
		
	}
	
	private void calculateBoundaries() {
		if(entries.size() != 0) {
			entries_boundary = new ArrayList<>();
			for (int i = 0; i < entries.size(); i++) {
				entries_boundary.add(calculateBoundary(i));
				
			}
			
		}
	}
	
	private IWidget calculateBoundary(int element_count) {
		EvalableFloat entry_y = new EvalableFloat(MessageFormat.format("({0}) + ({1}) * (({2}) + {3})", y.getExpression(), element_count, entry_height.getExpression(), margin.getExpression()));
		return new IWidget(x, entry_y, entry_width, entry_height);
	}
	
	private void calculateMaxHeight() {
		this.max_height = new EvalableFloat(MessageFormat.format("({0}) * ({1}) + ({0} - 1) * ({2})", entries.size(), entry_height.getExpression(), margin.getExpression()));
		
	}
	
	private boolean isDragWheel = false;
	private boolean isMouseInWheel = false;
	
	private int hover_entry_id = -1, chosen_entry_id = -1;

	@Override
	public void stroke(long nvg, String fontName) {
		{
			float wheel_x = x.getValue() + entry_width.getValue();
			float wheel_y = y.getValue();
			float wheel_width = ListVertical.wheel_width.getValue();
			float wheel_height = this.height.getValue();
			
			isMouseInWheel = DisplayUtils.isMouseInBorder(wheel_x, wheel_y, wheel_width, wheel_height);
			
			if(Game.INSTANCE_GAME.getDisplay().isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && isMouseInWheel) {
				isDragWheel = true;
				
			}
			
			if(!Game.INSTANCE_GAME.getDisplay().isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && isDragWheel) {
				isDragWheel = false;
				
			}
			
			if(isDragWheel) {
				this.wheel_offset += Game.INSTANCE_GAME.getDisplay().getCursor_dy();
				
			}
			
		}
		
		if(isMouseHover()) {
			this.wheel_offset -= Game.INSTANCE_GAME.getDisplay().getWheelDelta() * 10.0f;			
			
		} 
		
		if(this.wheel_offset < 0) this.wheel_offset = 0;
		else if(this.wheel_offset > (max_height.getValue() - height.getValue())) this.wheel_offset = max_height.getValue() - height.getValue();
		
		if(isMouseHover() && !(isMouseInWheel || isDragWheel)) {
			hover_entry_id = (int) ((this.wheel_offset + Game.INSTANCE_GAME.getCursorY() - y.getValue()) / (entry_height.getValue() + margin.getValue()));
			
			if(Game.INSTANCE_GAME.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT) && (hover_entry_id > 0 && hover_entry_id < entries.size())) {
				chosen_entry_id = hover_entry_id;
				
				Game.INSTANCE_GAME.getRendererManager().message("Test!", String.valueOf(chosen_entry_id), Type.FINE);
				
			}
			
		} else {
			hover_entry_id = -1;
			
		}
		
		nvgSave(nvg);
		nvgScissor(nvg, x.getValue(), y.getValue(), width.getValue(), height.getValue());
		
		// entries
		for (int i = 0; i < entries.size(); i++) {
			IListEntry entry = entries.get(i);
			IWidget boundary = entries_boundary.get(i);
			
			boolean isHover = hover_entry_id == i;
			boolean isClicked = chosen_entry_id == i;
			
			nvgTranslate(nvg, boundary.getX().getValue(), boundary.getY().getValue() - wheel_offset);
			entry.stroke(nvg, i, boundary.getWidth().getValue(), boundary.getHeight().getValue(), fontName, isHover, isClicked);
			nvgResetTransform(nvg);
			
		}
		
		// wheel
		{
			float wheel_length = height.getValue() * (height.getValue() / max_height.getValue());
			float wheel_y_offset = (height.getValue() - wheel_length) * (wheel_offset / (max_height.getValue() - height.getValue()));
			
			nvgBeginPath(nvg);
			nvgRect(nvg, x.getValue() + entry_width.getValue() + 3.0f * Game.INSTANCE_GAME.getDisplayScale(), y.getValue() + wheel_y_offset, 4.0f * Game.INSTANCE_GAME.getDisplayScale(), wheel_length);
			if(isDragWheel || isMouseInWheel)
				nvgFillColor(nvg, wheel_clicked_color);
			else
				nvgFillColor(nvg, wheel_color);
			nvgFill(nvg);
			
		}
		
		nvgRestore(nvg);
		
	}
	
	public int getChoosenEntryID() {
		return chosen_entry_id;
	}
	
	public int getHoverEntryID() {
		return hover_entry_id;
	}
	
	@Override
	public void size(int w, int h) {
		super.size(w, h);
		
		margin.needEvalAgain();
		calculateBoundaries();
		
		max_height.needEvalAgain();
		
		wheel_width.needEvalAgain();
		
		for (IWidget boundary : entries_boundary) {
			boundary.size(w, h);
		}
		
		
	}

}
