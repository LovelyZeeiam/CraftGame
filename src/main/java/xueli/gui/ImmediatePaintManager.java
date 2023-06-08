package xueli.gui;

import org.lwjgl.utils.vector.Matrix3f;
import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2f;
import xueli.gui.driver.GraphicDriver;

// Every frame it just draws all
public class ImmediatePaintManager extends PaintManager {
	
	private final GraphicDriver driver;
	
	public ImmediatePaintManager(WidgetAccess widget, GraphicDriver driver) {
		super(widget, driver);
		this.driver = driver;
		
		// Can't put in parent class initialization because "announcements" can be null there!
		this.announceSizeChange();
		this.announceRepaint(0, 0, getWidgetWidth(), getWidgetHeight());
		
	}

	@Override
	public void announceSizeChange() {
	}

	@Override
	public void announceRepaint(float x, float y, float width, float height) {
	}

	@Override
	public void doPaint() {
		this.driver.pushMatrix(Matrix3f.translate(new Vector2f(getWidgetX(), getWidgetY()), new Matrix3f(), null));
		this.driver.scissorPush(0, 0, getWidgetWidth(), getWidgetHeight());
		this.widgetRealPaint(0, 0, getWidgetWidth(), getWidgetHeight());
		this.driver.scissorPop();
		this.driver.popMatrix();
		
	}

	@Override
	public void release() {
	}

}
