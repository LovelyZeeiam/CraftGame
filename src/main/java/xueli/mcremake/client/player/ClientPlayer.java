package xueli.mcremake.client.player;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3d;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;

import xueli.game2.Vector;
import xueli.game2.camera3d.MovableCamera;
import xueli.game2.display.Display;
import xueli.game2.math.TriFuncMap;
import xueli.game2.phys.aabb.AABB;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.core.entity.EntityCollider;
import xueli.mcremake.core.entity.PickResult;
import xueli.mcremake.core.entity.VirtualKeyboard;
import xueli.mcremake.core.world.WorldAccessible;
import xueli.mcremake.registry.GameRegistry;

public class ClientPlayer extends Vector {
	
	private static final long serialVersionUID = -1935507223867606182L;

	private static final AABB PLAYER_COLLISION_BOX = new AABB(new Vector3d(-0.4, -1.5, -0.4), new Vector3d(0.4, 0.2, 0.4));

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
		// TODO: CHANGE
		keyboard.forward = display.isKeyDown(GLFW.GLFW_KEY_W);
		keyboard.backward = display.isKeyDown(GLFW.GLFW_KEY_S);
		keyboard.leftward = display.isKeyDown(GLFW.GLFW_KEY_A);
		keyboard.rightward = display.isKeyDown(GLFW.GLFW_KEY_D);
		keyboard.wantDash = display.isKeyDown(GLFW.GLFW_KEY_R);
		keyboard.wantFly = display.isKeyDown(GLFW.GLFW_KEY_SPACE);
		keyboard.wantSneak = display.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT);
//		keyboard.wantUseLeftButton = display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) ? 1 : 0;
//		keyboard.wantUseRightButton = display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT) ? 1 : 0;

		this.rotX -= display.getCursorDY() * 0.1f;
		this.rotY += display.getCursorDX() * 0.1f;
		this.rotX = Math.min(this.rotX, 89.0);
		this.rotX = Math.max(this.rotX, -89.0);

	}

	private void keyboardStateReset() {
		keyboard.forward = keyboard.backward = keyboard.leftward = keyboard.rightward = keyboard.wantDash = keyboard.wantFly = keyboard.wantSneak = false;
//		keyboard.wantUseLeftButton = keyboard.wantUseRightButton = 0;

	}

	public void handleLeftButton(PickResult pickResult, int holdTime) {
		Vector3i blockPos = pickResult.blockPos();
		world.setBlock(blockPos.x, blockPos.y, blockPos.z, null);

	}

	public void handleRightButton(PickResult pickResult, int holdTime) {
		Vector3i blockPos = pickResult.placePos();
		world.setBlock(blockPos.x, blockPos.y, blockPos.z, GameRegistry.STONE);

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

		Vector3f acceleration = new Vector3f();
		float speed = 0.3f;

		if(keyboard.wantDash) {
			speed *= 7.0f;
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

//		System.out.println(this.x + ", " + this.y + ", " + this.z);

	}

	public void tick() {
		this.moveTick();
		this.keyboardStateReset();

	}

	public MovableCamera getCamera(float partialTick) {
//		System.out.println(partialTick);
		movableCamera.x = lastTickX + (x - lastTickX) * partialTick;
		movableCamera.y = lastTickY + (y - lastTickY) * partialTick;
		movableCamera.z = lastTickZ + (z - lastTickZ) * partialTick;
		movableCamera.rotX = rotX;
		movableCamera.rotY = rotY;
		movableCamera.rotZ = rotZ;

//		System.out.println(this.movableCamera.x + ", " + this.movableCamera.y + ", " + this.movableCamera.z + ", " + this.movableCamera.rotX + ", " + this.movableCamera.rotY);

		return movableCamera;
	}

}
