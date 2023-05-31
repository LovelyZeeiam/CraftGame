package xueli.animation;

public class TransitionManager {

	private final AnimationManager animator;

	public TransitionManager(AnimationManager animator) {
		this.animator = animator;
	}

	public TransitionCaller registerNewTransition(TransitionBinding binding) {
		return this.registerNewTransition(binding, Curves.linear, 0);
	}

	public TransitionCaller registerNewTransition(TransitionBinding binding, Curve curve, long duration) {
		TransitionCaller caller = new TransitionCaller() {
			private AnimationInstance previous;

			@Override
			public void announceTransition() {
				if (previous != null) {
					previous.stop(previous.getProgress());
				}

				AnimationInstance newInstance = animator.start(binding, curve, duration);
				newInstance.addAnimationEndListener(() -> {
					previous = null;
				});
				this.previous = newInstance;

			}
		};
		caller.announceTransition();
		return caller;
	}

	public StateChangingTransitionCaller registerNewStateChangingTransition(TransitionBinding binding, Curve curve,
			long duration) {
		StateChangingTransitionCaller caller = new StateChangingTransitionCaller() {
			private AnimationInstance previous;

			@Override
			public void announceTransition(boolean state) {
				if (previous != null) {
					previous.stop(previous.getProgress());
				}

				AnimationInstance newInstance = state ? animator.start(binding, curve, duration)
						: animator.startReverse(binding, curve, duration);
				newInstance.addAnimationEndListener(() -> {
					previous = null;
				});
				this.previous = newInstance;

			}
		};
		caller.announceTransition(false);
		return caller;
	}

}
