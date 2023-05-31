package xueli.swingx.responsive;

import javax.swing.JLabel;

import xueli.swingx.component.SimpleLayerUI;

public interface PropertyAccessible<C, T> {

	public T get(C comp);

	public void set(C comp, T val);

	public static PropertyAccessible<JLabel, Double> fontAccessible(JLabel label) {
		return new PropertyAccessible<JLabel, Double>() {
			@Override
			public Double get(JLabel comp) {
				return (double) comp.getFont().getSize();
			}

			@Override
			public void set(JLabel comp, Double val) {
				comp.setFont(comp.getFont().deriveFont(val.floatValue()));

			}
		};
	}

//	public static PropertyAccessible<Label, Double> fontAccessible(Label label) {
//		return new PropertyAccessible<Label, Double>() {
//			@Override
//			public Double get(Label comp) {
//				return (double) comp.getFont().getSize();
//			}
//
//			@Override
//			public void set(Label comp, Double val) {
//				comp.setFont(comp.getFont().deriveFont(val.floatValue()));
//			}
//		};
//	}

	public static PropertyAccessible<SimpleLayerUI, Double> layerAlphaAccessible(SimpleLayerUI ui) {
		return new PropertyAccessible<SimpleLayerUI, Double>() {
			@Override
			public Double get(SimpleLayerUI comp) {
				return (double) comp.getAlpha();
			}

			@Override
			public void set(SimpleLayerUI comp, Double val) {
				comp.setAlpha(val.floatValue());
			}
		};
	}

	public static PropertyAccessible<SimpleLayerUI, Double> layerScaleAccessible(SimpleLayerUI ui) {
		return new PropertyAccessible<SimpleLayerUI, Double>() {
			@Override
			public Double get(SimpleLayerUI comp) {
				return (double) comp.getScale();
			}

			@Override
			public void set(SimpleLayerUI comp, Double val) {
				comp.setScale(val.floatValue());
			}
		};
	}

}
