package xueli.linotebook.noteeditor;

import xueli.swingx.PropertyListenableModel;

public class NoteOptions extends PropertyListenableModel {
	
	public static final String PROPERTY_FULL_WIDTH = "0";
	
	private boolean fullWidth = false;
	
	public NoteOptions() {
	}
	
	public boolean isFullWidth() {
		return fullWidth;
	}
	
	public void setFullWidth(boolean fullWidth) {
		this.fullWidth = fullWidth;
		firePropertyChange(PROPERTY_FULL_WIDTH);
	}
	
}
