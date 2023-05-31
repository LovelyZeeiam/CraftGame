package xueli.swingx.responsive;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;

import xueli.swingx.layout.LayoutListener;

@Deprecated
public class Responsive {

	private static final HashMap<Container, Responsive> responsives = new HashMap<>();

	public static void addResponsive(Component child, ValueProvider<Dimension, Dimension> provider) {
		Container father = child.getParent();
		if (father == null) {
			System.err.println("No Father? " + child.toString());
			return;
		}
		responsives.computeIfAbsent(father, Responsive::new).addResponsiveChild(child, provider);

	}

	private final HashMap<Component, ValueProvider<Dimension, Dimension>> responsiveData = new HashMap<>();

	Responsive(Container father) {
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

	public Responsive addResponsiveChild(Component child, ValueProvider<Dimension, Dimension> provider) {
		responsiveData.put(child, provider);
		return this;
	}

}
