package xueli.jrich;

import org.fusesource.jansi.Ansi;

public interface ConsoleRenderable {

	public void render(int width, int height, Ansi buf);

}
