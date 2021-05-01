package xueli.craftgame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.block.Tile;
import xueli.craftgame.world.Dimension;
import xueli.game.Game;
import xueli.game.display.Display;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.math.MousePicker;
import xueli.game.vector.Vector;

public class Player {

	private static final float MAX_TOUCH = 8.0f;

	private Vector pos = new Vector(0, 30, 0);
	private Vector3f speed = new Vector3f();
	private Vector3f acceleration = new Vector3f();

	private Dimension dimension;
	private Display display;
	
	private Inventory inventory;

	public Player(Dimension dimension) {
		this.display = Game.INSTANCE_GAME.getDisplay();
		this.dimension = dimension;
		
		this.inventory = new Inventory(dimension.getBlocks()); 

	}

	private Vector3i lastSelectedBlock, selectedBlock;

	private long lastTimeOperationBlock = Time.thisTime;

	public void tick() {
		if (display.isMouseGrabbed()) {
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
				acceleration.y += this.getSpeed() * 2.0f;
			}

			if (display.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
				acceleration.y -= this.getSpeed() * 2.0f;
			}

			pos.rotX += display.getCursor_dy() * 0.08f;
			pos.rotY += display.getCursor_dx() * 0.08f;

			if (display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
				if (selectedBlock != null && Time.thisTime - lastTimeOperationBlock > 100) {
					dimension.setBlock(selectedBlock.getX(), selectedBlock.getY(), selectedBlock.getZ(), null);
					lastTimeOperationBlock = Time.thisTime;
				}
			} else if (display.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
				if (lastSelectedBlock != null && Time.thisTime - lastTimeOperationBlock > 100) {
					dimension.setBlock(lastSelectedBlock.getX(), lastSelectedBlock.getY(), lastSelectedBlock.getZ(),
							new Tile(this.inventory.getChosenBase()));
					lastTimeOperationBlock = Time.thisTime;
				}

			} else {
				lastTimeOperationBlock = 0;
			}

		}

		speed.x += acceleration.x * Time.deltaTime / 1000.0f;
		speed.y += acceleration.y * Time.deltaTime / 1000.0f;
		speed.z += acceleration.z * Time.deltaTime / 1000.0f;

		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime / 1000.0f, speed.y * Time.deltaTime / 1000.0f,
				speed.z * Time.deltaTime / 1000.0f);

		pos.x += deltaPos.x;
		pos.y += deltaPos.y;
		pos.z += deltaPos.z;

		speed.x *= 0.8f;
		speed.y *= 0.8f;
		speed.z *= 0.8f;

		acceleration.x = acceleration.y = acceleration.z = 0;
		
		this.inventory.tick();

	}

	public void pickTick() {
		lastSelectedBlock = selectedBlock = null;

		Vector3i lastRayVector = null;

		MousePicker picker = new MousePicker(pos, MatrixHelper.lastTimeProjMatrix, MatrixHelper.lastTimeViewMatrix);
		for (float d = 0; d <= MAX_TOUCH; d += 0.1f) {
			Vector3f p = picker.getPointOnRay(d);
			Vector3i pb = new Vector3i(p);

			if (dimension.getBlock(pb.getX(), pb.getY(), pb.getZ()) != null) {
				selectedBlock = new Vector3i(p);
				lastSelectedBlock = lastRayVector;
				break;
			}
			lastRayVector = new Vector3i(p);
		}

		// System.out.println(selectedBlock);

	}

	public float getSpeed() {
		return 80.0f;
	}

	public Vector getPos() {
		return pos;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

}
