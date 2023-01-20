package xueli.animation;

import java.util.function.Supplier;

public abstract class DoubleValueAnimationBinding extends CurveAnimationBinding {
	
	private final Supplier<Double> startValueSupplier, endValueSupplier;
	private final boolean stay;
	
	public DoubleValueAnimationBinding(Curve curve, Supplier<Double> startValue, Supplier<Double> endValue, boolean stay) {
		super(curve);
		this.startValueSupplier = startValue;
		this.endValueSupplier = endValue;
		this.stay = stay;
		
	}
	
	private double realStartValue;
	private double realEndValue, realDurationValue;
	
	@Override
	public void animStart() {
		this.realStartValue = startValueSupplier.get();
		this.realEndValue = endValueSupplier.get();
		this.realDurationValue = this.realEndValue - this.realStartValue;
		
	}
	
	@Override
	protected void animRealProgress(double progress) {
		this.progress(realStartValue + this.realDurationValue * progress);
	}
	
	@Override
	public void animEnd() {
		if(!stay) {
			this.progress(realStartValue);
		} else {
			this.progress(realEndValue);
		}
	}
	
	protected abstract void progress(double val);

}
