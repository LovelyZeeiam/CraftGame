package xueli.gui.widget;

import java.beans.PropertyChangeSupport;

public class Widget {
	
	private double x = 0.0, y = 0.0;
	private double width = 0.0, height = 0.0;
	
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public Widget() {
	}

}
