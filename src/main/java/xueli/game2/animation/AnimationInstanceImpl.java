package xueli.game2.animation;

class AnimationInstanceImpl implements AnimationInstance {
	
	private final AnimationBinding binding;
	private final long startTime, duration, endTime;
	
	private AnimationStage stage = AnimationStage.PLAYING;
	
	AnimationInstanceImpl(AnimationBinding binding, long startTime, long duration) {
		this.binding = binding;
		this.startTime = startTime;
		this.duration = duration;
		this.endTime = this.startTime + this.duration;
		
	}

	/**
	 * @return Whether the animation ends and its instance needs removing from tick list
	 */
	boolean tick(long currentTime) {
		
		// TODO: tick
		return false;
	}
	
	@Override
	public void pause() {
		this.stage = AnimationStage.PAUSE;
	}

	@Override
	public void stop() {
		this.stage = AnimationStage.STOP;
	}

}
