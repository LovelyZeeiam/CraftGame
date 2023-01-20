package xueli.animation;

import java.util.HashMap;

public class TransitionManager {
	
	private final AnimationManager animator;
	
	// Every transition called should correspond to one animation instance or null
	// TODO: Is there no need to create a concurrent HashMap?
	private final HashMap<TransitionCaller, AnimationInstance> instances = new HashMap<>();
	
	public TransitionManager(AnimationManager animator) {
		this.animator = animator;
	}
	
	public TransitionCaller registerNewTransition(TransitionBinding binding, long duration) {
		TransitionCaller caller = new TransitionCaller() {
			@Override
			public void announceTransition() {
				AnimationInstance previous = instances.get(this);
				if(previous != null) {
					previous.stop((long) (previous.getProgress() * previous.getDuration()));				
				}
				
				AnimationInstance newInstance = animator.start(binding, duration);
				newInstance.addAnimationEndListener(() -> {
					instances.remove(this);
				});
				instances.put(this, newInstance);
				
			}
		};
		caller.announceTransition();
		return caller;
	}
	
}
