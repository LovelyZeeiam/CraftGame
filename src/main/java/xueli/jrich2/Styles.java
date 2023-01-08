package xueli.jrich2;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Attribute;

public class Styles {
	
	Color fgColor = null, bgColor = null;
	boolean bold = false, italic = false, negative = false, strikethrough = false;
	Blink blink = null;
	Underline underline = null;
	
	public Styles() {}
	
	public Styles fgColor(Color fgColor) {
		this.fgColor = fgColor;
		return this;
	}

	public Styles bgColor(Color bgColor) {
		this.bgColor = bgColor;
		return this;
	}

	public Styles bold() {
		this.bold = true;
		return this;
	}

	public Styles italic() {
		this.italic = true;
		return this;
	}

	public Styles negative() {
		this.negative = true;
		return this;
	}

	public Styles strikethrough() {
		this.strikethrough = true;
		return this;
	}

	public Styles blink(Blink blink) {
		this.blink = blink;
		return this;
	}

	public Styles underline(Underline underline) {
		this.underline = underline;
		return this;
	}
	
	public static void compile(Styles lastStyles, Styles thisStyles, Ansi buf) {
		if(thisStyles == null) {
			if(lastStyles != null)
				buf.reset();
			return;
		}
		
		if(lastStyles == null)
			lastStyles = new Styles();
		
		{
			Color thisBgColor = null;
			if(!lastStyles.bgColor.equals((thisBgColor = thisStyles.bgColor))) {
				if(thisBgColor == null) {
					buf.bg(Ansi.Color.DEFAULT);
				} else {
					buf.bgRgb(thisBgColor.getR(), thisBgColor.getG(), thisBgColor.getB());
				}
			}
		}
		{
			Color thisFgColor = null;
			if(!lastStyles.fgColor.equals((thisFgColor = thisStyles.fgColor))) {
				if(thisFgColor == null) {
					buf.bg(Ansi.Color.DEFAULT);
				} else {
					buf.bgRgb(thisFgColor.getR(), thisFgColor.getG(), thisFgColor.getB());
				}
			}
		}
		{
			boolean bold;
			if(lastStyles.bold != (bold = thisStyles.bold)) {
				if(bold) {
					buf.a(Attribute.INTENSITY_BOLD);
				} else {
					buf.a(Attribute.INTENSITY_BOLD_OFF);
				}
			}
		}
		{
			boolean negative;
			if(lastStyles.negative != (negative = thisStyles.negative)) {
				if(negative) {
					buf.a(Attribute.NEGATIVE_ON);
				} else {
					buf.a(Attribute.NEGATIVE_OFF);
				}
			}
		}
		{
			boolean strikethrough;
			if(lastStyles.strikethrough != (strikethrough = thisStyles.strikethrough)) {
				if(strikethrough) {
					buf.a(Attribute.STRIKETHROUGH_ON);
				} else {
					buf.a(Attribute.STRIKETHROUGH_OFF);
				}
			}
		}
		{
			Blink blink;
			if(lastStyles.blink != (blink = thisStyles.blink)) {
				switch (blink) {
				case BLINK_SLOW -> buf.a(Attribute.BLINK_SLOW);
				case BLINK_FAST -> buf.a(Attribute.BLINK_FAST);
				default -> buf.a(Attribute.BLINK_OFF);
				}
			}
		}
		{
			Underline underline;
			if(lastStyles.underline != (underline = thisStyles.underline)) {
				switch (underline) {
				case UNDERLINE -> buf.a(Attribute.UNDERLINE);
				case UNDERLINE_DOUBLE -> buf.a(Attribute.UNDERLINE_DOUBLE);
				default -> buf.a(Attribute.UNDERLINE_OFF);
				}
			}
		}
		
	}

	public static enum Blink {
		BLINK_SLOW, BLINK_FAST
	}
	
	public static enum Underline {
		UNDERLINE, UNDERLINE_DOUBLE
	}
	
}
