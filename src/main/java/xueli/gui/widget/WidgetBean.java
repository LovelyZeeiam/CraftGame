package xueli.gui.widget;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

import javax.swing.text.SimpleAttributeSet;

import xueli.gui.GameUIContext;

public class WidgetBean {
	
	protected final GameUIContext ctx;
	
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private final SimpleAttributeSet attributes = new SimpleAttributeSet();
	
	public WidgetBean(GameUIContext ctx) {
		this.ctx = ctx;
	}
	
	public void registerPropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.addPropertyChangeListener(listener);
	}
	
	public void unregisterPropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.removePropertyChangeListener(listener);
	}
	
	public void registerPropertyChangeListener(String name, PropertyChangeListener listener) {
		this.changeSupport.addPropertyChangeListener(name, listener);
	}
	
	public void unregisterPropertyChangeListener(String name, PropertyChangeListener listener) {
		this.changeSupport.removePropertyChangeListener(name, listener);
	}
	
	public void firePropertyChange(String name, Object oldValue, Object newValue) {
		this.changeSupport.firePropertyChange(name, oldValue, newValue);
	}
	
	public final boolean hasAttribute(Object name) {
		return this.attributes.isDefined(name);
	}
	
	public final Object getAttribute(Object name) {
		return this.attributes.getAttribute(name);
	}
	
	// Return the old value if contains
	public final Object putAttribute(Object name, Object value) {
		Object oldValue = null;
		if(hasAttribute(name))
			oldValue = getAttribute(name);
		
		if(Objects.equals(value, oldValue))
			return oldValue;
		
		this.attributes.addAttribute(name, value);
		this.firePropertyChange(getAttributePropertyName(name), oldValue, value);
		return oldValue;
	}

	public final void removeAttribute(Object name) {
		this.attributes.removeAttribute(name);
	}
	
	// So please use objects that have difference "toString" result!
	public static final String getAttributePropertyName(Object name) {
		return "P:" + name.toString();
	}

}
