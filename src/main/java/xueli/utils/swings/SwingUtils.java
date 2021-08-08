package xueli.utils.swings;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {

	public static void centerWindow(int width, int height, JFrame f) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
	}

}
