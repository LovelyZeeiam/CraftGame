package xueli.game2.renderer.legacy.multithreaded;

import java.util.function.Consumer;

public interface Renderable {

	public void gatherRenderData(Consumer<RenderData> consumer);

}
