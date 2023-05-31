package xueli.swingx.component;

import java.awt.Component;

import javax.swing.JLayeredPane;

public class FullScreenLayeredPane extends JLayeredPane {

	private static final long serialVersionUID = 2353556941953154263L;

	public FullScreenLayeredPane() {
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		Component[] cs = getComponents();
		for (int i = 0; i < cs.length; i++) {
			cs[i].setBounds(0, 0, width, height);
		}

	}

}
