package xueli.jrich2;

import java.awt.Dimension;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import xueli.jrich2.MeasureSpec.Mode;

public class RichConsole {
	
	static {
		AnsiConsole.systemInstall();
	}
	
	public RichConsole() {
		
	}
	
	private int getWidth() {
		int width = AnsiConsole.getTerminalWidth();
		return Math.max(80, width);
	}
	
	public void print(RichWidget widget) {
		int width = getWidth();
		Dimension size = widget.onMeasure(new MeasureSpec(Mode.MAX_LIMITED, width), new MeasureSpec(Mode.UNLIMITED, 0));
		
		Canvas canvas = new Canvas(size.width, size.height, this);
		widget.onDraw(0, 0, size.width, size.height, canvas);
		
		Ansi ansi = Ansi.ansi();
		canvas.flush(ansi);
		AnsiConsole.out().println(ansi);
		
	}
	
}
