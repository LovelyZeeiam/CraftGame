package xueli.utils.exception;

import javax.swing.JOptionPane;

public class CrashReport {

	private static final String NICE_COMMENT[] = { "AbaAbaAba", "Why did things go like this?",
			"But it works on my computer /(ToT)/~~", "ElectroBoooooom!", "Also try Marshmello!", "Go get xueli!",
			"What bad ideas could xueli be thinking of?" };

	private String state;
	private Exception e;

	public CrashReport(String state, Exception e) {
		this.e = e;
		this.state = state;
	}

	public void showCrashReport() {
		JOptionPane.showMessageDialog(null, getMessage(), getNiceComment(), JOptionPane.ERROR_MESSAGE);
	}

	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		Throwable t = e;

		builder.append("Exception");
		while (true) {
			builder.append(t.getClass().getName());
			if (t.getMessage() != null && !t.getMessage().isBlank())
				builder.append(": ").append(t.getMessage());

			StackTraceElement[] es = t.getStackTrace();
			for (int i = 0; i < Math.min(es.length, 4); i++) {
				StackTraceElement element = es[i];
				builder.append(System.lineSeparator());
				builder.append("    ").append(element.getClassName()).append(":").append(element.getMethodName())
						.append("(").append(element.getFileName()).append(":").append(element.getLineNumber())
						.append(")");
			}

			builder.append(System.lineSeparator());

			t = e.getCause();
			if (t == null)
				break;

			builder.append("Caused by");

		}

		return builder.toString();
	}

	public String getNiceComment() {
		return NICE_COMMENT[(int) (System.currentTimeMillis() % NICE_COMMENT.length)];
	}

}
