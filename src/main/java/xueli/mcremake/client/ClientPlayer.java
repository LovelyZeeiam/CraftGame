package xueli.mcremake.client;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game.vector.Vector;
import org.lwjgl.utils.vector.Vector3d;
import xueli.game2.camera3d.MovableCamera;
import xueli.game2.core.math.TriFuncMap;
import xueli.game2.display.Display;
import xueli.game2.phys.aabb.AABB;
import xueli.mcremake.core.entity.EntityCollider;
import xueli.mcremake.core.entity.VirtualKeyboard;
import xueli.mcremake.core.world.WorldAccessible;

public class ClientPlayer extends Vector {

	private static AABB PLAYER_COLLISION_BOX = new AABB(new Vector3d(-0.4, -1.5, -0.4), new Vector3d(0.4, 0.2, 0.4));

	private final CraftGameClient ctx;
	private final WorldAccessible world;
	private final EntityCollider collider;

	public final VirtualKeyboard keyboard = new VirtualKeyboard();

	private boolean firstTick = true;
	private double lastTickX, lastTickY, lastTickZ;
	private final MovableCamera movableCamera = new MovableCamera();

	private final Vector3d velocity = new Vector3d();

	public ClientPlayer(CraftGameClient ctx) {
		this.ctx = ctx;
		this.world = ctx.getWorld();
		this.collider = new EntityCollider(PLAYER_COLLISION_BOX, world);

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
		keyboard.wantUseLeftHand = display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT);
		keyboard.wantUseRightHand = display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT);

		this.rotX += display.getCursorDY() * 0.1f;
		this.rotY += display.getCursorDX() * 0.1f;

	}

	private void moveTick() {
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
		float speed = 0.6f;

		if(keyboard.wantDash) {
			speed *= 4.0f;
		}

		if(keyboard.forward) {
			acceleration.x -= speed * (float) TriFuncMap.sin(-rotY);
			acceleration.z -= speed * (float) TriFuncMap.cos(-rotY);
		} else if(keyboard.backward) {
			acceleration.x += speed * (float) TriFuncMap.sin(-rotY);
			acceleration.z += speed * (float) TriFuncMap.cos(-rotY);
		}

		if(keyboard.leftward) {
			acceleration.x -= speed * (float) TriFuncMap.sin(-rotY + 90);
			acceleration.z -= speed * (float) TriFuncMap.cos(-rotY + 90);
		} else if(keyboard.rightward) {
			acceleration.x += speed * (float) TriFuncMap.sin(-rotY + 90);
			acceleration.z += speed * (float) TriFuncMap.cos(-rotY + 90);
		}

		if(keyboard.wantFly) {
			acceleration.y += speed * 1.4f;
		} else if(keyboard.wantSneak) {
			acceleration.y -= speed * 1.4f;
		}

		velocity.x += acceleration.x;
		velocity.y += acceleration.y;
		velocity.z += acceleration.z;

		velocity.x *= 0.5;
		velocity.z *= 0.5;
		velocity.y *= 0.6;

		Vector3d delta = new Vector3d(velocity.x, velocity.y, velocity.z);
		collider.collide(new Vector3d(this.x, this.y, this.z), delta, delta);
		this.x += delta.x;
		this.y += delta.y;
		this.z += delta.z;

	}

	public void tick() {
		this.moveTick();

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
