package xueli.game2.renderer.legacy.buffer;

public interface Bindable {

	public void bind();
	public void unbind();

	default public void bind(Runnable r) {
		bind();
		r.run();
		unbind();
	}

}
