package xueli.jrich;

import xueli.jrich.widgets.ConsoleText;
import xueli.jrich.widgets.LeftRightBlocks;

public class JRichTest {

	public static void main(String[] args) {
		JRichConsole console = new JRichConsole();
		console.render(new LeftRightBlocks(
				10,
				new ConsoleText("Fuck".repeat(10)),
				new ConsoleText("Shit".repeat(100))
		));


	}

}
