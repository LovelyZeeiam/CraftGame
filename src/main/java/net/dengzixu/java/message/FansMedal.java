package net.dengzixu.java.message;

public class FansMedal {
	private int medalLevel;
	private String medalName;
	private int medalColor;
	private int medalColorStart;
	private int medalColorEnd;
	private int medalColorBorder;
	private boolean lighted;

	public int getMedalLevel() {
		return medalLevel;
	}

	public void setMedalLevel(int medalLevel) {
		this.medalLevel = medalLevel;
	}

	public String getMedalName() {
		return medalName;
	}

	public void setMedalName(String medalName) {
		this.medalName = medalName;
	}

	public int getMedalColor() {
		return medalColor;
	}

	public void setMedalColor(int medalColor) {
		this.medalColor = medalColor;
	}

	public int getMedalColorStart() {
		return medalColorStart;
	}

	public void setMedalColorStart(int medalColorStart) {
		this.medalColorStart = medalColorStart;
	}

	public int getMedalColorEnd() {
		return medalColorEnd;
	}

	public void setMedalColorEnd(int medalColorEnd) {
		this.medalColorEnd = medalColorEnd;
	}

	public int getMedalColorBorder() {
		return medalColorBorder;
	}

	public void setMedalColorBorder(int medalColorBorder) {
		this.medalColorBorder = medalColorBorder;
	}

	public boolean isLighted() {
		return lighted;
	}

	public void setLighted(boolean lighted) {
		this.lighted = lighted;
	}

	@Override
	public String toString() {
		return "fansMedal{" + "medalLevel=" + medalLevel + ", medalName='" + medalName + '\'' + ", medalColor="
				+ medalColor + ", medalColorStart=" + medalColorStart + ", medalColorEnd=" + medalColorEnd
				+ ", medalColorBorder=" + medalColorBorder + ", lighted=" + lighted + '}';
	}
}
