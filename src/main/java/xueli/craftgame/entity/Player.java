package xueli.craftgame.entity;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import com.flowpowered.nbt.ByteTag;

import xueli.craftgame.block.BlockFace;
import xueli.craftgame.block.BlockTags;
import xueli.craftgame.inventory.Inventory;
import xueli.craftgame.world.Dimension;
import xueli.craftgame.world.Tile;
import xueli.game.Game;
import xueli.game.display.Display;
import xueli.game.physics.AABB;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.math.MousePicker;
import xueli.game.vector.Vector;

public class Player {

	private static final float MAX_TOUCH = 8.0f;

	Vector pos = new Vector(0, 30, 0);
	Vector3f speed = new Vector3f();
	private Vector3f acceleration = new Vector3f();
	
	boolean onGround = true;

	private Dimension dimension;
	private WorldCollider collider;
	
	private Display display;

	private Inventory inventory;

	public Player(Dimension dimension) {
		this.display = Game.INSTANCE_GAME.getDisplay();
		this.dimension = dimension;
		this.collider = new WorldCollider(dimension);

		if(dimension != null)
			this.inventory = new Inventory(dimension.getBlocks());

	}

	private long lastTimeOperationBlock = Time.thisTime;
	
	public void tick() {
		if (display.isMouseGrabbed()) {
			/**
			 * Mouse Pick
			 */
			Vector3i lastSelectedBlock = null, selectedBlock = null;
			byte place_block_face_to = -1, place_block_part = -1;
			Vector3f lastRayVector = null;

			MousePicker picker = new MousePicker(pos, MatrixHelper.lastTimeProjMatrix, MatrixHelper.lastTimeViewMatrix);
			for (float d = 0; d <= MAX_TOUCH; d += 0.1f) {
				Vector3f p = picker.getPointOnRay(d);
				Vector3i pb = new Vector3i(p);

				if (dimension.getBlock(pb.getX(), pb.getY(), pb.getZ()) != null) {
					selectedBlock = new Vector3i(p);
					lastSelectedBlock = lastRayVector != null ? new Vector3i(lastRayVector) : null;

					Tile tile = dimension.getBlock(selectedBlock.getX(), selectedBlock.getY(), selectedBlock.getZ());
					tile.getBase().getListener().onLookAt(selectedBlock.getX(), selectedBlock.getY(),
							selectedBlock.getZ(), tile, dimension, this);

					if (lastRayVector != null) {
						if (lastRayVector.getY() - lastSelectedBlock.getY() > 0.5f)
							place_block_part = BlockFace.PART_UP;
						else
							place_block_part = BlockFace.PART_DOWN;
					}

					break;
				}
				lastRayVector = p;
			}

			if (pos.rotY >= 45 && pos.rotY <= 135)
				place_block_face_to = BlockFace.LEFT;
			else if (pos.rotY > 135 && pos.rotY < 225)
				place_block_face_to = BlockFace.FRONT;
			else if (pos.rotY >= 225 && pos.rotY <= 315)
				place_block_face_to = BlockFace.RIGHT;
			else
				place_block_face_to = BlockFace.BACK;

			// System.out.println("[Player] " +
			// BlockFace.getPartDescription(place_block_part) + ", " +
			// BlockFace.getFacingDescription(place_block_face_to));

			/**
			 * Player move
			 */
			if (display.isKeyDown(GLFW.GLFW_KEY_W)) {
				if (display.isKeyDown(GLFW.GLFW_KEY_R)) {
					acceleration.x -= this.getSpeed() * 4f * (float) Math.sin(Math.toRadians(-pos.rotY));
					acceleration.z -= this.getSpeed() * 4f * (float) Math.cos(Math.toRadians(-pos.rotY));
				} else {
					acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
					acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
				}
			}
			if (display.isKeyDown(GLFW.GLFW_KEY_S)) {
				acceleration.x += this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
				acceleration.z += this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
			}
			if (display.isKeyDown(GLFW.GLFW_KEY_A)) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY + 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY + 90));
			}
			if (display.isKeyDown(GLFW.GLFW_KEY_D)) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY - 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY - 90));
			}

			if (display.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				//acceleration.y += 1500;
				acceleration.y += this.getSpeed() * 2.0f;
				
			}

			if (display.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
				acceleration.y -= this.getSpeed() * 2.0f;
			}
			
			pos.rotX += display.getCursor_dy() * 0.08f;
			pos.rotY += display.getCursor_dx() * 0.08f;

			if (pos.rotY > 360) {
				pos.rotY -= 360;
			} else if (pos.rotY < 0) {
				pos.rotY += 360;
			}

			if (pos.rotX > 90)
				pos.rotX = 90;
			else if (pos.rotX < -90)
				pos.rotX = -90;

			if (display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
				if (selectedBlock != null && Time.thisTime - lastTimeOperationBlock > 500) {
					dimension.setBlock(selectedBlock.getX(), selectedBlock.getY(), selectedBlock.getZ(), null);
					lastTimeOperationBlock = Time.thisTime;
				}
			} else if (display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
				if (lastSelectedBlock != null && Time.thisTime - lastTimeOperationBlock > 500) {
					Tile tile = new Tile(this.inventory.getChosenBase());
					if (this.inventory.getChosenBase().getTags().contains(BlockTags.HAS_DIFFERENT_DIRECTION))
						tile.getTags().put(new ByteTag(BlockTags.TAG_NAME_FACE_TO, place_block_face_to));
					if (this.inventory.getChosenBase().getTags().contains(BlockTags.HAS_PART_UP_AND_PART_DOWN))
						tile.getTags().put(new ByteTag(BlockTags.TAG_NAME_PART, place_block_part));

					dimension.setBlock(lastSelectedBlock.getX(), lastSelectedBlock.getY(), lastSelectedBlock.getZ(),
							tile);
					lastTimeOperationBlock = Time.thisTime;
				}

			} else {
				lastTimeOperationBlock = 0;
			}

		}
	
		// TODO: When deltaTime increase, player will jump higher
		
		speed.x += acceleration.x * Time.deltaTime / 1000.0f;
		speed.y += acceleration.y * Time.deltaTime / 1000.0f;
		speed.z += acceleration.z * Time.deltaTime / 1000.0f;
		
		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime / 1000.0f, speed.y * Time.deltaTime / 1000.0f,
				speed.z * Time.deltaTime / 1000.0f);
		//pos.x += deltaPos.x;
		//pos.y += deltaPos.y;
		//pos.z += deltaPos.z;
		onGround = false;
		collider.entityCollide(this, deltaPos);
		
		speed.x *= 0.8f;
		speed.y *= 0.9f;
		speed.z *= 0.8f;
		
		acceleration.x = acceleration.y = acceleration.z = 0;
		//acceleration.y -= 98.0f;
		
		this.inventory.tick();

	}

	public void close() {
		this.inventory.close();

	}

	public float getSpeed() {
		return 80.0f;
	}

	public AABB getOriginAABB() {
		return new AABB(-0.5f, 0.5f, -1.5f, 0.5f, -0.5f, 0.5f);
	}

	public Vector getPos() {
		return pos;
	}

	public Inventory getInventory() {
		return inventory;
	}

}
