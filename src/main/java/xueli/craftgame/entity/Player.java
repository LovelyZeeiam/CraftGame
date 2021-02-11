package xueli.craftgame.entity;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.block.Tile;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.client.inventory.Inventory;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.callbacks.KeyCallback;
import xueli.gamengine.utils.math.MousePicker;

public class Player extends Entity {

	private static final int placeBlockDuration = 200;
	private Vector3f last_time_ray_end;
	private Vector3i block_select, last_block_select;
	private byte place_block_face_to;

	private long placeTimeCount = 0;
	private static AABB originAABB = new AABB(-0.5f, 0.5f, -1.5f, 0.5f, -0.5f, 0.5f);
	public int gamemode = 1;
	public int health = 20;
	public float resistant = 0.0005f;
	public float sensivity = 0.05f;

	private Inventory inventory;

	public Player(float x, float y, float z, World world) {
		super(x, y, z, world);
		inventory = new Inventory(world, this);

	}

	public Player(float x, float y, float z, float rotX, float rotY, float rotZ, World world) {
		super(x, y, z, rotX, rotY, rotZ, world);
		inventory = new Inventory(world, this);

	}
	@Override
	public void tick() {
		Display display = Display.currentDisplay;

		if (display.mouseGrabbed) {
			if (KeyCallback.keys[GLFW.GLFW_KEY_W]) {
				if (KeyCallback.keys[GLFW.GLFW_KEY_R]) {
					acceleration.x -= this.getSpeed() * 4f * (float) Math.sin(Math.toRadians(-pos.rotY));
					acceleration.z -= this.getSpeed() * 4f * (float) Math.cos(Math.toRadians(-pos.rotY));
				} else {
					acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
					acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
				}
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_S]) {
				acceleration.x += this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
				acceleration.z += this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_A]) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY + 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY + 90));
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_D]) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY - 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY - 90));
			}

			// TODO: 跳跃不真实
			if (KeyCallback.keys[GLFW.GLFW_KEY_SPACE]) {
				//if(isOnGround)
					acceleration.y += this.getSpeed() * 2.0f;
			}

			if (KeyCallback.keys[GLFW.GLFW_KEY_LEFT_SHIFT]) {
				acceleration.y -= this.getSpeed() * 2.0f;
			}

			if (display.isMouseDown(0) & block_select != null & Time.thisTime - placeTimeCount > placeBlockDuration) {
				inventory.onLeftClick();
				placeTimeCount = Time.thisTime;
			}

			if (display.isMouseDown(1) & block_select != null & Time.thisTime - placeTimeCount > placeBlockDuration) {
				inventory.onRightClick();
				placeTimeCount = Time.thisTime;
			}
		}

		super.updatePos();

	}

	public void pickTick() {
		block_select = null;
		MousePicker.ray(pos);
		for (float distance = 0; distance < 8; distance += 0.05f) {
			Vector3f searching_ray_end = MousePicker.getPointOnRay(distance);
			Vector3i searching_block_pos = new Vector3i((int) searching_ray_end.getX(), (int) searching_ray_end.getY(), (int) searching_ray_end.getZ());
			if (world.hasBlock(searching_block_pos)) {
				block_select = searching_block_pos;
				
				float delta_x = last_time_ray_end.getX() - searching_ray_end.getX();
				float delta_z = last_time_ray_end.getZ() - searching_ray_end.getZ();
				int delta_x_int = last_block_select.getX() - searching_block_pos.getX();
				int delta_z_int = last_block_select.getZ() - searching_block_pos.getZ();
				
				if(delta_x_int > 0 && delta_z_int == 0)
					place_block_face_to = BlockFace.RIGHT;
				else if(delta_x_int < 0 && delta_z_int == 0)
					place_block_face_to = BlockFace.LEFT;
				else if(delta_z_int > 0 && delta_x_int == 0)
					place_block_face_to = BlockFace.BACK;
				else if(delta_z_int < 0 && delta_x_int == 0)
					place_block_face_to = BlockFace.FRONT;
				else if(delta_x - Math.abs(delta_z) > 0)
					place_block_face_to = BlockFace.RIGHT;
				else if(delta_x + Math.abs(delta_z) < 0)
					place_block_face_to = BlockFace.LEFT;
				else if(delta_z - Math.abs(delta_x) > 0)
					place_block_face_to = BlockFace.BACK;
				else if(delta_z + Math.abs(delta_x) < 0)
					place_block_face_to = BlockFace.FRONT;
				else
					place_block_face_to = BlockFace.FRONT;
				
				Tile tile = world.getBlock(block_select.getX(), block_select.getY(), block_select.getZ());
				if(tile.getListener() != null)
					tile.getListener().onLookAt(block_select.getX(), block_select.getY(), block_select.getZ(), world);

				break;
			}
			last_block_select = searching_block_pos;
			last_time_ray_end = searching_ray_end;
		}
	}
	
	public Vector3i getBlock_select() {
		return block_select;
	}

	public Vector3f getLast_time_ray_end() {
		return last_time_ray_end;
	}

	public void setLast_time_ray_end(Vector3f last_time_ray_end) {
		this.last_time_ray_end = last_time_ray_end;
	}

	public Vector3i getLast_block_select() {
		return last_block_select;
	}

	public void setLast_block_select(Vector3i last_block_select) {
		this.last_block_select = last_block_select;
	}

	public byte getPlace_block_face_to() {
		return place_block_face_to;
	}

	public void setPlace_block_face_to(byte place_block_face_to) {
		this.place_block_face_to = place_block_face_to;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public float getSpeed() {
		return 80.0f;
	}

	@Override
	public AABB getOriginAABB() {
		return originAABB;
	}

}
