package xueli.game2.renderer.legacy.buffer;

import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import xueli.game2.renderer.legacy.VertexType;
import xueli.game2.renderer.legacy.shape.ShapeType;

public class VertexAttribute implements Bindable {

	private final int vao;
	private final HashMap<Integer, AttributeBuffer> buffers = new HashMap<>();

	private final ShapeType shapeType;

	public VertexAttribute(ShapeType shapeType) {
		this.shapeType = shapeType;
		this.vao = GL30.glGenVertexArrays();

	}

	// TODO: change to VertexFormatSpec and pass it from constructor
	public void addAttributeBuffer(int id, int attrSize, VertexType type) {
		AttributeBuffer buf = new AttributeBuffer(id, attrSize, type);
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

	public void renderElement(ElementBuffer ebo, int vertCount) {
		this.bind();
		ebo.bind();
		buffers.values().forEach(AttributeBuffer::tick);
		GL30.glDrawElements(shapeType.getGLValue(), vertCount, GL11.GL_UNSIGNED_INT, 0);
		ebo.unbind();
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
