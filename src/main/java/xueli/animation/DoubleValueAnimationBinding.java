package xueli.animation;

import java.util.function.Supplier;

public abstract class DoubleValueAnimationBinding implements AnimationBinding {

	private final Supplier<Double> startValueSupplier, endValueSupplier;
	private final boolean stay;

	public DoubleValueAnimationBinding(Supplier<Double> startValue, Supplier<Double> endValue, boolean stay) {
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
	public void animProgress(double progress) {
		this.progress(realStartValue + this.realDurationValue * progress);

	}

	@Override
	public void animEnd() {
		if (!stay) {
			this.progress(realStartValue);
		} else {
			this.progress(realEndValue);
		}
	}

	protected abstract void progress(double val);

}
