package xueli.game2.resource.submanager.render.texture.atlas;

import java.util.Objects;

import org.lwjgl.utils.vector.Vector2f;

public final class AtlasResourceHolder {

	private final int textureId;

	private final Vector2f leftTop;
	private final Vector2f rightBottom;
	private final Vector2f leftBottom;
	private final Vector2f rightTop;

	public AtlasResourceHolder(Vector2f leftTop, Vector2f rightBottom, int textureId) {
		this.textureId = textureId;

		this.leftTop = leftTop;
		this.rightBottom = rightBottom;

		this.leftBottom = new Vector2f(leftTop.x, rightBottom.y);
		this.rightTop = new Vector2f(rightBottom.x, leftTop.y);

	}

	public Vector2f leftTop() {
		return leftTop;
	}

	public Vector2f rightBottom() {
		return rightBottom;
	}

	public Vector2f leftBottom() {
		return leftBottom;
	}

	public Vector2f rightTop() {
		return rightTop;
	}

	public int textureId() {
		return textureId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (AtlasResourceHolder) obj;
		return Objects.equals(this.leftTop, that.leftTop) &&
				Objects.equals(this.rightBottom, that.rightBottom) &&
				this.textureId == that.textureId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(leftTop, rightBottom, textureId);
	}

	@Override
	public String toString() {
		return "AtlasResourceHolder[" +
				"leftTop=" + leftTop + ", " +
				"rightBottom=" + rightBottom + ", " +
				"textureId=" + textureId + ']';
	}

}
