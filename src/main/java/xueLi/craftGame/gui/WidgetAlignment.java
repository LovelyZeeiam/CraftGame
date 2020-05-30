package xueLi.craftGame.gui;

import org.lwjgl.util.vector.Vector2f;

public enum WidgetAlignment {

	/**
	 * 这里什么都妹有吖~
	 */
	NONE(new Processor() {
		@Override
		public Vector2f process(GUIWidget widget) {
			return new Vector2f(widget.x,widget.y);
		}
	}),
	/**
	 * 全部居中,此时的pos是偏移量
	 */
	MIDDLE(new Processor() {
		@Override
		public Vector2f process(GUIWidget widget) {
			return new Vector2f((widget.x - widget.width) / 2, (widget.y - widget.height) / 2);
		}
	}),
	/**
	 * 只有x居中
	 */
	X_MIDDLE(new Processor() {
		@Override
		public Vector2f process(GUIWidget widget) {
			return new Vector2f((widget.x - widget.width) / 2, widget.y);
		}
	}),
	/**
	 * 只有y居中
	 */
	Y_MIDDLE(new Processor() {
		@Override
		public Vector2f process(GUIWidget widget) {
			return new Vector2f(widget.x, (widget.y - widget.height) / 2);
		}
	});
	
	private Processor processor;
	private WidgetAlignment(Processor processor) {
		this.processor = processor;
	}
	
	/**
	 * @return 处理后的控件位置
	 */
	public Vector2f process(GUIWidget widget) {
		return processor.process(widget);
	}
	
	public static interface Processor {
		public Vector2f process(GUIWidget widget);
	}
	
}
