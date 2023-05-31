package xueli.swingx.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;

import xueli.swingx.responsive.ValueProvider;

/**
 * This layout places all the component in a line horizontally with many mode to
 * select.
 * 
 */
public class HorizontalFilledLayout implements LayoutManager2 {

	private final VerticalAlign verticalAlign;
	private final HorizontalAlign horizentalAlign;

	private final HashMap<Component, ValueProvider<Integer, Integer>> componentLayoutConstraintMap = new HashMap<>();

	public HorizontalFilledLayout(VerticalAlign verticalAlign, HorizontalAlign horizentalAlign) {
		this.verticalAlign = verticalAlign;
		this.horizentalAlign = horizentalAlign;

	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof Number n) {
			componentLayoutConstraintMap.put(comp, w -> n.intValue());
		} else if (constraints instanceof String str) {
			componentLayoutConstraintMap.put(comp, ValueProvider.parseInteger(str));
		}
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		componentLayoutConstraintMap.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int preferredWidth = 0, preferredHeight = 0;
		synchronized (parent.getTreeLock()) {
			Component[] components = parent.getComponents();
			for (int i = 0; i < components.length; i++) {
				Component child = components[i];
				if (!child.isVisible())
					continue;
				Dimension childPreferredSize = child.getPreferredSize();
				preferredHeight = Math.max(preferredHeight, childPreferredSize.height);
				preferredWidth += childPreferredSize.width;
			}
		}
		return new Dimension(preferredWidth, preferredHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	private static record LayoutResult(int relativeX, int relativeY, int width, int height) {
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			int parentWidth = parent.getWidth();
			int parentHeight = parent.getHeight();

			Component[] components = parent.getComponents();
			LayoutResult[] layoutResults = new LayoutResult[components.length];

			// To make horizental align easy, we first pretend to combine the components to
			// one panel.
			// Calculate its relative layout to this "existed" panel.
			// After the loop, this variable will be the sum width of all components
			int pointerX = 0;

			for (int i = 0; i < components.length; i++) {
				Component child = components[i];
				if (!child.isVisible())
					continue;

				var widthProvider = componentLayoutConstraintMap.get(child);
				Dimension childPreferredSize = child.getPreferredSize();

				int childWidth;
				if (widthProvider == null) {
					childWidth = childPreferredSize.width;
				} else {
					childWidth = widthProvider.get(parentWidth);
				}

				int childY = 0;
				int childHeight = 0;

				switch (this.verticalAlign) {
				case ALIGN_TOP -> {
					childHeight = childPreferredSize.height;
					childY = 0;
				}
				case ALIGN_CENTER -> {
					childHeight = childPreferredSize.height;
					childY = (parentHeight - childHeight) / 2;
				}
				case ALIGN_BOTTOM -> {
					childHeight = childPreferredSize.height;
					childY = parentHeight - childHeight;
				}
				case FILL -> {
					childHeight = parentHeight;
					childY = 0;
				}
				}

				layoutResults[i] = new LayoutResult(pointerX, childY, childWidth, childHeight);
				pointerX += childWidth;
			}

			int layoutStartX = switch (this.horizentalAlign) {
			case ALIGN_LEFT -> 0;
			case CENTER -> (parentWidth - pointerX) / 2;
			case ALIGN_RIGHT -> parentWidth - pointerX;
			};

			for (int i = 0; i < components.length; i++) {
				Component child = components[i];
				LayoutResult layoutResult = layoutResults[i];
				if (layoutResult == null)
					continue;
				child.setBounds(layoutStartX + layoutResult.relativeX, layoutResult.relativeY, layoutResult.width,
						layoutResult.height);
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

//	int i = 0;
	@Override
	public void invalidateLayout(Container target) {
//		System.out.println("[INVALIDATE] " + (i++));
//		this.layoutContainer(target);
	}

	public static enum HorizontalAlign {
		ALIGN_LEFT, CENTER, ALIGN_RIGHT,
	}

	public static enum VerticalAlign {
		ALIGN_TOP, ALIGN_CENTER, ALIGN_BOTTOM, FILL,
	}

}
