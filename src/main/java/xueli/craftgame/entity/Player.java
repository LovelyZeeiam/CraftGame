package xueli.craftgame.entity;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3f;

import xueli.craftgame.inventory.Inventory;
import xueli.craftgame.world.Dimension;
import xueli.game.Game;
import xueli.game.display.Display;
import xueli.game.physics.AABB;
import xueli.game.utils.Time;
import xueli.game.vector.Vector;

public class Player {

	private static final float MAX_TOUCH = 8.0f;

	Vector pos = new Vector(0, 30, 0);
	Vector3f speed = new Vector3f();
	private Vector3f acceleration = new Vector3f();

	boolean onGround = true;

	private Dimension dimension;
	private PlayerPicker picker;
	private WorldCollider collider;

	private Display display;
	private PlayerProvider provider;

	Inventory inventory;

	public Player(Dimension dimension) {
		this.display = Game.INSTANCE_GAME.getDisplay();
		this.dimension = dimension;
		this.collider = new WorldCollider(dimension);
		this.provider = new PlayerProvider(this);

		if (dimension != null) {
			this.inventory = new Inventory(this, dimension.getBlocks());
			this.picker = new PlayerPicker(this, MAX_TOUCH);

		}

		this.provider.load();

	}

	private long lastTimeOperationBlock = Time.thisTime;

	public void tick() {
		if (display.isMouseGrabbed()) {
			/**
			 * mouse picker
			 */
			this.picker.tick();

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
				// acceleration.y += 1500;
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
				if (Time.thisTime - lastTimeOperationBlock > 500) {
					inventory.leftClick(this);
					lastTimeOperationBlock = Time.thisTime;
				}
			} else if (display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
				if (Time.thisTime - lastTimeOperationBlock > 500) {
					inventory.rightClick(this);
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
		// pos.x += deltaPos.x;
		// pos.y += deltaPos.y;
		// pos.z += deltaPos.z;
		onGround = false;
		collider.entityCollide(this, deltaPos);

		speed.x *= 0.8f;
		speed.y *= 0.9f;
		speed.z *= 0.8f;

		acceleration.x = acceleration.y = acceleration.z = 0;
		// acceleration.y -= 98.0f;

		this.inventory.tick();

	}

	public void close() {
		this.provider.save();
		this.inventory.close();

	}

	public float getSpeed() {
		return 80.0f;
	}

	public AABB getOriginAABB() {
		/**
		 * When it comes to 0.4f in x, z value, the collision will not work perfectly.
		 */
		return new AABB(-0.36f, 0.36f, -1.51f, 0.1f, -0.36f, 0.36f);
	}

	public Vector getPos() {
		return pos;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public PlayerPicker getPicker() {
		return picker;
	}

}
