package xueli.craftgame.entity;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import xueli.game.utils.Time;
import xueli.game.vector.Vector;

public abstract class Entity {

	protected Vector position;

	private Vector3f acceleration = new Vector3f();
	private Vector3f speed = new Vector3f();

	protected HashMap<String, Float> boneParameters = new HashMap<>();

	public Entity(Vector position) {
		this.position = position;

	}

	public Entity() {
		this.position = new Vector();

	}

	public void updatePos() {
		speed.x += acceleration.x * Time.deltaTime / 1000.0f;
		speed.y += acceleration.y * Time.deltaTime / 1000.0f;
		speed.z += acceleration.z * Time.deltaTime / 1000.0f;

		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime / 1000.0f, speed.y * Time.deltaTime / 1000.0f,
				speed.z * Time.deltaTime / 1000.0f);

		position.x += deltaPos.x;
		position.y += deltaPos.y;
		position.z += deltaPos.z;

		speed.x *= 0.8f;
		speed.y *= 0.8f;
		speed.z *= 0.8f;

		acceleration.x = acceleration.y = acceleration.z = 0;

	}

	public Vector getPosition() {
		return position;
	}

	/**
	 * 由于实体的顶点数据都很确定，并且变化仅仅有矩阵变化，所以采用一种先注册顶点，渲染的时候仅修改矩阵的对策
	 */
	private boolean hasInitBuffer = false;
	protected VertexPointerEntity pointer;

	protected abstract void storeBuffer(EntityRenderer renderer);

	protected abstract void render(EntityRenderer renderer);

	public float getBoneParameters(String name) {
		return this.boneParameters.get(name);
	}

	public void setBoneParameters(String key, float value) {
		this.boneParameters.put(key, value);
	}

	public void draw(EntityRenderer renderer) {
		if (!hasInitBuffer) {
			this.storeBuffer(renderer);
			hasInitBuffer = true;
		}

		render(renderer);

	}

}
