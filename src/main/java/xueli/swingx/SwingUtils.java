package xueli.swingx;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class SwingUtils {

	public static void centerWindow(int width, int height, JFrame f) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
	}

}
