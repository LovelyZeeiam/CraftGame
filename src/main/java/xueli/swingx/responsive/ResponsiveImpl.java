package xueli.swingx.responsive;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;

import xueli.swingx.layout.LayoutListener;

public class ResponsiveImpl {
	
	private static final HashMap<Container, ResponsiveImpl> responsives = new HashMap<>();
	
	public static void addResponsive(Component child, ValueProvider<Dimension> provider) {
		Container father = child.getParent();
		if(father == null) {
			System.err.println("No Father? " + child.toString());
			return;
		}
		responsives.computeIfAbsent(father, ResponsiveImpl::new)
			.addResponsiveChild(child, provider);
		
	}
	
	// The ratio should be the result of width / height
	public static ValueProvider<Dimension> newProviderRatioHeight(ValueProvider<Integer> heightProvider, double ratio) {
		return d -> {
			int height = heightProvider.get(d.height);
			int width = (int) (height * ratio);
			return new Dimension(width, height);
		};
	}
	
	// The ratio should be the result of width / height
	public static ValueProvider<Dimension> newProviderRatioWidth(ValueProvider<Integer> widthProvider, double ratio) {
		return d -> {
			int width = widthProvider.get(d.height);
			int height = (int) (width / ratio);
			return new Dimension(width, height);
		};
	}
	
	// The ratio should be the result of width / height
	public static ValueProvider<Dimension> newProviderRatioVMin(ValueProvider<Integer> minProvider, double ratio) {
		return d -> {
			int childWidth, childHeight;
			if(d.width >= d.height) {
				childHeight = minProvider.get(d.height);
				childWidth = (int) (childHeight * ratio);
			} else {
				childWidth = minProvider.get(d.width);
				childHeight = (int) (childWidth / ratio);
			}
			return new Dimension(childWidth, childHeight);
		};
	}
	
	// The ratio should be the result of width / height
	public static ValueProvider<Dimension> newProviderRatioVMax(ValueProvider<Integer> maxProvider, double ratio) {
		return d -> {
			int childWidth, childHeight;
			if(d.width <= d.height) {
				childHeight = maxProvider.get(d.height);
				childWidth = (int) (childHeight * ratio);
			} else {
				childWidth = maxProvider.get(d.width);
				childHeight = (int) (childWidth / ratio);
			}
			return new Dimension(childWidth, childHeight);
		};
	}
	
	private final HashMap<Component, ValueProvider<Dimension>> responsiveData = new HashMap<>();
	
	ResponsiveImpl(Container father) {
		father.setLayout(new LayoutListener(father.getLayout()) {
			@Override
			protected void beforeLayout() {
				Dimension fatherSize = father.getSize();
				responsiveData.forEach((c, v) -> {
					c.setPreferredSize(v.get(fatherSize));
				});
			}
		});
	}
	
	public ResponsiveImpl addResponsiveChild(Component child, ValueProvider<Dimension> provider) {
		responsiveData.put(child, provider);
		return this;
	}
	
}
