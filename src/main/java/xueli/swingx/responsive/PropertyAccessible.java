package xueli.swingx.responsive;

import java.awt.Component;

import javax.swing.JLabel;

public interface PropertyAccessible<C extends Component, T> {

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
	
}
