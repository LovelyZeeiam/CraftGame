package xueli.swingx.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;

import xueli.swingx.responsive.ValueProvider;

/**
 * This layout places all the component in a line horizontally with
 * many mode to select.
 * 
 */
public class VerticalFilledLayout implements LayoutManager2 {
	
	private final HorizontalAlign horizontalAlign;
	private final VerticalAlign verticalAlign;
	
	private final HashMap<Component, ValueProvider<Integer, Integer>> componentLayoutConstraintMap = new HashMap<>();
	
	public VerticalFilledLayout(HorizontalAlign horizentalAlign, VerticalAlign verticalAlign) {
		this.horizontalAlign = horizentalAlign;
		this.verticalAlign = verticalAlign;
		
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints instanceof Number n) {
			componentLayoutConstraintMap.put(comp, w -> n.intValue());
		} else if(constraints instanceof String str) {
			componentLayoutConstraintMap.put(comp, ValueProvider.parseInteger(str));
		}
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		componentLayoutConstraintMap.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int preferredHeight = 0, preferredWidth = 0;
		synchronized (parent.getTreeLock()) {
			Component[] components = parent.getComponents();
			for(int i = 0; i < components.length; i++) {
				Component child = components[i];
				if(!child.isVisible()) continue;
				Dimension childPreferredSize = child.getPreferredSize();
				preferredWidth = Math.max(preferredWidth, childPreferredSize.width);
				preferredHeight += childPreferredSize.height;
			}
		}
		return new Dimension(preferredWidth, preferredHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}
	
	private static record LayoutResult(int relativeX, int relativeY, int width, int height) {}
	
	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			int parentWidth = parent.getWidth();
			int parentHeight = parent.getHeight();
			
			Component[] components = parent.getComponents();
			LayoutResult[] layoutResults = new LayoutResult[components.length];
			
			// To make horizental align easy, we first pretend to combine the components to one panel.
			// Calculate its relative layout to this "existed" panel.
			// After the loop, this variable will be the sum width of all components
			int pointerY = 0;
			
			for(int i = 0; i < components.length; i++) {
				Component child = components[i];
				if(!child.isVisible()) continue;
				var heightProvider = componentLayoutConstraintMap.get(child);
				Dimension childPreferredSize = child.getPreferredSize();
				
				int childHeight;
				if(heightProvider == null) {
					childHeight = childPreferredSize.height;
				} else {
					childHeight = heightProvider.get(parentWidth);
				}
				
				int childX = 0;
				int childWidth = 0;
				
				switch (this.horizontalAlign) {
				case ALIGN_LEFT -> {
					childWidth = childPreferredSize.width;
					childX = 0;
				}
				case ALIGN_CENTER -> {
					childWidth = childPreferredSize.width;
					childX = (parentWidth - childWidth) / 2;
				}
				case ALIGN_RIGHT -> {
					childWidth = childPreferredSize.width;
					childX = parentWidth - childWidth;
				}
				case FILL -> {
					childWidth = parentWidth;
					childX = 0;
				}
				}
				
				layoutResults[i] = new LayoutResult(childX, pointerY, childWidth, childHeight);
				pointerY += childHeight;
			}
			
			int layoutStartY = switch (this.verticalAlign) {
			case ALIGN_TOP -> 0;
			case CENTER -> (parentHeight - pointerY) / 2;
			case ALIGN_BOTTOM -> parentHeight - pointerY;
			};
			
			for(int i = 0; i < components.length; i++) {
				Component child = components[i];
				LayoutResult layoutResult = layoutResults[i];
				if(layoutResult == null) continue;
				child.setBounds(layoutResult.relativeX, layoutStartY + layoutResult.relativeY, layoutResult.width, layoutResult.height);
			}
			
		}
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return null;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}
	
	@Override
	public void invalidateLayout(Container target) {
	}
	
	public static enum VerticalAlign {
		ALIGN_TOP, CENTER, ALIGN_BOTTOM,
	}
	
	public static enum HorizontalAlign {
		ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT, FILL,
	}
	
}
