package xueli.gui.skin;

import xueli.gui.SkinTheme;
import xueli.gui.WidgetGroup;
import xueli.gui.driver.GraphicDriver;
import xueli.gui.widget.Rectangle;

public class DefaultSkin extends SkinTheme {
	
	public static final SkinTheme SHARED_INSTANCE = new DefaultSkin();

	public DefaultSkin() {
		registerSkin(WidgetGroup.class, new DefaultWidgetGroupSkin());
		registerSkin(Rectangle.class, new DefaultRectangleSkin());
		
	}
	
	@Override
	public void install(GraphicDriver driver) {
		
	}
	
	@Override
	public void uninstall(GraphicDriver driver) {
		
	}

}
