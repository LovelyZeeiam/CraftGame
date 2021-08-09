package xueli.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import xueli.utils.collection.Table;

public class Threads {

	private static final int DUMP_BEFORE_NUM = 5;

	public static void dumpAllThreads(File f) throws IOException {
		Map<Thread, StackTraceElement[]> m = Thread.getAllStackTraces();
		Table<String> table = new Table<>();

		table.put(0, 0, "Name");
		table.put(1, 0, "State");
		table.put(2, 0, "StackTrace");

		int count = 1;
		for (Entry<Thread, StackTraceElement[]> e : m.entrySet()) {
			Thread t = e.getKey();
			StackTraceElement[] es = e.getValue();

			StringBuilder stackTraceBuilder = new StringBuilder();
			for (int i = 0; i < Math.min(DUMP_BEFORE_NUM, es.length); i++) {
				StackTraceElement stackTraceElement = es[i];
				stackTraceBuilder.append(stackTraceElement.getClassName()).append(".")
						.append(stackTraceElement.getMethodName()).append("(").append(stackTraceElement.getFileName())
						.append(":").append(stackTraceElement.getLineNumber()).append(")");
				stackTraceBuilder.append(System.lineSeparator());
			}

			String traces = stackTraceBuilder.substring(0, Math.max(0, stackTraceBuilder.length() - 1));
			table.put(0, count, t.getName());
			table.put(1, count, t.getState().toString());
			table.put(2, count, traces);

			count++;

		}

		System.out.println(table);

	}

	public static void main(String[] args) throws IOException {
		dumpAllThreads(new File("E:\\videos\\myliverecord\\recordings\\a.xls"));

	}

}
