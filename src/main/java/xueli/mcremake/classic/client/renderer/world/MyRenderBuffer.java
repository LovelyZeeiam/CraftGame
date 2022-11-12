package xueli.mcremake.classic.client.renderer.world;

import xueli.game2.renderer.legacy.ShapeType;
import xueli.game2.renderer.legacy.VertexType;
import xueli.game2.renderer.legacy.engine.VertexAttributeRenderBuffer;

public class MyRenderBuffer extends VertexAttributeRenderBuffer {

	public static final int ATTR_VERTEX = 0;
	public static final int UV_VERTEX = 1;
	public static final int COLOR_VERTEX = 2;

	public MyRenderBuffer() {
		super(ShapeType.TRIANGLES);
		this.attr.bind(() -> {
			this.attr.addAttributeBuffer(ATTR_VERTEX, 3, VertexType.FLOAT);
			this.attr.addAttributeBuffer(UV_VERTEX, 2, VertexType.FLOAT);
			this.attr.addAttributeBuffer(COLOR_VERTEX, 3, VertexType.FLOAT);
		});

	}

}
