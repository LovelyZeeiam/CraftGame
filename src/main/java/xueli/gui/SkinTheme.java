package xueli.gui;

import java.util.HashMap;

import xueli.gui.driver.GraphicDriver;

public class SkinTheme {
	
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
