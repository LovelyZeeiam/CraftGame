package xueli.jrich;

import org.fusesource.jansi.Ansi;

public class JRichTest {

	public static void main(String[] args) {
		ConsoleFlowAppendable f = new ConsoleFlowAppendable(40);
		f.print("adsada ".repeat(20));
		f.setAttribute(new Attribute() {
			@Override
			public String compile() {
				return Ansi.ansi().fgBlue().toString();
			}
		});
		f.print("adsada ".repeat(20));
		f.setAttribute(new Attribute() {
			@Override
			public String compile() {
				return Ansi.ansi().reset().toString();
			}
		});
		f.render();

	}

}
