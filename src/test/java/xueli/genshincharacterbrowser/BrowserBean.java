package xueli.genshincharacterbrowser;

import java.beans.JavaBean;
import javax.swing.DefaultListModel;

import xueli.swingx.PropertyListenableModel;

@JavaBean(description = "Bean of My Browser Window")
public class BrowserBean extends PropertyListenableModel {
	
	public static final String PROPERTY_DIMENSION = "d";
	public static final String PROPERTY_CHAR = "c";
	public static final String PROPERTY_LOADING = "l";
	
	private int choosenDimension = 0;
	private GenshinChar chosenChar = null;
	private boolean isLoading = false;
	private DefaultListModel<GenshinChar> charList = new DefaultListModel<>();
	
	public BrowserBean() {
	}

	public int getChoosenDimension() {
		return choosenDimension;
	}

	public void setChoosenDimension(int choosenDimension) {
		this.choosenDimension = choosenDimension;
		firePropertyChange(PROPERTY_DIMENSION);
	}

	public GenshinChar getChosenChar() {
		return chosenChar;
	}

	public void setChosenChar(GenshinChar chosenChar) {
		this.chosenChar = chosenChar;
		firePropertyChange(PROPERTY_CHAR);
	}

	public boolean isLoading() {
		return isLoading;
	}

	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
		firePropertyChange(PROPERTY_LOADING);
	}

	public DefaultListModel<GenshinChar> getCharList() {
		return charList;
	}
	
}
