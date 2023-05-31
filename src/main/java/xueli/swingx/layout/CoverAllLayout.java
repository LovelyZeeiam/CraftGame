package xueli.swingx.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class CoverAllLayout implements LayoutManager {

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(0, 0);
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			int parentWidth = parent.getWidth();
			int parentHeight = parent.getHeight();
			Component[] cs = parent.getComponents();
			for (int i = 0; i < cs.length; i++) {
				Component c = cs[i];
				c.setBounds(0, 0, parentWidth, parentHeight);
			}
		}

	}

}
