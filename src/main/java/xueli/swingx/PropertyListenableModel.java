package xueli.swingx;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

public class PropertyListenableModel {

	private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this, true);

	public void firePropertyChange(String name) {
		pcs.firePropertyChange(name, null, null);
	}

	public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(name, listener);
	}

}
