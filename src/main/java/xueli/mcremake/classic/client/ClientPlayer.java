package xueli.mcremake.classic.client;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game.vector.Vector;
import xueli.game2.camera3d.MovableCamera;
import xueli.game2.display.Display;
import xueli.mcremake.classic.core.entity.VirtualKeyboard;

public class ClientPlayer extends Vector {

	private final CraftGameClient ctx;

	public final VirtualKeyboard keyboard = new VirtualKeyboard();

	private boolean firstTick = true;
	private double lastTickX, lastTickY, lastTickZ;
	private MovableCamera movableCamera = new MovableCamera();

	private final Vector3f velocity = new Vector3f();

	public ClientPlayer(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	public void inputRefresh() {
		Display display = ctx.getDisplay();
		keyboard.forward = display.isKeyDown(GLFW.GLFW_KEY_W);
		keyboard.backward = display.isKeyDown(GLFW.GLFW_KEY_S);
		keyboard.leftward = display.isKeyDown(GLFW.GLFW_KEY_A);
		keyboard.rightward = display.isKeyDown(GLFW.GLFW_KEY_D);
		keyboard.wantDash = display.isKeyDown(GLFW.GLFW_KEY_R);
		keyboard.wantFly = display.isKeyDown(GLFW.GLFW_KEY_SPACE);
		keyboard.wantSneak = display.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);

		this.rotX += display.getCursorDY() * 0.1f;
		this.rotY += display.getCursorDX() * 0.1f;

	}

	public void tick() {
		if(firstTick) {
			movableCamera.x = lastTickX = x;
			movableCamera.y = lastTickY = y;
			movableCamera.z = lastTickZ = z;
			firstTick = false;
		}

		lastTickX = x;
		lastTickY = y;
		lastTickZ = z;

		Display display = ctx.getDisplay();

		Vector3f acceleration = new Vector3f();
		float speed = 0.09f;

		if(keyboard.forward) {
			acceleration.x -= speed * (float) Math.sin(Math.toRadians(-rotY));
			acceleration.z -= speed * (float) Math.cos(Math.toRadians(-rotY));
		} else if(keyboard.backward) {
			acceleration.x += speed * (float) Math.sin(Math.toRadians(-rotY));
			acceleration.z += speed * (float) Math.cos(Math.toRadians(-rotY));
		}

		if(keyboard.leftward) {
			acceleration.x -= speed * (float) Math.sin(Math.toRadians(-rotY + 90));
			acceleration.z -= speed * (float) Math.cos(Math.toRadians(-rotY + 90));
		} else if(keyboard.rightward) {
			acceleration.x += speed * (float) Math.sin(Math.toRadians(-rotY + 90));
			acceleration.z += speed * (float) Math.cos(Math.toRadians(-rotY + 90));
		}

		if(keyboard.wantFly) {
			acceleration.y += speed * 1.4f;
		} else if(keyboard.wantSneak) {
			acceleration.y -= speed * 1.4f;
		}

		velocity.x += acceleration.x;
		velocity.y += acceleration.y;
		velocity.z += acceleration.z;

		velocity.x *= 0.9f;
		velocity.z *= 0.9f;
		velocity.y *= 0.8f;

		this.x += velocity.x;
		this.y += velocity.y;
		this.z += velocity.z;

	}

	public Matrix4f getViewMatrix(float partialTick) {
//		System.out.println(partialTick);
		movableCamera.x = lastTickX + (x - lastTickX) * partialTick;
		movableCamera.y = lastTickY + (y - lastTickY) * partialTick;
		movableCamera.z = lastTickZ + (z - lastTickZ) * partialTick;
		movableCamera.rotX = rotX;
		movableCamera.rotY = rotY;
		movableCamera.rotZ = rotZ;

//		System.out.println(this.movableCamera.x + ", " + this.movableCamera.y + ", " + this.movableCamera.z + ", " + this.movableCamera.rotX + ", " + this.movableCamera.rotY);

		return movableCamera.getCameraMatrix();
	}

}
