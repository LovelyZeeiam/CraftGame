package xueli.game2.renderer.legacy.system;

import xueli.game2.resource.submanager.render.shader.Shader;

import java.util.Arrays;
import java.util.Objects;

public class RenderState {

	private final Shader shader;
	private final int renderType;
	private final int[] textureId = new int[16];

	public RenderState(Shader shader, int renderType) {
		this.shader = shader;
		this.renderType = renderType;
	}

	public RenderState setTextureId(int index, int id) {
		textureId[index] = id;
		return this;
	}

	public int getTextureId(int index) {
		return textureId[index];
	}

	public Shader getShader() {
		return shader;
	}

	public int getRenderType() {
		return renderType;
	}

	int[] getTextureId() {
		return textureId;
	}

	public static RenderState easyState(Shader shader, int renderType, int textureId) {
		return new RenderState(shader, renderType).setTextureId(0, textureId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RenderState that = (RenderState) o;
		return renderType == that.renderType && shader.equals(that.shader) && Arrays.equals(textureId, that.textureId);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(shader, renderType);
		result = 31 * result + Arrays.hashCode(textureId);
		return result;
	}
}
