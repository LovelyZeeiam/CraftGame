package xueli.game2.animation;

import java.util.ArrayList;

class AnimationInstanceImpl implements AnimationInstance {
	
	private final AnimationBinding binding;
	private final long startTime, duration, endTime;
	
	private double progress = 0.0;
	private AnimationStage stage = AnimationStage.PLAYING;
	
	private final ArrayList<AnimationEndListener> endListeners = new ArrayList<>();
	
	AnimationInstanceImpl(AnimationBinding binding, long startTime, long duration) {
		this.binding = binding;
		this.startTime = startTime;
		this.duration = duration;
		this.endTime = this.startTime + this.duration;
		
		this.binding.animStart();
		this.progress = 0.0;
		
	}

	/**
	 * @return Whether the animation needs ticking
	 */
	boolean tick(long currentTime) {	
		if(currentTime > endTime) {
			this.stage = AnimationStage.STOP;
			this.progress = 1.0;
			this.binding.animEnd();
			
			endListeners.forEach(AnimationEndListener::onAnimationEnd);
			
			return false;
		}
		
		switch (this.stage) {
		case PLAYING -> {
			this.progress = (double) (currentTime - startTime) / duration;
			this.binding.animProgress(progress);
		}
		case PAUSE -> {}
		case STOP -> {
			return false;
		}
		}
		
		return true;
	}
	
	@Override
	public void pause() {
		this.stage = AnimationStage.PAUSE;
		
	}

	@Override
	public void stop(long jumpTime) {
		this.stage = AnimationStage.STOP;
		if(jumpTime >= 0)
			this.binding.animProgress((double) jumpTime / this.duration);
		else
			this.binding.animProgress(1.0);
		
	}
	
	@Override
	public double getProgress() {
		return this.progress;
	}
	
	@Override
	public void addAnimationEndListener(AnimationEndListener listener) {
		endListeners.add(listener);
	}

}
