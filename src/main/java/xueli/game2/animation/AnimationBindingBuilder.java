package xueli.game2.animation;

import java.util.ArrayList;

public class AnimationBindingBuilder {
	
	private ArrayList<AnimationBinding> bindings = new ArrayList<>();
	
	private double lastPointTime = 0;
	private ArrayList<Double> separatePoints = new ArrayList<>();
	
	{
		separatePoints.add(0.0);
	}
	
	private AnimationBindingBuilder() {
	}
	
	public AnimationBindingBuilder add(AnimationBinding binding, double part) {
		binding = binding == null ? AnimationBinding.EMPTY : binding;
		bindings.add(binding);
		this.lastPointTime += part;
		separatePoints.add(this.lastPointTime);
		return this;
	}
	
	public AnimationBinding build() {
		if(bindings.isEmpty()) return AnimationBinding.EMPTY;
		
		double allCount = lastPointTime;
//		for(int i = 0; i < separatePoints.size(); i++) {
//			separatePoints.set(i, separatePoints.get(i) / allCount);
//		}
//		separatePoints.add(1.0);
		
		return new AnimationBinding() {
			AnimationBinding current;
			int currentAnimIndex;
			double currentBindingStart, currentBindingEnd, currentBindingDuration;
			double lastTimeProgress;
			
			@Override
			public void animStart() {
				currentBindingStart = 0;
				currentBindingEnd = 0;
				currentBindingDuration = 0;
				
				currentAnimIndex = 0;
				refreshAnim();
				lastTimeProgress = 0;
				
			}
			
			@Override
			public void animProgress(double timeProgress) {
				double relativeTimeProgress = timeProgress * allCount;
				
				if(relativeTimeProgress > lastTimeProgress) {
					while(relativeTimeProgress > currentBindingEnd) {
						current.animEnd();
						
						currentAnimIndex++;
						refreshAnim();
						
						current.animStart();
						
					}
					
				} else if(relativeTimeProgress < lastTimeProgress) {
					while(relativeTimeProgress < currentBindingStart) {
						current.animStart();
						
						currentAnimIndex--;
						refreshAnim();
						
						current.animStartFromEnd();
						
					}
					
				}
				
				current.animProgress((relativeTimeProgress - currentBindingStart) / currentBindingDuration);
				lastTimeProgress = relativeTimeProgress;
				
			}
			
			private void refreshAnim() {
				current = bindings.get(currentAnimIndex = 0);
				currentBindingStart = separatePoints.get(currentAnimIndex);
				currentBindingEnd = separatePoints.get(currentAnimIndex + 1);
				currentBindingDuration = currentBindingEnd - currentBindingStart;
				
			}
			
			@Override
			public void animEnd() {
				current.animEnd();
				
			}
		};
	}
	
	public static AnimationBindingBuilder newBuilder() {
		return new AnimationBindingBuilder();
	}
	
}
