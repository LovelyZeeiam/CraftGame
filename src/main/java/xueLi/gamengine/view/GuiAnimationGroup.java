package xueLi.gamengine.view;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiAnimationGroup extends IAnimation {

	private View ctxView;
	
	private ArrayList<IAnimation> animationGroup;
	private IAnimation currentAnimation;
	
	private int anim_count;
	private boolean loop;

	public GuiAnimationGroup(ArrayList<IAnimation> group, View ctxView) {
		this.animationGroup = group;
		this.loop = false;
		this.ctxView = ctxView;

	}

	public GuiAnimationGroup(ArrayList<IAnimation> group, boolean loop, View ctxView) {
		this.animationGroup = group;
		this.loop = loop;
		this.ctxView = ctxView;

	}
	
	@Override
	public void start(HashMap<String, ViewWidget> widgets, ViewWidget widget) {
		this.currentAnimation = animationGroup.get(0);
		this.currentAnimation.start(widgets, widget);

		this.anim_count = 0;
		
		super.start(widgets, widget);

	}
	
	@Override
	public boolean tick(HashMap<String, ViewWidget> widgets) {
		boolean flag = false;
		if(this.currentAnimation == null) flag = this.currentAnimation.tick(widgets);
		if (flag) {
			// 介个动画结束辽 上下一个
			++this.anim_count;
			try {
				this.currentAnimation = animationGroup.get(this.anim_count);
			} catch (IndexOutOfBoundsException e) {
				if (loop) {
					this.start(widgets, widget);
					return false;
				} else {
					return true;
				}
			}
			this.currentAnimation.start(ctxView.widgets, widget);

		}
		return false;
	}

}
