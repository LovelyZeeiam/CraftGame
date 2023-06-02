package xueli.gui.widget;

import java.awt.Color;

import xueli.gui.GameUIContext;
import xueli.gui.Widget;
import xueli.gui.WidgetSkin;
import xueli.gui.driver.GraphicDriver;
import xueli.gui.driver.GraphicDriver.FillType;

public class TestWidget extends Widget {

	public TestWidget(GameUIContext ctx) {
		super(ctx);

		setUseImmediateMode(true);
		setBounds(100, 100, 100, 100);
		setSkin(new WidgetSkin() {

			@Override
			public void install(Widget widget) {

			}

			@Override
			public void paint(Widget widget, float x, float y, float width, float height, GraphicDriver graphics) {
				graphics.setColor(Color.lightGray);
				graphics.drawFilledRect(0, 0, width, height, FillType.COLOR);
			}

			@Override
			public void uninstall(Widget widget) {

			}

		});

	}

}
