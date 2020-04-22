package xueLi.craftGame.gui.animation2D;

public interface AnimationCallBack {

	/**
	 * 初始化这个动画的回调函数
	 * @param animation 该动画本身的指针
	 */
	public void initAnimation(Animation2D animation);
	
	/**
	 * 当这个动画开始的时候会执行什么
	 * @param animation
	 */
	public void startAnimation(Animation2D animation);
	
	/**
	 * 处理动画的回调函数
	 * @param offset 从动画开始到调用这个函数经过的时间毫秒数
	 * @param total 动画的总共将持续的时间
	 * @param animation 该动画本身的指针
	 */
	public void processAnimation(long offset, Animation2D animation);
	
	/**
	 * 动画结束啦 回个调
	 * @param animation 该动画本身的指针
	 */
	public void stopAnimation(Animation2D animation);
	
}
