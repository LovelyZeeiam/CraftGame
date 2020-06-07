package xueLi.craftGame.entity;

import org.lwjgl.glfw.GLFW;

import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.Display;
import xueLi.craftGame.utils.MousePicker;
import xueLi.craftGame.world.World;

public class Player extends Entity {

	public int gamemode = 1;
	public int health = 20;

	public BlockPos blockPointed;

	public float resistant = 0.0005f;
	public float sensivity = 0.05f;

	private final HitBox hitbox = new HitBox(-0.2f, -1.8f, -0.2f, 0.2f, 0.2f, 0.2f);

	public Player(float x, float y, float z) {
		super(x, y, z);
		// this.attrib.box = hitbox;
	}

	public Player(float x, float y, float z, float rotX, float rotY, float rotZ) {
		super(x, y, z, rotX, rotY, rotZ);
	}

	private static BlockPos block_select, last_block_select;
	private static long placeTimeCount;

	@Override
	public void tick(World world) {
		speed.x = speed.y = speed.z = 0;

		if (Display.mouseGrabbed) {
			if (Display.isKeyDown(GLFW.GLFW_KEY_W)) {
				if (Display.isKeyDown(GLFW.GLFW_KEY_R)) {
					speed.x -= this.getSpeed() * 2f * (float) Math.sin(Math.toRadians(-pos.rotY));
					speed.z -= this.getSpeed() * 2f * (float) Math.cos(Math.toRadians(-pos.rotY));
				} else {
					speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
					speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
				}
			}
			if (Display.isKeyDown(GLFW.GLFW_KEY_S)) {
				speed.x += this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
				speed.z += this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
			}
			if (Display.isKeyDown(GLFW.GLFW_KEY_A)) {
				speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY + 90));
				speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY + 90));
			}
			if (Display.isKeyDown(GLFW.GLFW_KEY_D)) {
				speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY - 90));
				speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY - 90));
			}

			if (Display.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				speed.y += this.getSpeed() * 0.5f;
			}

			if (Display.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
				speed.y -= this.getSpeed() * 0.5f;
			}

			pos.rotX -= Display.mouseDY * sensivity;
			pos.rotY += Display.mouseDX * sensivity;

			if (Display.isMouseDown(0) & block_select != null & Display.currentTime - placeTimeCount > 100) {
				world.setBlock(block_select, null);
				placeTimeCount = Display.currentTime;
			}

			if (Display.isMouseDown(1) & block_select != null & Display.currentTime - placeTimeCount > 100) {
				world.setBlock(last_block_select, 1);
				placeTimeCount = Display.currentTime;
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
		return 0.01f;
	}

	@Override
	public HitBox getOriginHitBox() {
		return hitbox;
	}

}
