package xueli.jrich2;

import org.fusesource.jansi.Ansi;

public class Canvas {
	
	@SuppressWarnings("unused")
	private final RichConsole context;
	
	public final Character[][] buffer;
	private final int width, height;
	
	public Canvas(int width, int height, RichConsole context) {
		this.context = context;
		this.buffer = new Character[width][height];
		this.width = width;
		this.height = height;
		
	}
	
	public void flush(Ansi compiledBuf) {
		Styles lastStyles = new Styles();
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				var c = buffer[i][j];
				
				boolean emptyChar = c == null;
				var styles = emptyChar ? null : c.styles;
				Styles.compile(lastStyles, styles, compiledBuf);
				lastStyles = styles;
				
				compiledBuf.a(emptyChar ? ' ' : c.ch);
				
			}
		}
		compiledBuf.reset();
	}
	
}
