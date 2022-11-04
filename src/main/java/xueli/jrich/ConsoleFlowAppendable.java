package xueli.jrich;

public class ConsoleFlowAppendable implements ConsoleAppendable {

	private final ConsoleBuffer buf = new ConsoleBuffer();
	private final int consoleWidth;

	private int pX = 0, pY = 0;

	public ConsoleFlowAppendable(int width) {
		this.consoleWidth = width;

	}

	@Override
	public void setAttribute(Attribute a) {
		buf.addAsciiSymbol(pX + 1, pY, a.compile());

	}

	@Override
	public void print(char c) {
		buf.writeChar(c, pX, pY);
		pX++;

		if(pX >= consoleWidth) {
			newLine();
		}

	}

	@Override
	public void newLine() {
		pX = 0;
		pY++;

	}

	@Override
	public void render() {
		buf.render(this.consoleWidth, Integer.MAX_VALUE);

	}

}
