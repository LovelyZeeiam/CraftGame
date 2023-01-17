package xueli.swingx.responsive;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import xueli.animation.Curve;
import xueli.animation.TransitionBinding;

public class TransitionBindings {
	
	public static TransitionBinding newBindingDimension(Component component, ValueProvider<Dimension, Dimension> sizeProvider, Curve curve) {
		return new TransitionBinding(curve) {
			private Dimension oldSize, newSize;
			
			@Override
			public void animStart() {
				super.animStart();
				
				Container parent = component.getParent();
				Dimension parentSize = parent.getSize();
				
				oldSize = component.getPreferredSize();
				if(oldSize == null) oldSize = new Dimension();
				newSize = sizeProvider.get(parentSize);
				
			}
			
			@Override
			protected void progress(double val) {
				component.setPreferredSize(new Dimension(
						(int) (oldSize.width + (newSize.width - oldSize.width) * val),
						(int) (oldSize.height + (newSize.height - oldSize.height) * val)
				));
				// https://www.dovov.com/swing-guivalidaterevalidateinvalidate.html
				component.revalidate();
				
			}
		};
	}
	
	public static <C extends Component> TransitionBinding newBindingNumber(C component, ValueProvider<Dimension, Double> valueProvider, PropertyAccessible<C, Double> accessible, Curve curve) {
		return new TransitionBinding(curve) {
			
			private double oldValue, newValue;
			
			@Override
			public void animStart() {
				super.animStart();
				
				Container parent = component.getParent();
				Dimension parentSize = parent.getSize();
				
				Double oldValueObj = accessible.get(component);
				if(oldValueObj == null)
					oldValue = 0;
				else
					oldValue = oldValueObj;
				newValue = valueProvider.get(parentSize);
				
			}
			
			@Override
			protected void progress(double val) {
				accessible.set(component, (oldValue + (newValue - oldValue) * val));
			}
			
		};
	}
	
}
