package xueli.animation;

public interface AnimationBinding {

	public void animStart();

	default public void animStartFromEnd() {
		this.animStart();
	}

	public void animProgress(double timeProgress);

	public void animEnd();

	public static AnimationBinding EMPTY = new AnimationBinding() {
		@Override
		public void animStart() {
		}

		@Override
		public void animProgress(double timeProgress) {
		}

		@Override
		public void animEnd() {
		}
	};

}
