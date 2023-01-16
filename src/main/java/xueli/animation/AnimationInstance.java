package xueli.animation;

public interface AnimationInstance {
	
	public void pause();
	
	public void stop(long jumpTime);
	
	public double getProgress();
	
	public long getDuration();
	
	public void addAnimationEndListener(AnimationEndListener listener);
	
}
