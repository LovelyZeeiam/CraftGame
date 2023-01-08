package xueli.jrich2;

public class Character {
	
	public final char ch;
	public final Styles styles;
	
	public Character(char ch) {
		this.ch = ch;
		this.styles = null;
	}
	
	public Character(char ch, Styles styles) {
		this.ch = ch;
		this.styles = styles;
	}
	
}
