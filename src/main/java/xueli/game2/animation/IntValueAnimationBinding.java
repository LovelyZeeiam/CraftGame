package xueli.game2.animation;

import java.util.function.Supplier;

public abstract class IntValueAnimationBinding extends CurveAnimationBinding {
	
	private final Supplier<Integer> startValueSupplier, endValueSupplier;
	private final boolean stay;
	
	public IntValueAnimationBinding(Curve curve, Supplier<Integer> startValue, Supplier<Integer> endValue, boolean stay) {
		super(curve);
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
		
	}
	
	@Override
	protected void animRealProgress(double progress) {
		this.progress((int) (realStartValue + this.realDurationValue * progress));
	}
	
	@Override
	public void animEnd() {
		if(!stay) {
			this.progress(realStartValue);
		}
	}
	
	protected abstract void progress(int val);

}
