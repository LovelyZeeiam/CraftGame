package xueli.jrich;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class ConsoleBuffer {

	private HashMap<ConsolePosition, ArrayList<String>> asciiControlSymbols = new HashMap<>();
	private final ArrayList<StringBuffer> buffer = new ArrayList<>();

	public ConsoleBuffer() {
	}

	public void writeBlock(ConsoleBuffer r, int x, int y, int width, int height) {
		HashMap<ConsolePosition, ArrayList<String>> symbols = r.asciiControlSymbols;
		symbols.forEach((p, l) -> this.getAsciiSymbols(x + p.x(), y + p.y()).addAll(l));

		ArrayList<StringBuffer> src = r.buffer;
		int lineCount = 0;
		boolean overflow = false;
		if(height >= src.size()) {
			lineCount = src.size();
		} else {
			lineCount = height;
			overflow = true;
		}

		for (int i = 0; i < lineCount; i++) {
			StringBuffer line = src.get(i);
			this.writeLine(line, x, y + i, width);
		}

		if(overflow) {
			this.writeLine("...", x + width - 3, y + height - 1, 3);
		}

	}

	public void writeLine(CharSequence c, int x, int y, int width) {
		this.fillToLine(y);
		StringBuffer strBuf = buffer.get(y);
		this.fillToCharInLine(x, strBuf);

		String str = c.toString();
		str = str.substring(0, Math.min(str.length(), width));
		strBuf.replace(x, x + Math.min(width, c.length()), str);

	}

	public void writeChar(char c, int x, int y) {
		this.fillToLine(y);
		StringBuffer strBuf = buffer.get(y);
		this.fillToCharInLine(x, strBuf);
		strBuf.setCharAt(x, c);

	}

	private void fillToLine(int needY) {
		int bufferSize = buffer.size();
		if(needY >= bufferSize) {
			int more = needY + 1 - bufferSize;
			for (int i = 0; i < more; i++) {
				buffer.add(new StringBuffer());
			}
		}
	}

	private void fillToCharInLine(int needX, StringBuffer buf) {
		int strLength = buf.length();
		if(needX >= strLength) {
			int more = needX + 1 - strLength;
			buf.append(" ".repeat(more));
		}

	}

	private List<String> getAsciiSymbols(int x, int y) {
		return asciiControlSymbols.computeIfAbsent(new ConsolePosition(x, y), k -> new ArrayList<>());
	}

	public void addAsciiSymbol(int x, int y, String symbol) {
		getAsciiSymbols(x, y).add(symbol);
	}

	void render(int width, int height) {
//		asciiControlSymbols.keySet().forEach(p -> System.out.println(p));
		
		PrintStream out = System.out;

		int printHeight = Math.min(height, buffer.size());
		for (int i = 0; i < printHeight; i++) {
			StringBuffer line = buffer.get(i);
			char[] cs = new char[line.length()];
			line.getChars(0, line.length(), cs, 0);

			for (int j = 0; j < cs.length; j++) {
				if(j >= width) break;

				ArrayList<String> symbols = asciiControlSymbols.get(new ConsolePosition(j, i));
				if(symbols != null) {
					symbols.forEach(out::print);
//					System.out.print("@");
				}

				out.print(cs[j]);

			}

			out.println();

		}

	}

}
