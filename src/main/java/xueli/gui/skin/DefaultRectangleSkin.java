package xueli.gui.skin;

import xueli.gui.SizeHint;
import xueli.gui.Widget;
import xueli.gui.WidgetSkin;
import xueli.gui.driver.GraphicDriver;
import xueli.gui.driver.GraphicDriver.FillType;
import xueli.gui.widget.Rectangle;

public class DefaultRectangleSkin implements WidgetSkin {

	public DefaultRectangleSkin() {
	}

	@Override
	public void install(Widget widget) {
	}

	@Override
	public SizeHint measure(Widget widget, GraphicDriver graphics) {
		return new SizeHint(SizeHint.POLICY_IGNORED, 0, 0);
	}

	@Override
	public void paint(Widget widget, float x, float y, float width, float height, GraphicDriver graphics) {
		Rectangle w = (Rectangle) widget;
		graphics.setColor(w.getColor());
		graphics.drawFilledRect(x, y, width, height, FillType.COLOR);
		
	}

	@Override
	public void uninstall(Widget widget) {
	}

}
