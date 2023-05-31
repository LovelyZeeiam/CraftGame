package xueli.swingx.responsive;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import xueli.animation.TransitionBinding;

public class TransitionBindings {

	public static TransitionBinding newBindingDimension(Component component,
			ValueProvider<Dimension, Dimension> sizeProvider) {
		return new TransitionBinding() {
			private Dimension oldSize, newSize;

			@Override
			public void animStart() {
				Container parent = component.getParent();
				Dimension parentSize = parent.getSize();

				oldSize = component.getPreferredSize();
				if (oldSize == null)
					oldSize = new Dimension();
				newSize = sizeProvider.get(parentSize);

				super.animStart();

			}

			@Override
			public void animProgress(double val) {
				component.setPreferredSize(new Dimension((int) (oldSize.width + (newSize.width - oldSize.width) * val),
						(int) (oldSize.height + (newSize.height - oldSize.height) * val)));
				// https://www.dovov.com/swing-guivalidaterevalidateinvalidate.html
				component.revalidate(); // But it can cause lag because every time it will call to layout all the
										// components

			}
		};
	}

	public static <C extends Component> TransitionBinding newBindingNumber(C component,
			ValueProvider<Dimension, Double> valueProvider, PropertyAccessible<C, Double> accessible) {
		return new TransitionBinding() {

			private double oldValue, newValue;

			@Override
			public void animStart() {
				Container parent = component.getParent();
				Dimension parentSize = parent.getSize();

				Double oldValueObj = accessible.get(component);
				if (oldValueObj == null)
					oldValue = 0;
				else
					oldValue = oldValueObj;
				newValue = valueProvider.get(parentSize);

				super.animStart();

			}

			@Override
			public void animProgress(double val) {
				accessible.set(component, (oldValue + (newValue - oldValue) * val));
			}

		};
	}

	public static <C> TransitionBinding newBindingNumber(C c, AnimationValueProvider<Double> valueProvider,
			PropertyAccessible<C, Double> accessible) {
		return new TransitionBinding() {

			private double oldValue, newValue;

			@Override
			public void animStart() {
				this.oldValue = valueProvider.getStart();
				this.newValue = valueProvider.getEnd();
				super.animStart();

			}

			@Override
			public void animProgress(double val) {
				accessible.set(c, (oldValue + (newValue - oldValue) * val));
			}

		};
	}

}
