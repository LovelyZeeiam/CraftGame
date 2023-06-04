package xueli.awacoder.bean;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

public class AwaCoderBean {

	private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this, true);

	public AwaCoderBean() {
	}

	public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(name, listener);
	}

}
