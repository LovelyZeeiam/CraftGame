package xueli.animation;

import java.util.ArrayList;
import java.util.function.Supplier;

public class AnimationManager {
	
	private final Supplier<Long> timeProvider;
	private final ArrayList<AnimationInstanceImpl> animationInstances = new ArrayList<>();
	
	public AnimationManager(Supplier<Long> timeProvider) {
		this.timeProvider = timeProvider;
	}
	
	public AnimationInstance start(AnimationBinding binding, Curve curve, long duration) {
		AnimationInstanceImpl inst = new AnimationInstanceImpl(binding, timeProvider.get(), duration, curve, false);
		animationInstances.add(inst);
		return inst;
	}
	
	public AnimationInstance startReverse(AnimationBinding binding, Curve curve, long duration) {
		AnimationInstanceImpl inst = new AnimationInstanceImpl(binding, timeProvider.get(), duration, curve, true);
		animationInstances.add(inst);
		return inst;
	}
	
	public AnimationInstance startReverse(AnimationBinding binding, Curve curve, AnimationInstance previous) {
		return this.startReverse(binding, curve, previous.getDuration(), previous);
	}
	
	public AnimationInstance startReverse(AnimationBinding binding, Curve curve, long duration, AnimationInstance previous) {
		long simulateStartTime = timeProvider.get();
		if(previous != null) {
			double lastProgress = previous.getProgress();
			int shouldHaveGoneTime = (int) ((1 - lastProgress) * duration);
			simulateStartTime -= shouldHaveGoneTime;
		}
		AnimationInstanceImpl inst = new AnimationInstanceImpl(binding, simulateStartTime, duration, curve, true);
		animationInstances.add(inst);
		return inst;
	}
	
	public void tick() {
		var iter = animationInstances.iterator();
		while(iter.hasNext()) {
			AnimationInstanceImpl inst = iter.next();
			if(!inst.tick(timeProvider.get())) {
				iter.remove();
			}
		}
		
	}
	
}
