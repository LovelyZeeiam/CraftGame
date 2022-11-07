package xueli.jrich;

import xueli.jrich.attributes.Attribute;

@Deprecated
public class ConsoleFlowAppendable extends ConsoleBuffer implements ConsoleAppendable {

	private final int consoleWidth;

	private int pX = 0, pY = 0;

	public ConsoleFlowAppendable(int width) {
		this.consoleWidth = width;

	}

	@Override
	public void addAttribute(Attribute a) {
		this.addAsciiSymbol(pX, pY, a.compile());

	}

	@Override
	public void print(char c) {
		this.writeChar(c, pX, pY);
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
		super.render(this.consoleWidth, Integer.MAX_VALUE);

	}
	
	public int getpX() {
		return pX;
	}
	
	public int getpY() {
		return pY;
	}

}
