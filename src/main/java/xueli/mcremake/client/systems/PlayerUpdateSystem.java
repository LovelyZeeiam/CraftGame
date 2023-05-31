package xueli.mcremake.client.systems;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3d;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game2.Vector;
import xueli.game2.display.Display;
import xueli.game2.math.TriFuncMap;
import xueli.game2.phys.aabb.AABB;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.IGameSystem;
import xueli.mcremake.client.player.AttackButtonHandler;
import xueli.mcremake.client.player.UseButtonHandler;
import xueli.mcremake.core.entity.CollideResult;
import xueli.mcremake.core.entity.EntityCollider;
import xueli.mcremake.core.entity.PickCollider;
import xueli.mcremake.core.entity.VirtualKeyboard;

public class PlayerUpdateSystem implements IGameSystem {

	private static final AABB PLAYER_COLLISION_BOX = new AABB(new Vector3d(-0.4, -1.5, -0.4),
			new Vector3d(0.4, 0.2, 0.4));
	private static final double MAX_REACH_DISTANCE = 6.0;

	private final VirtualKeyboard keyboard = new VirtualKeyboard();
	// private boolean firstTick = true;

	private EntityCollider collider;

	private AttackButtonHandler attackHandler;
	private UseButtonHandler useHandler;

	@Override
	public void start(CraftGameClient ctx) {
		this.collider = new EntityCollider(PLAYER_COLLISION_BOX, ctx.state.world);
		this.attackHandler = new AttackButtonHandler(ctx);
		this.useHandler = new UseButtonHandler(ctx);

	}

	@Override
	public void update(CraftGameClient ctx) {
		this.updateKeyboardState(ctx);
		this.updateRotation(ctx);
		this.updateRenderPosition(ctx);
		this.updatePickResult(ctx);
		this.tickMouseButton(ctx);

	}

	private void updateKeyboardState(CraftGameClient ctx) {
		keyboard.forward = ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_W);
		keyboard.backward = ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_S);
		keyboard.leftward = ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_A);
		keyboard.rightward = ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_D);
		keyboard.wantDash = ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_R);
		keyboard.wantFly = ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_SPACE);
		keyboard.wantSneak = ctx.state.keyBindings.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT);

	}

	private void updateRotation(CraftGameClient ctx) {
		Display display = ctx.getDisplay();
		ctx.state.player.rotX -= display.getCursorDY() * 0.1; // TODO: Into key binding or something like that for the
																// "abstract" layer
		ctx.state.player.rotY += display.getCursorDX() * 0.1;
		ctx.state.player.rotX = Math.min(ctx.state.player.rotX, 89.0); // TODO: Why isn't the view doing smooth
																		// rotation?
		ctx.state.player.rotX = Math.max(ctx.state.player.rotX, -89.0);
		// System.out.println(ctx.state.player.rotX + ", " + ctx.state.player.rotY);

	}

	private void updateRenderPosition(CraftGameClient ctx) {
		Vector positionOnRender = ctx.state.player.positionOnRender;
		Vector3d lastTickPosition = ctx.state.player.lastTickPosition;
		Vector3d recentTickPosition = ctx.state.player.position;
		positionOnRender.x = lastTickPosition.x + (recentTickPosition.x - lastTickPosition.x) * ctx.state.partialTick;
		positionOnRender.y = lastTickPosition.y + (recentTickPosition.y - lastTickPosition.y) * ctx.state.partialTick;
		positionOnRender.z = lastTickPosition.z + (recentTickPosition.z - lastTickPosition.z) * ctx.state.partialTick;
		positionOnRender.rotX = ctx.state.player.rotX;
		positionOnRender.rotY = ctx.state.player.rotY;

	}

	private void updatePickResult(CraftGameClient ctx) {
		Vector renderPos = ctx.state.player.positionOnRender;
		ctx.state.player.pickResult = PickCollider.pick(renderPos, MAX_REACH_DISTANCE, ctx.state.world);

	}

	private void tickMouseButton(CraftGameClient ctx) {
		attackHandler.tick(ctx);
		useHandler.tick(ctx);

	}

	@Override
	public void tick(CraftGameClient ctx) {
		this.movePlayer(ctx);

		// firstTick = false;

	}

	private void movePlayer(CraftGameClient ctx) {
		Vector3d position = ctx.state.player.position;
		Vector3d velocity = ctx.state.player.velocity;

		ctx.state.player.lastTickPosition.x = position.x;
		ctx.state.player.lastTickPosition.y = position.y;
		ctx.state.player.lastTickPosition.z = position.z;

		Vector3f acceleration = new Vector3f();
		float speed = 0.3f;

		if (keyboard.wantDash) {
			speed *= 7.0f;
		}

		if (keyboard.forward) {
			acceleration.x -= speed * (float) TriFuncMap.sin(-ctx.state.player.rotY);
			acceleration.z -= speed * (float) TriFuncMap.cos(-ctx.state.player.rotY);
		} else if (keyboard.backward) {
			acceleration.x += speed * (float) TriFuncMap.sin(-ctx.state.player.rotY);
			acceleration.z += speed * (float) TriFuncMap.cos(-ctx.state.player.rotY);
		}

		if (keyboard.leftward) {
			acceleration.x -= speed * (float) TriFuncMap.sin(-ctx.state.player.rotY + 90);
			acceleration.z -= speed * (float) TriFuncMap.cos(-ctx.state.player.rotY + 90);
		} else if (keyboard.rightward) {
			acceleration.x += speed * (float) TriFuncMap.sin(-ctx.state.player.rotY + 90);
			acceleration.z += speed * (float) TriFuncMap.cos(-ctx.state.player.rotY + 90);
		}

		if (keyboard.wantFly) {
			acceleration.y += speed * 1.4f;
		} else if (keyboard.wantSneak) {
			acceleration.y -= speed * 1.4f;
		}

		velocity.x += acceleration.x;
		velocity.y += acceleration.y;
		velocity.z += acceleration.z;

		velocity.x *= 0.5;
		velocity.z *= 0.5;
		velocity.y *= 0.6;

		Vector3d delta = new Vector3d(velocity.x, velocity.y, velocity.z);
		CollideResult result = collider.collide(new Vector3d(position.x, position.y, position.z), delta, delta);
		position.x += delta.x;
		position.y += delta.y;
		position.z += delta.z;
		// System.out.println(delta);

		if (result.xCollide() != CollideResult.NO_COLLIDE) {
			velocity.x = 0;
		}
		if (result.yCollide() != CollideResult.NO_COLLIDE) {
			velocity.y = 0;
		}
		if (result.zCollide() != CollideResult.NO_COLLIDE) {
			velocity.z = 0;
		}
		System.out.println(result);

		// keyboard.forward = keyboard.backward = keyboard.leftward = keyboard.rightward
		// = keyboard.wantDash = keyboard.wantFly = keyboard.wantSneak = false;

	}

}
