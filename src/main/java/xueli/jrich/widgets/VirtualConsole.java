package xueli.jrich.widgets;

public class VirtualConsole {

	private final int width;
	private int pX = 0, pY = 0;

	public VirtualConsole(int width) {
		this.width = width;

	}

	public boolean printChar() {
		pX++;
		if(pX >= width) {
			newLine();
			return true;
		}
		return false;
	}

	public void newLine() {
		pX = 0;
		pY++;
	}

	public int getX() {
		return pX;
	}

	public int getY() {
		return pY;
	}

}
