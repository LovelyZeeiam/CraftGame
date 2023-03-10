package xueli.animation;

import java.util.ArrayList;

class AnimationInstanceImpl implements AnimationInstance {
	
	private final AnimationBinding binding;
	private final Curve curve;
	private final long startTime, duration, endTime;
	private final boolean reverse;
	
	// Progress from the time, so its change is linear
	private double progress = 0.0;
	private AnimationStage stage = AnimationStage.PLAYING;
	
	private final ArrayList<AnimationEndListener> endListeners = new ArrayList<>();
	
	AnimationInstanceImpl(AnimationBinding binding, long startTime, long duration, Curve curve, boolean reverse) {
		this.binding = binding;
		this.startTime = startTime;
		this.duration = duration;
		this.curve = curve;
		this.endTime = this.startTime + this.duration;
		
		this.reverse = reverse;
		
		this.binding.animStart();
		this.progress = 0.0;
		
	}

	/**
	 * @return Whether the animation needs ticking
	 */
	boolean tick(long currentTime) {	
		if(currentTime >= endTime) {
			this.stage = AnimationStage.STOP;
			this.progress = 1.0;
			this.binding.animEnd();
			
			endListeners.forEach(AnimationEndListener::onAnimationEnd);
			
			return false;
		}
		
		switch (this.stage) {
		case PLAYING -> {
			this.progress = (double) (currentTime - startTime) / duration;
			double curveValue = curve.getValue(progress);
			if(reverse)
				this.binding.animProgress(1 - curveValue);
			else
				this.binding.animProgress(curveValue);
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
	public void stop(double progress) {
		this.stage = AnimationStage.STOP;
		this.binding.animProgress(curve.getValue(progress));
		
	}
	
	@Override
	public double getProgress() {
		return this.progress;
	}
	
	@Override
	public long getDuration() {
		return this.duration;
	}
	
	@Override
	public void addAnimationEndListener(AnimationEndListener listener) {
		endListeners.add(listener);
	}

}
