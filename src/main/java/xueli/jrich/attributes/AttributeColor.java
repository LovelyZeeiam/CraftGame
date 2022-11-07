package xueli.jrich.attributes;

@Deprecated
public class AttributeColor implements Attribute {

	private ColorType type;

	private final Mode mode;
	private float r, g, b;
	private BuiltInColor builtinColor;
	private int colorId;

	public AttributeColor(float r, float g, float b, ColorType type) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.type = type;
		this.mode = Mode.TWENTY_FOUR_COLOR;

	}

	public AttributeColor(int colorId, ColorType type) {
		this.colorId = colorId;
		this.type = type;
		this.mode = Mode.EIGHT_BIT_COLOR;

	}

	public AttributeColor(BuiltInColor colorId, ColorType type) {
		this.builtinColor = colorId;
		this.type = type;
		this.mode = Mode.BUILTIN;

	}

	@Override
	public String compile() {
		StringBuilder builder = new StringBuilder();
		builder.append((char) 27).append("[");

		switch (mode) {
			case BUILTIN -> {
				// eg. 34
				int baseNum = this.type == ColorType.FOREGROUND ? 30: 40;
				builder.append(baseNum + builtinColor.getColorId());

			}
			case EIGHT_BIT_COLOR -> {
				// eg. 38;5;0
				int baseNum = this.type == ColorType.FOREGROUND ? 38: 48;
				builder.append(baseNum).append(";").append(2);
				builder.append(colorId);

			}
			case TWENTY_FOUR_COLOR -> {
				// eg. 38;2;255;0;255
				int baseNum = this.type == ColorType.FOREGROUND ? 38: 48;
				builder.append(baseNum).append(";").append(5);

				builder.append((int) (r * 255)).append(";")
					.append((int) (g * 255)).append(";")
					.append((int) (b * 255));

			}
		}

		builder.append("m");
		return builder.toString();
	}

	public static enum ColorType {
		FOREGROUND, BACKGROUND;
	}

	public static enum BuiltInColor {

		BLACK(0),
		RED(1),
		GREEN(2),
		YELLOW(3),
		BLUE(4),
		PINK(5),
		CYAN(6),
		WHITE(7),
		DEFAULT(9);

		int colorId;
		BuiltInColor(int colorId) {
			this.colorId = colorId;
		}

		public int getColorId() {
			return colorId;
		}

	}

	private static enum Mode {
		BUILTIN, EIGHT_BIT_COLOR, TWENTY_FOUR_COLOR;
	}

}
