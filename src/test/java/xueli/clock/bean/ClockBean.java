package xueli.clock.bean;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

public class ClockBean {

	public static final String PROPERTY_TIME = "1";
	public static final String PROPERTY_DATE = "2";
	public static final String PROPERTY_SHOW_FUNATIONAL_PANEL = "3";

	private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this, true);

	private String timeStr, dateStr;
	private boolean showFunctionalPanel = false;

	public ClockBean() {

	}

	public void setTimeString(String newTimeStr) {
		this.pcs.firePropertyChange(PROPERTY_TIME, this.timeStr, newTimeStr);
		this.timeStr = newTimeStr;
	}

	public String getTimeString() {
		return timeStr;
	}

	public void setDateString(String newDateStr) {
		this.pcs.firePropertyChange(PROPERTY_DATE, this.dateStr, newDateStr);
		this.dateStr = newDateStr;
	}

	public String getDateString() {
		return dateStr;
	}

	public void setShowFunctionalPanel(boolean showFunctionalPanel) {
		this.pcs.firePropertyChange(PROPERTY_SHOW_FUNATIONAL_PANEL, this.showFunctionalPanel, showFunctionalPanel);
		this.showFunctionalPanel = showFunctionalPanel;
	}

	public boolean isShowFunctionalPanel() {
		return showFunctionalPanel;
	}

	public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(name, listener);
	}

}
