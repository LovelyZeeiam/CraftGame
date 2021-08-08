package xueli.utils.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Logger {

	public static void info(Object o) {
		wrapLog(o.toString(), Level.INFO);
	}

	public static void error(Object o) {
		wrapLog(o.toString(), Level.SEVERE);
	}

	public static void warning(Object o) {
		wrapLog(o.toString(), Level.WARNING);
	}

	private static void wrapLog(String s, Level l) {
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		StackTraceElement ste = es[3];

		LogRecord r = new LogRecord(l, s);
		r.setLoggerName(ste.getClassName());
		r.setSourceClassName(ste.getClassName());
		r.setSourceMethodName(ste.getMethodName());

		java.util.logging.Logger.getGlobal().log(r);
	}

}
