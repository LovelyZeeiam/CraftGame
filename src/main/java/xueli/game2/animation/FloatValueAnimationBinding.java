package xueli.game2.animation;

import java.util.function.Supplier;

public abstract class FloatValueAnimationBinding extends CurveAnimationBinding {
	
	private final Supplier<Float> startValueSupplier, endValueSupplier;
	private final boolean stay;
	
	public FloatValueAnimationBinding(Curve curve, Supplier<Float> startValue, Supplier<Float> endValue, boolean stay) {
		super(curve);
		this.startValueSupplier = startValue;
		this.endValueSupplier = endValue;
		this.stay = stay;
		
	}
	
	private float realStartValue;
	private double realEndValue, realDurationValue;
	
	@Override
	public void animStart() {
		this.realStartValue = startValueSupplier.get();
		this.realEndValue = endValueSupplier.get();
		this.realDurationValue = this.realEndValue - this.realStartValue;
		
	}
	
	@Override
	protected void animRealProgress(double progress) {
		this.progress((float) (realStartValue + this.realDurationValue * progress));
	}
	
	@Override
	public void animEnd() {
		if(!stay) {
			this.progress(realStartValue);
		}
	}
	
	protected abstract void progress(float val);

}
