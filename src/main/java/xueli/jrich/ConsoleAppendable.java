package xueli.jrich;

public interface ConsoleAppendable {

	public void setAttribute(Attribute a);
	public void print(char c);
	public void newLine();

	public void render();

	default public void print(CharSequence s) {
		int length = s.length();
		for (int i = 0; i < length; i++) {
			this.print(s.charAt(i));
		}

	}

	default public void println(CharSequence s) {
		print(s);
		newLine();
	}

}
