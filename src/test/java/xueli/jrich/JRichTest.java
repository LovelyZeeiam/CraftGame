package xueli.jrich;

import xueli.jrich.attributes.AttributeStyle;

public class JRichTest {

	public static void main(String[] args) {
		ConsoleFlowAppendable f = new ConsoleFlowAppendable(40);
		f.print("adsada ".repeat(20));
		f.addAttribute(new AttributeStyle(AttributeStyle.Style.REVERSE));
		f.print("adsada ".repeat(20));
		f.addAttribute(new AttributeStyle(AttributeStyle.Style.RESET));

		ConsoleBuffer f2 = new ConsoleBuffer();
		f2.writeBlock(f, 20, 2, 40, 20);
		f2.writeBlock(f, 1, 0, 30, 10);

		f2.render(80, Integer.MAX_VALUE);

	}

}
