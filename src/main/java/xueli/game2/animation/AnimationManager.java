package xueli.game2.animation;

import java.util.ArrayList;
import java.util.function.Supplier;

public class AnimationManager {
	
	private final Supplier<Long> timeProvider;
	private final ArrayList<AnimationInstanceImpl> animationInstances = new ArrayList<>();
	
	public AnimationManager(Supplier<Long> timeProvider) {
		this.timeProvider = timeProvider;
	}
	
	public AnimationInstance start(AnimationBinding binding, long duration) {
		AnimationInstanceImpl inst = new AnimationInstanceImpl(binding, timeProvider.get(), duration);
		animationInstances.add(inst);
		return inst;
	}
	
	public void tick(long time) {
		
	}
	
}
