package xueli.utils.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;

import org.fusesource.jansi.Ansi;

public class MyLogger {

	private static final Ansi.Color STATE_COLOR = Ansi.Color.BLUE;

	private static final HashMap<Level, Ansi.Color> textColor = new HashMap<>();

	static {
		textColor.put(Level.INFO, Ansi.Color.CYAN);
		textColor.put(Level.SEVERE, Ansi.Color.RED);
		textColor.put(Level.WARNING, Ansi.Color.YELLOW);

	}

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("aa hh:mm:ss");

	private final Stack<String> state = new Stack<>();

	private static MyLogger DEFAULT_LOGGER = new MyLogger();

	public static MyLogger getInstance() {
		return DEFAULT_LOGGER;
	}

	public void pushState(String state) {
		this.state.push(state);
	}

	public void popState() {
		state.pop();
	}

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
		a.a(DATE_FORMATTER.format(new Date(System.currentTimeMillis()))).a(" ").a(Ansi.Attribute.INTENSITY_BOLD)
				.fg(STATE_COLOR);
		a.a("[").a(stackOut()).a("]");
		a.a(Ansi.Attribute.RESET).fgDefault().a(" ").a(ste.getFileName()).a(":").a(String.valueOf(ste.getLineNumber()))
				.a(" ");
		a.fg(color).a(Ansi.Attribute.INTENSITY_BOLD).a(String.valueOf(s)).a(Ansi.Attribute.RESET).fgDefault();

		System.out.println(a);

	}

	private String stackOut() {
		StringBuilder b = new StringBuilder();
		if (!state.empty()) {
			for (int i = 0; i < state.size() - 1; i++) {
				String e = state.get(i);
				b.append(e).append(": ");
			}
			b.append(state.peek());
		}
		return b.toString();
	}

}
