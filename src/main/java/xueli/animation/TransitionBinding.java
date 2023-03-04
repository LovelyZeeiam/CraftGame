package xueli.animation;

public abstract class TransitionBinding implements AnimationBinding {

	public TransitionBinding() {
	}

	@Override
	public void animStart() {
		this.animProgress(0.0);
		
	}

	@Override
	public void animEnd() {
		this.animProgress(1.0);
	}

}
