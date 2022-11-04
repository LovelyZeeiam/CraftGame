package xueli.jrich;

import org.fusesource.jansi.Ansi;

import xueli.jrich.attributes.AttributeColor;
import xueli.jrich.attributes.AttributeColor.BuiltInColor;
import xueli.jrich.attributes.AttributeColor.ColorType;

public class JRichTest {

	public static void main(String[] args) {
		ConsoleFlowAppendable f = new ConsoleFlowAppendable(40);
		
		f.addAttribute(new AttributeColor(BuiltInColor.YELLOW, ColorType.FOREGROUND));
		f.print("fff ".repeat(20));
		f.addAttribute(new AttributeColor(BuiltInColor.DEFAULT, ColorType.FOREGROUND));
		
//		System.out.println(f.getpX() + ", " + f.getpY());
		f.addAttribute(new AttributeColor(BuiltInColor.BLUE, ColorType.BACKGROUND));
//		System.out.println(f.getpX() + ", " + f.getpY());
		f.print("adsada ".repeat(20));
		f.addAttribute(new AttributeColor(BuiltInColor.DEFAULT, ColorType.BACKGROUND));
		f.render();
		
		ConsoleBuffer f2 = new ConsoleBuffer();
		f2.writeBlock(f, 20, 2, 40, 20);
		f2.render(80, Integer.MAX_VALUE);
		
		
	}

}
