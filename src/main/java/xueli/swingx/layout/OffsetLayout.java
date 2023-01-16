package xueli.swingx.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.util.HashMap;

public class OffsetLayout implements LayoutManager2 {
	
	private final LayoutManager layoutManager;
	
	private final HashMap<Component, Point> originPositions = new HashMap<>();
	private final HashMap<Component, Point> offsets = new HashMap<>();
	
	public OffsetLayout(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		layoutManager.addLayoutComponent(name, comp);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		layoutManager.removeLayoutComponent(comp);
		
		originPositions.remove(comp);
		offsets.remove(comp);
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutManager.preferredLayoutSize(parent);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutManager.minimumLayoutSize(parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		layoutManager.layoutContainer(parent);
		
		synchronized (parent.getTreeLock()) {
			Component[] cs = parent.getComponents();
			for(int i = 0; i < cs.length; i++) {
				Component c = cs[i];
				Point originLocation = c.getLocation();
				originPositions.put(c, originLocation);
				
				Point offset = offsets.get(c);
				if(offset != null) {
					c.setLocation(originLocation.x + offset.x, originLocation.y + offset.y);
				}
				
			}
		}
		
	}
	
	public void setOffset(Component c, Point offset) {
		if(offset == null) offset = new Point(0, 0);
		this.setOffset(c, offset.x, offset.y);
	}
	
	public void setOffset(Component c, int x, int y) {
		offsets.put(c, new Point(x, y));
		Point originLocation = originPositions.get(c);
		if(originLocation != null) {
			c.setLocation(originLocation.x + x, originLocation.y + y);
		}
	}
	
	public Point getOffset(Component c) {
		return offsets.get(c);
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(layoutManager instanceof LayoutManager2 lm2) {
			lm2.addLayoutComponent(comp, constraints);
		}
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		if(layoutManager instanceof LayoutManager2 lm2) {
			lm2.maximumLayoutSize(target);
		}
		return null;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		if(layoutManager instanceof LayoutManager2 lm2) {
			lm2.getLayoutAlignmentX(target);
		}
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		if(layoutManager instanceof LayoutManager2 lm2) {
			lm2.getLayoutAlignmentY(target);
		}
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		if(layoutManager instanceof LayoutManager2 lm2) {
			lm2.invalidateLayout(target);
		}
	}

}
