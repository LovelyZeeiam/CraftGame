package xueli.game2.renderer.legacy.buffer;

import org.lwjgl.opengl.GL30;
import xueli.game2.renderer.legacy.ShapeType;
import xueli.game2.renderer.legacy.VertexType;

import java.util.HashMap;
import java.util.function.Consumer;

public class VertexAttribute implements Bindable {

	private final int vao;
	private final HashMap<Integer, AttributeBuffer> buffers = new HashMap<>();

	private final ShapeType shapeType;

	public VertexAttribute(ShapeType shapeType) {
		this.shapeType = shapeType;
		this.vao = GL30.glGenVertexArrays();

	}

	public void addAttributeBuffer(int id, int attrSize, VertexType type) {
		AttributeBuffer buf = new AttributeBuffer(id, attrSize, type);
		buf.init();
		this.buffers.put(id, buf);

	}

	public AttributeBuffer getAttributeBuffer(int id) {
		return this.buffers.get(id);
	}

	public void forEachAttribute(Consumer<Integer> c) {
		buffers.keySet().forEach(c);
	}

	@Override
	public void bind() {
		GL30.glBindVertexArray(vao);
	}

	public void render(int vertCount) {
		this.bind();
		buffers.values().forEach(AttributeBuffer::tick);
		GL30.glDrawArrays(shapeType.getGLValue(), 0, vertCount);
		this.unbind();

	}

	@Override
	public void unbind() {
		GL30.glBindVertexArray(0);
	}

	public void release() {
		this.bind();
		buffers.values().forEach(AttributeBuffer::release);
		this.unbind();

		GL30.glDeleteVertexArrays(this.vao);

	}

}
