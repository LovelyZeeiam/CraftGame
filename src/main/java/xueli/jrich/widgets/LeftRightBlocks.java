package xueli.jrich.widgets;

import org.fusesource.jansi.Ansi;
import xueli.jrich.ConsoleRenderable;

public class LeftRightBlocks implements ConsoleRenderable {

	private final int leftBlockWidth;
	private final int marginBetween = 1;
	private final ConsoleRenderable left, right;

	public LeftRightBlocks(int leftBlockWidth, ConsoleRenderable left, ConsoleRenderable right) {
		this.leftBlockWidth = leftBlockWidth;
		this.left = left;
		this.right = right;

	}

	@Override
	public void render(int width, int height, Ansi buf) {
		Ansi leftBuffer = Ansi.ansi();
		Ansi rightBuffer = Ansi.ansi();

		int leftWidth = this.leftBlockWidth;
		int rightWidth = width - marginBetween - leftWidth;

		left.render(leftWidth, height, leftBuffer);
		right.render(rightWidth, height, rightBuffer);

		String leftStr = leftBuffer.toString();
		String rightStr = rightBuffer.toString();

		buf.cursorRight(leftWidth + marginBetween);
		buf.saveCursorPosition();

		buf.cursorLeft(leftWidth + marginBetween);
		buf.a(leftStr);
		buf.restoreCursorPosition();

		rightStr.lines().forEach(line -> {
			buf.a(line);
			buf.newline().cursorRight(leftWidth + marginBetween);
		});


	}

}
