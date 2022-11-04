package xueli.jrich.attributes;

public class AttributeStyle implements Attribute {

	private final Style style;

	public AttributeStyle(Style s) {
		this.style = s;

	}

	@Override
	public String compile() {
		return "\033[" + style.getID() + "m";
	}

	public static enum Style {

		RESET(0),
		HIGHLIGHT(1),
		UNDERLINE(4),
		BLINK(5),
		REVERSE(7),
		BLANKING(8);

		int id;
		Style(int id) {
			this.id = id;
		}

		public int getID() {
			return id;
		}

	}

}
