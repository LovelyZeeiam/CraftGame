package xueli.jrich.widgets;

import org.fusesource.jansi.Ansi;
import xueli.jrich.ConsoleRenderable;

import java.util.List;

public class ConsoleText implements ConsoleRenderable {

	private final String text;

	public ConsoleText(String text) {
		this.text = text;
	}

	@Override
	public void render(int width, int height, Ansi buf) {
		VirtualConsole vc = new VirtualConsole(width);
		boolean reachEnd = false;

		List<String> lines = text.lines().toList();
		for (String line : lines) {
			char[] chars = line.toCharArray();
			for (char c : chars) {
				buf.a(c);

				boolean shouldNextLine = vc.printChar();
				if(vc.getY() >= height) {
					reachEnd = true;
					break;
				} else if(shouldNextLine) {
					buf.newline();
				}

			}

			vc.newLine();
			if(vc.getY() >= height) {
				reachEnd = true;
				break;
			} else {
				buf.newline();
			}

		}

		// If the pointer reaches the end, an ellipsis is shown.
		if(reachEnd) {
			buf.cursorLeft(3);
			buf.a("...");
		}

	}

}
