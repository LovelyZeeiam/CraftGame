package xueli.animation;

public interface AnimationInstance {
	
	public void pause();
	
	public void stop(double progress);
	
	public double getProgress();
	
	public long getDuration();
	
	public void addAnimationEndListener(AnimationEndListener listener);
	
}
