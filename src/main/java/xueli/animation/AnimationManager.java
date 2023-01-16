package xueli.animation;

import java.util.ArrayList;
import java.util.function.Supplier;

public class AnimationManager {
	
	private final Supplier<Long> timeProvider;
	private final ArrayList<AnimationInstanceImpl> animationInstances = new ArrayList<>();
	
	public AnimationManager(Supplier<Long> timeProvider) {
		this.timeProvider = timeProvider;
	}
	
	public AnimationInstance start(AnimationBinding binding, long duration) {
		AnimationInstanceImpl inst = new AnimationInstanceImpl(binding, timeProvider.get(), duration, false);
		animationInstances.add(inst);
		return inst;
	}
	
	public AnimationInstance startReverse(AnimationBinding binding, long duration) {
		AnimationInstanceImpl inst = new AnimationInstanceImpl(binding, timeProvider.get(), duration, true);
		animationInstances.add(inst);
		return inst;
	}
	
	public AnimationInstance startReverse(AnimationBinding binding, AnimationInstance previous) {
		return this.startReverse(binding, previous.getDuration(), previous);
	}
	
	public AnimationInstance startReverse(AnimationBinding binding, long duration, AnimationInstance previous) {
		long simulateStartTime = timeProvider.get();
		if(previous != null) {
			double lastProgress = previous.getProgress();
			int shouldHaveGoneTime = (int) ((1 - lastProgress) * duration);
			simulateStartTime -= shouldHaveGoneTime;
		}
		AnimationInstanceImpl inst = new AnimationInstanceImpl(binding, simulateStartTime, duration, true);
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
