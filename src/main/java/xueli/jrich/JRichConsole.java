package xueli.jrich;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.function.Supplier;

public class JRichConsole {

	static {
		AnsiConsole.systemInstall();
	}

	private final Supplier<Integer> column;

	public JRichConsole() {
		this.column = () -> {
			int width = AnsiConsole.getTerminalWidth();
			return Math.max(80, width);
		};

	}

	public JRichConsole(int column) {
		this.column = () -> column;

	}

	public void render(ConsoleRenderable c) {
		Ansi buffer = Ansi.ansi();
		c.render(this.column.get(), Integer.MAX_VALUE, buffer);
		buffer.reset();
		AnsiConsole.sysOut().print(buffer);

	}

}
