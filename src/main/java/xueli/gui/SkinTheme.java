package xueli.gui;

import java.util.HashMap;

import xueli.gui.driver.GraphicDriver;
import xueli.gui.skin.DefaultRectangleSkin;
import xueli.gui.skin.DefaultWidgetGroupSkin;
import xueli.gui.widget.Rectangle;

public class SkinTheme {
	
	public static final SkinTheme DEFAULT_THEME = new SkinTheme();
	
	static {
		DEFAULT_THEME.registerSkin(WidgetGroup.class, new DefaultWidgetGroupSkin());
		DEFAULT_THEME.registerSkin(Rectangle.class, new DefaultRectangleSkin());
		
	}
	
	private final HashMap<Class<? extends Widget>, WidgetSkin> skinRegistry = new HashMap<>();
	
	protected void registerSkin(Class<? extends Widget> clazz, WidgetSkin skin) {
		this.skinRegistry.put(clazz, skin);
	}
	
	public WidgetSkin getSkin(Class<? extends Widget> clazz) {
		return skinRegistry.get(clazz);
	}
	
	public void install(GraphicDriver driver) {}
	
	public void uninstall(GraphicDriver driver) {}
	
}
