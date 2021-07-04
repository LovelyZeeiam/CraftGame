package xueli.utils.console;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConsoleFormatter {

	public static String roundScale(double d, int scale) {
		return new BigDecimal(d).setScale(scale, RoundingMode.DOWN).toString();
	}

}
