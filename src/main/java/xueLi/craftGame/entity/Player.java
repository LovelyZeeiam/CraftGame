package xueLi.craftGame.entity;

import org.lwjgl.glfw.GLFW;

import xueLi.craftGame.block.Tile;
import xueLi.craftGame.world.BlockPos;
import xueLi.craftGame.world.World;
import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.MousePicker;
import xueLi.gamengine.utils.Time;
import xueLi.gamengine.utils.callbacks.KeyCallback;

public class Player extends Entity {

	public int gamemode = 1;
	public int health = 20;

	public BlockPos blockPointed;

	public float resistant = 0.0005f;
	public float sensivity = 0.05f;

	public Player(float x, float y, float z) {
		super(x, y, z);

	}

	public Player(float x, float y, float z, float rotX, float rotY, float rotZ) {
		super(x, y, z, rotX, rotY, rotZ);
	}

	private static BlockPos block_select, last_block_select;
	private static long placeTimeCount;

	@Override
	public void tick(World world) {
		speed.x = speed.y = speed.z = 0;

		Display display = Display.currentDisplay;

		if (display.mouseGrabbed) {
			if (KeyCallback.keys[GLFW.GLFW_KEY_W]) {
				if (KeyCallback.keys[GLFW.GLFW_KEY_R]) {
					speed.x -= this.getSpeed() * 2f * (float) Math.sin(Math.toRadians(-pos.rotY));
					speed.z -= this.getSpeed() * 2f * (float) Math.cos(Math.toRadians(-pos.rotY));
				} else {
					speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
					speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
				}
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_S]) {
				speed.x += this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
				speed.z += this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_A]) {
				speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY + 90));
				speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY + 90));
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_D]) {
				speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY - 90));
				speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY - 90));
			}

			if (KeyCallback.keys[GLFW.GLFW_KEY_SPACE]) {
				speed.y += this.getSpeed() * 0.5f;
			}

			if (KeyCallback.keys[GLFW.GLFW_KEY_LEFT_SHIFT]) {
				speed.y -= this.getSpeed() * 0.5f;
			}

			if (display.isMouseDown(0) & block_select != null & Time.thisTime - placeTimeCount > 100) {
				world.setBlock(block_select, (Tile) null);
				placeTimeCount = Time.thisTime;
			}

			if (display.isMouseDown(1) & block_select != null & Time.thisTime - placeTimeCount > 100) {
				world.setBlock(last_block_select, "dirt");
				placeTimeCount = Time.thisTime;
			}
		}

		super.updatePos(world);

	}

	public void pickTick(World world) {
		block_select = null;
		MousePicker.ray(pos);
		for (float distance = 0; distance < 8; distance += 0.05f) {
			BlockPos searching_block_pos = MousePicker.getPointOnRay(distance);
			if (world.hasBlock(searching_block_pos)) {
				block_select = searching_block_pos;
				break;
			}
			last_block_select = searching_block_pos;
		}
	}

	@Override
	public float getSpeed() {
		return 0.008f;
	}

}
