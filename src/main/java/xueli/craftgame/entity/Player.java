package xueli.craftgame.entity;

import org.lwjgl.glfw.GLFW;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockListener;
import xueli.craftgame.block.BlockResource;
import xueli.craftgame.block.Tile;
import xueli.craftgame.world.BlockPos;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.MousePicker;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.callbacks.KeyCallback;

public class Player extends Entity {

	private static BlockPos block_select, last_block_select;
	private static long placeTimeCount;
	private static AABB originAABB = new AABB(-0.5f, 0.5f, -1.5f, 0.2f, -0.5f, 0.5f);
	public int gamemode = 1;
	public int health = 20;
	public float resistant = 0.0005f;
	public float sensivity = 0.05f;

	public BlockData handBlockData;

	public Player(float x, float y, float z, World world) {
		super(x, y, z, world);
		defaultBlockData();

	}

	public Player(float x, float y, float z, float rotX, float rotY, float rotZ, World world) {
		super(x, y, z, rotX, rotY, rotZ, world);
		defaultBlockData();

	}

	private void defaultBlockData() {
		handBlockData = BlockResource.blockDatas.get("craftgame:dirt");

	}

	@Override
	public void tick() {
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
				Tile tile = world.getBlock(block_select.getX(), block_select.getY(), block_select.getZ());
				tile.getListener().onLeftClick(block_select.getX(), block_select.getY(), block_select.getZ(), world);

				placeTimeCount = Time.thisTime;
			}

			if (display.isMouseDown(1) & block_select != null & Time.thisTime - placeTimeCount > 100) {
				Tile tile = world.getBlock(block_select.getX(), block_select.getY(), block_select.getZ());
				if (tile.getListener().onRightClick(block_select.getX(), block_select.getY(), block_select.getZ(),
						world) == BlockListener.RightClick.PLACE_BLOCK_WHEN_RIGHT_CLICK)
					world.setBlock(last_block_select, new Tile(handBlockData));
				placeTimeCount = Time.thisTime;
			}
		}

		super.updatePos();

	}

	public void pickTick() {
		block_select = null;
		MousePicker.ray(pos);
		for (float distance = 0; distance < 8; distance += 0.05f) {
			BlockPos searching_block_pos = MousePicker.getPointOnRay(distance);
			if (world.hasBlock(searching_block_pos)) {
				block_select = searching_block_pos;

				Tile tile = world.getBlock(block_select.getX(), block_select.getY(), block_select.getZ());
				tile.getListener().onLookAt(block_select.getX(), block_select.getY(), block_select.getZ(), world);

				break;
			}
			last_block_select = searching_block_pos;
		}
	}

	@Override
	public float getSpeed() {
		return 0.004f;
	}

	@Override
	public AABB getOriginAABB() {
		return originAABB;
	}

}
