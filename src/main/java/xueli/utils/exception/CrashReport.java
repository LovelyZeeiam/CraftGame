package xueli.utils.exception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CrashReport {

	private static final String NICE_COMMENT[] = { "AbaAbaAba", "Why did things go like this?",
			"But it works on my computer /(ToT)/~~", "ElectroBoooooom!", "Also try Marshmello!", "Go get xueli!",
			"What bad ideas could xueli be thinking of?" };

	private String state;
	private Throwable e;

	public CrashReport(String state, Throwable e) {
		this.e = e;
		this.state = state;
	}

	public void showCrashReport() {
		new Thread(() -> {
			e.printStackTrace();
			this.showErrorDialog(getNiceComment(), getMessage());
		}).start();

	}

	private void showErrorDialog(String title, String message) {
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
		frame.dispose();

	}

	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		Throwable t = e;

		builder.append("[" + this.state + "] Exception ");
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

			t = t.getCause();
			if (t == null)
				break;

			builder.append("Caused by ");

		}

		return builder.toString();
	}

	public String getNiceComment() {
		return NICE_COMMENT[(int) (System.currentTimeMillis() % NICE_COMMENT.length)];
	}

}
