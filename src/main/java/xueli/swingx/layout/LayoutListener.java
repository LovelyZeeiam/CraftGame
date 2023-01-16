package xueli.swingx.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

/**
 * The aim to write this listener is to make use of the "layoutContainer" method,
 * because when the father's "layoutContainer" is called, its size is adjusted,
 * but all of its children's size is not calculated. So to make responsive layout
 * we can slide in this golden opportunity.
 */
public abstract class LayoutListener implements LayoutManager2 {
	
	private final LayoutManager layoutManager;
	
	public LayoutListener(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		layoutManager.addLayoutComponent(name, comp);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		layoutManager.removeLayoutComponent(comp);
		
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
		this.beforeLayout();
		layoutManager.layoutContainer(parent);
		
	}
	
	protected abstract void beforeLayout();
	
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
