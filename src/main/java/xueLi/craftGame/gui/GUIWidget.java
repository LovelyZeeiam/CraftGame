package xueLi.craftGame.gui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import xueLi.craftGame.gui.animation2D.Animation2D;

public abstract class GUIWidget {

	private static class Box {
		public Vector2f pos1, pos2 = new Vector2f();
	}

	public Vector2f pos;
	public Vector2f size;
	private Box box = new Box();

	public Vector4f backgroundColor;

	// Sorry but now we only support one animation on the same time;
	private Animation2D animation;

	protected GUIWidget(Vector2f pos, Vector2f size, Vector4f backgroundColor) {
		this.pos = pos;
		this.size = size;
		this.backgroundColor = backgroundColor;
		updateBox();
	}

	protected void updateBox() {
		box.pos1 = pos;
		box.pos2.x = pos.x + size.x;
		box.pos2.y = pos.y + size.y;
	}

	public void update() {
		if(this.animation != null && tickAnimation())
			updateBox();
		
	}

	public abstract void draw();

	public void addAnimation(Animation2D animation) {
		this.animation = animation;
		this.animation.startAnimation();
	}

	/**
	 * @return 我该不该刷新Box呢呐呐呐
	 */
	protected boolean tickAnimation() {
		if (this.animation.started) {
			this.pos = this.animation.pos;
			this.size = this.animation.size;
			return true;
		} else
			this.animation = null;
		return false;
	}

	public void stopAnimation() {
		if(this.animation != null)
			this.animation.stopAnimation();
	}
	
	public abstract void cleanUp();

	/**
	 * 检测鼠标是否在控件上方
	 * 
	 * @param x 鼠标X坐标
	 * @param y 鼠标Y坐标
	 * @return 鼠标是否在控件上方
	 */
	protected boolean isMouseOn(int x, int y) {
		if (x < box.pos1.x | x > box.pos2.x | y < box.pos1.y | y > box.pos2.y)
			return false;
		return true;
	}

}
