package xueli.gamengine.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public static boolean debug = true;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

	private static synchronized void printDate() {
		System.out.print(format.format(new Date()));

		// 上上一级方法der~
		String className = Thread.currentThread().getStackTrace()[3].getClassName();// 调用的类名
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();// 调用的行数

		System.out.print(" " + className + ": " + lineNumber + " ");

	}

	public static synchronized void info(Object o) {
		printDate();
		System.out.println("[Info] " + o);
	}

	public static synchronized void warn(Object o) {
		printDate();
		System.out.println("[Warn] " + o);
	}

	public static synchronized void error(Object o) {
		printDate();
		System.out.println("[Error] " + o);
	}

	public static synchronized void error(Throwable throwable) {
		throw new RuntimeException(throwable);
	}

	public static synchronized void checkNullAndThrow(Object o, String message) {
		if (o == null)
			error(new Exception(message));
	}

}
