package xueli.gui.widget;

import java.awt.Color;

import xueli.gui.UIContext;
import xueli.gui.Widget;

public class Rectangle extends Widget {
	
//	public static final Identifier IDENTIFIER = new Identifier(MyUIFrameworkInfo.FRAMEWORK_NAME, "rectangle"); 
	
	public static final String PROPERTY_COLOR = "color";
	
	private Color color;
	
	public Rectangle(UIContext ctx) {
		this(Color.white, ctx);
		
	}
	
	public Rectangle(Color color, UIContext ctx) {
		super(ctx);
		
		setUseImmediateMode(true);
		this.color = color;
		
		this.registerChangeListener();
		
	}
	
	private void registerChangeListener() {
		this.registerPropertyChangeListener(e -> this.announceRepaint());
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		Color oldColor = this.color;
		this.color = color;
		this.firePropertyChange(PROPERTY_COLOR, oldColor, color);
		
	}
	
}
