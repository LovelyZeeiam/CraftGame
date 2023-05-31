package xueli.utils.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Attribute;
import org.fusesource.jansi.AnsiConsole;

public class Logger {

//	private static final Ansi.Color STATE_COLOR = Ansi.Color.BLUE;

	private static final HashMap<Level, Ansi.Color> textColor = new HashMap<>();

	static {
//		AnsiConsole.systemInstall();

		textColor.put(Level.INFO, Ansi.Color.GREEN);
		textColor.put(Level.SEVERE, Ansi.Color.RED);
		textColor.put(Level.WARNING, Ansi.Color.YELLOW);

	}

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("HH:mm:ss");

//	private final Stack<String> state = new Stack<>();

	private static Logger DEFAULT_LOGGER = new Logger();

	public static Logger getInstance() {
		return DEFAULT_LOGGER;
	}

//	public void pushState(String state) {
//		this.state.push(state);
//	}
//
//	public void popState() {
//		state.pop();
//	}

	public void info(Object o) {
		wrapLog(o, Level.INFO);
	}

	public void error(Object o) {
		wrapLog(o, Level.SEVERE);
	}

	public void warning(Object o) {
		wrapLog(o, Level.WARNING);
	}

	private void wrapLog(Object s, Level l) {
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		StackTraceElement ste = es[3];

		Ansi.Color color = textColor.get(l);
		if (color == null)
			color = Ansi.Color.DEFAULT;

		Ansi a = Ansi.ansi();
		int consoleWidth = AnsiConsole.getTerminalWidth();
		if (consoleWidth == 0)
			consoleWidth = 100;

		String timeStr = DATE_FORMATTER.format(new Date());
		String pathStr = String.format("%s:%d", ste.getFileName(), ste.getLineNumber());
		int contentLeftStart = timeStr.length() + 1;
		int contentRightEnd = consoleWidth - pathStr.length() - 1;
		int contentPerLine = contentRightEnd - contentLeftStart;
		String contentStr = Objects.toString(s);
		int oneLineLogSpace = contentPerLine - contentStr.length();

		a.reset();
		if (contentPerLine <= 0) {
			a.a(timeStr).newline();
			a.a(Attribute.ITALIC).a(pathStr).a(Attribute.ITALIC_OFF).newline();
			a.fg(color).render(contentStr).fgDefault().newline();
		} else {
			a.a(timeStr).a(" ");

			if (oneLineLogSpace < 0) {
				int lineStartInStr = 0, lineEndInStr = contentPerLine;
				a.fg(color).render(contentStr.substring(lineStartInStr, lineEndInStr)).fgDefault();
				lineStartInStr += contentPerLine;
				lineEndInStr += contentPerLine;

				a.a(" ").a(Attribute.ITALIC).a(pathStr).a(Attribute.ITALIC_OFF).newline();

				while (true) {
					if (lineStartInStr >= contentStr.length())
						break;
					lineEndInStr = Math.min(contentStr.length(), lineEndInStr);

					a.a(" ".repeat(contentLeftStart));
					a.fg(color).render(contentStr.substring(lineStartInStr, lineEndInStr)).fgDefault();
					a.newline();

					lineStartInStr += contentPerLine;
					lineEndInStr += contentPerLine;

				}

			} else {
				a.fg(color).render(contentStr).fgDefault().a(" ".repeat(oneLineLogSpace)).a(" ").a(Attribute.ITALIC)
						.a(pathStr).newline();

			}

		}

		a.reset();
		System.out.print(a);

	}

//	private String stackOut() {
//		StringBuilder b = new StringBuilder();
//		if (!state.empty()) {
//			for (int i = 0; i < state.size() - 1; i++) {
//				String e = state.get(i);
//				b.append(e).append(": ");
//			}
//			b.append(state.peek());
//		}
//		return b.toString();
//	}

}
