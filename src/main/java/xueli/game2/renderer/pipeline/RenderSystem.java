package xueli.game2.renderer.pipeline;

import java.util.ArrayList;

import org.lwjgl.opengl.GL30;

import xueli.game2.lifecycle.LifeCycle;
import xueli.game2.renderer.buffer.AttributeBuffer;
import xueli.game2.renderer.buffer.Bindable;
import xueli.game2.resource.render.shader.Shader;

public class RenderSystem implements RenderEquipment {

	private Shader shader;

	private int vao;
	private int renderType = GL30.GL_TRIANGLES;
	private ArrayList<AttributeBuffer> attributes = new ArrayList<>();

	public RenderSystem(Shader shader) {
		this.shader = shader;
	}

	@Override
	public void init() {
		this.vao = GL30.glGenVertexArrays();
		bind(() -> {
			for (AttributeBuffer buffer : attributes) {
				buffer.init();
			}
		});

	}

	public AttributeBuffer addAttribute(AttributeBuffer buffer) {
		attributes.add(buffer);
		return buffer;
	}

	@Override
	public void bind() {
		GL30.glBindVertexArray(this.vao);
	}

	public void setRenderType(int renderType) {
		this.renderType = renderType;
	}

	@Override
	public void tick() {
		bind(() -> {
			shader.use();
			
			int vertCount = Integer.MAX_VALUE;
			for (AttributeBuffer buffer : attributes) {
				buffer.tick();
				vertCount = Math.min(vertCount, buffer.getVertexCount());
			}
			GL30.glDrawArrays(renderType, 0, vertCount);
			
			shader.unbind();
		});

	}

	@Override
	public void unbind() {
		GL30.glBindVertexArray(0);
	}

	@Override
	public void release() {
		for (AttributeBuffer buffer : attributes) {
			buffer.release();
		}
		GL30.glDeleteVertexArrays(this.vao);

	}

}
