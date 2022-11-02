package xueli.game2.renderer.legacy.system;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.lwjgl.opengl.GL30;

import xueli.game2.renderer.legacy.buffer.AttributeBuffer;
import xueli.game2.resource.submanager.render.shader.Shader;

// TODO: When submit, divide each render system with texture ID, shaders and so on...
public class RenderSystem implements RenderEquipment {

	private Shader shader;

	private int vao;
	private int renderType;
	private ConcurrentHashMap<Integer, AttributeBuffer> attributes = new ConcurrentHashMap<>();

	private int[] textureId;

	private RenderState originalState;

	RenderSystem(RenderState state) {
		this.originalState = state;

		this.shader = state.getShader();
		this.renderType = state.getRenderType();
		this.textureId = state.getTextureId();
	}

	private boolean init = false;

	@Override
	public void init() {
		if(init)
			return;
		init = true;

		this.vao = GL30.glGenVertexArrays();
		bind(() -> {
			for (AttributeBuffer buffer : attributes.values()) {
				buffer.init();
			}
		});

	}

	public AttributeBuffer addAttribute(AttributeBuffer buffer) {
		attributes.put(buffer.getId(), buffer);
		return buffer;
	}

	public AttributeBuffer getAttribute(int id) {
		return attributes.get(id);
	}

	public AttributeBuffer computeIfAbsence(int id, Function<Integer, AttributeBuffer> supplier) {
		return attributes.computeIfAbsent(id, supplier);
	}

	@Override
	public void bind() {
		GL30.glBindVertexArray(this.vao);
	}

	public int getRenderType() {
		return renderType;
	}

	public Shader getShader() {
		return shader;
	}

	@Override
	public void tick() {
		bind(() -> {
			shader.bind();
			for (int i = 0; i < textureId.length; i++) {
				GL30.glActiveTexture(GL30.GL_TEXTURE0 + i);
				GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId[i]);
			}

			int vertCount = Integer.MAX_VALUE;
			for (AttributeBuffer buffer : attributes.values()) {
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
		for (AttributeBuffer buffer : attributes.values()) {
			buffer.release();
		}
		GL30.glDeleteVertexArrays(this.vao);

	}

	public void reportRebuilt() {
		attributes.values().forEach(AttributeBuffer::reportSyncData);
	}

	public static RenderSystem withState(RenderState state) {
		return new RenderSystem(state);
	}

	public RenderState getOriginalState() {
		return originalState;
	}

}
