package xueli.game2.animation;

public interface AnimationInstance {
	
	public void pause();
	
	public void stop(long jumpTime);
	
	public double getProgress();
	
	public void addAnimationEndListener(AnimationEndListener listener);
	
}
