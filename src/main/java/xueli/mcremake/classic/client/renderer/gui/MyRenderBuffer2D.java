package xueli.mcremake.classic.client.renderer.gui;

import xueli.game2.renderer.legacy.ShapeType;
import xueli.game2.renderer.legacy.VertexType;
import xueli.game2.renderer.legacy.engine.VertexAttributeRenderBuffer;

public class MyRenderBuffer2D extends VertexAttributeRenderBuffer {

	public static final int ATTR_VERTEX = 0;
	public static final int ATTR_UV = 1;
	public static final int ATTR_COLOR = 2;

	public MyRenderBuffer2D() {
		super(ShapeType.TRIANGLES);
		this.attr.bind(() -> {
			this.attr.addAttributeBuffer(ATTR_VERTEX, 2, VertexType.FLOAT);
			this.attr.addAttributeBuffer(ATTR_UV, 2, VertexType.FLOAT);
			this.attr.addAttributeBuffer(ATTR_COLOR, 3, VertexType.FLOAT);
		});

	}

}
