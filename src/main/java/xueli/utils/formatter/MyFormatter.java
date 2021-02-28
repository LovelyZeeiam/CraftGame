package xueli.utils.formatter;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

	private static final String lineSeparate = System.getProperty("line.separator");

	@Override
	public String getHead(Handler h) {
		return "time,class,method,type,message" + lineSeparate;
	}

	@Override
	public String format(LogRecord record) {
		return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%s", record.getInstant().toString(),
				record.getSourceClassName(), record.getSourceMethodName(), record.getLevel().getName(),
				record.getMessage(), lineSeparate);
	}

	@Override
	public String getTail(Handler h) {
		return "";
	}

}
