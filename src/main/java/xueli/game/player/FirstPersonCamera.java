package xueli.game.player;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game.input.InputHolder;
import xueli.game.utils.Time;
import xueli.game.vector.Vector;

public class FirstPersonCamera {

	Vector pos;
	Vector3f speed = new Vector3f();
	private Vector3f acceleration = new Vector3f();

	private InputHolder inputHolder = null;

	public FirstPersonCamera(float x, float y, float z) {
		this(x, y, z, null);

	}

	public FirstPersonCamera(float x, float y, float z, float rotX, float rotY, float rotZ) {
		this(x, y, z, rotX, rotY, rotZ, null);

	}

	public FirstPersonCamera(float x, float y, float z, InputHolder holder) {
		this.pos = new Vector(x, y, z);
		this.inputHolder = holder;

	}

	public FirstPersonCamera(float x, float y, float z, float rotX, float rotY, float rotZ, InputHolder holder) {
		this.pos = new Vector(x, y, z, rotX, rotY, rotZ);
		this.inputHolder = holder;

	}

	public void tick() {
		if (inputHolder.isMouseGrabbed()) {
			if (inputHolder.isKeyDown(GLFW.GLFW_KEY_W)) {
				if (inputHolder.isKeyDown(GLFW.GLFW_KEY_R)) {
					acceleration.x -= this.getSpeed() * 4f * (float) Math.sin(Math.toRadians(-pos.rotY));
					acceleration.z -= this.getSpeed() * 4f * (float) Math.cos(Math.toRadians(-pos.rotY));
				} else {
					acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
					acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
				}
			}
			if (inputHolder.isKeyDown(GLFW.GLFW_KEY_S)) {
				acceleration.x += this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
				acceleration.z += this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
			}
			if (inputHolder.isKeyDown(GLFW.GLFW_KEY_A)) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY + 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY + 90));
			}
			if (inputHolder.isKeyDown(GLFW.GLFW_KEY_D)) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY - 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY - 90));
			}

			if (inputHolder.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				acceleration.y += this.getSpeed() * 2.0f;
			}

			if (inputHolder.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
				acceleration.y -= this.getSpeed() * 2.0f;
			}

			pos.rotX += inputHolder.getCursor_dy() * 0.08f;
			pos.rotY += inputHolder.getCursor_dx() * 0.08f;

			if (pos.rotY > 360) {
				pos.rotY -= 360;
			} else if (pos.rotY < 0) {
				pos.rotY += 360;
			}

			if (pos.rotX > 90)
				pos.rotX = 90;
			else if (pos.rotX < -90)
				pos.rotX = -90;

		}

		speed.x += acceleration.x * Time.deltaTime / 1000.0f;
		speed.y += acceleration.y * Time.deltaTime / 1000.0f;
		speed.z += acceleration.z * Time.deltaTime / 1000.0f;

		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime / 1000.0f, speed.y * Time.deltaTime / 1000.0f,
				speed.z * Time.deltaTime / 1000.0f);
		pos.x += deltaPos.x;
		pos.y += deltaPos.y;
		pos.z += deltaPos.z;

		speed.x *= 0.8f;
		speed.y *= 0.8f;
		speed.z *= 0.8f;

		acceleration.x = acceleration.y = acceleration.z = 0;

	}

	public float getSpeed() {
		return 80.0f;
	}

	public Vector getPos() {
		return pos;
	}

}
