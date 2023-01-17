package xueli.animation;

public abstract class TransitionBinding extends DoubleValueAnimationBinding {

	public TransitionBinding(Curve curve) {
		super(curve, () -> 0.0, () -> 1.0, true);
	}

}
