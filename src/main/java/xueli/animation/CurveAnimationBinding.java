package xueli.animation;

public abstract class CurveAnimationBinding implements AnimationBinding {

	private final Curve curve;

	public CurveAnimationBinding(Curve curve) {
		this.curve = curve;
	}

	@Override
	public void animProgress(double timeProgress) {
		this.animRealProgress(this.curve.getValue(timeProgress));
	}

	protected abstract void animRealProgress(double progress);

}
