package xueli.animation;

import java.util.function.Supplier;

public abstract class IntValueAnimationBinding implements AnimationBinding {
	
	private final Supplier<Integer> startValueSupplier, endValueSupplier;
	private final boolean stay;
	
	public IntValueAnimationBinding(Supplier<Integer> startValue, Supplier<Integer> endValue, boolean stay) {
		this.startValueSupplier = startValue;
		this.endValueSupplier = endValue;
		this.stay = stay;
		
	}
	
	private int realStartValue;
	private double realEndValue, realDurationValue;
	
	@Override
	public void animStart() {
		this.realStartValue = startValueSupplier.get();
		this.realEndValue = endValueSupplier.get();
		this.realDurationValue = this.realEndValue - this.realStartValue;
//		System.out.println(realStartValue + ", " + realEndValue + ", " + realDurationValue);
		
	}
	
	@Override
	public void animProgress(double progress) {
		this.progress((int) (realStartValue + this.realDurationValue * progress));
//		System.out.println(realStartValue + ", " + realDurationValue + ", " + progress);
	}
	
	@Override
	public void animEnd() {
		if(!stay) {
			this.progress(realStartValue);
		} else {
			this.progress((int) realEndValue);
		}
	}
	
	protected abstract void progress(int val);

}
