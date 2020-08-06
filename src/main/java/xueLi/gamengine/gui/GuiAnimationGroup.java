package xueLi.gamengine.gui;

import java.util.ArrayList;

public class GuiAnimationGroup extends IAnimation {

	private ArrayList<IAnimation> animationGroup;
	private IAnimation currentAnimation;
	private int anim_count;
	private boolean loop;

	public GuiAnimationGroup(ArrayList<IAnimation> group) {
		this.animationGroup = group;
		this.loop = false;

	}

	public GuiAnimationGroup(ArrayList<IAnimation> group, boolean loop) {
		this.animationGroup = group;
		this.loop = loop;

	}

	@Override
	public void start() {
		this.currentAnimation = animationGroup.get(0);
		this.currentAnimation.start();

		this.anim_count = 0;

	}

	@Override
	public boolean tick(GUIWidget widget) {
		if (this.currentAnimation.tick(widget)) {
			// 介个动画结束辽 上下一个
			++this.anim_count;
			try {
				this.currentAnimation = animationGroup.get(this.anim_count);
			} catch (IndexOutOfBoundsException e) {
				if (loop) {
					this.start();
					return false;
				} else {
					return true;
				}
			}
			this.currentAnimation.start();

			widget.real_x = widget.x.value;
			widget.real_y = widget.y.value;
			widget.real_width = widget.width.value;
			widget.real_height = widget.height.value;

		}
		return false;
	}

}
