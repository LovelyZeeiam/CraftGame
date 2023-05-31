package xueli.animation;

import java.util.function.Supplier;

public abstract class FloatValueAnimationBinding implements AnimationBinding {

	private final Supplier<Float> startValueSupplier, endValueSupplier;
	private final boolean stay;

	public FloatValueAnimationBinding(Supplier<Float> startValue, Supplier<Float> endValue, boolean stay) {
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
	public void animProgress(double progress) {
		this.progress((float) (realStartValue + this.realDurationValue * progress));
//		System.out.println(progress);

	}

	@Override
	public void animEnd() {
		if (!stay) {
			this.progress(realStartValue);
		} else {
			this.progress((float) realEndValue);
		}
	}

	protected abstract void progress(float val);

}
