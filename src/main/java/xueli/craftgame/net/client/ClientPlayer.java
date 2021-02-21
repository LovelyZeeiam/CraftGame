package xueli.craftgame.net.client;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.net.event.EventPlayerMove;
import xueli.craftgame.net.event.EventPlayerTurn;
import xueli.craftgame.net.message.Message;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.callbacks.KeyCallback;
import xueli.gamengine.utils.vector.Vector;

public class ClientPlayer {

	public static final float SENSITIVITY = 0.3f;

	private final Client client;
	private final WorldLogic logic;
	private final Display display;
	private final PlayerPicker picker;

	private Vector clientPlayerPos;
	private Vector3f acceleration = new Vector3f();
	private Vector3f speed = new Vector3f();

	public ClientPlayer(WorldLogic logic, Vector playerPos) {
		this.logic = logic;
		this.client = logic.getClient();
		this.clientPlayerPos = playerPos;
		this.display = logic.getCg().getDisplay();
		this.picker = new PlayerPicker(this);

	}

	public Client getClient() {
		return client;
	}

	public WorldLogic getLogic() {
		return logic;
	}

	public Vector getClientPlayerPos() {
		return clientPlayerPos;
	}

	public void tick() {
		float dry = display.getMouseDX() * SENSITIVITY;
		float drx = -display.getMouseDY() * SENSITIVITY;

		// 先在本地处理完这类型的事件 这类型的事件可以不用等待服务器响应而相应
		clientPlayerPos.rotX += drx;
		clientPlayerPos.rotY += dry;

		EventPlayerTurn turnEvent = new EventPlayerTurn(drx, dry, client.getId(), logic.getCg().getPlayerStat());
		client.send(Message.generateEventMessage(turnEvent));

		if (display.mouseGrabbed) {
			if (KeyCallback.keys[GLFW.GLFW_KEY_W]) {
				if (KeyCallback.keys[GLFW.GLFW_KEY_R]) {
					acceleration.x -= this.getSpeed() * 4f * (float) Math.sin(Math.toRadians(-clientPlayerPos.rotY));
					acceleration.z -= this.getSpeed() * 4f * (float) Math.cos(Math.toRadians(-clientPlayerPos.rotY));
				} else {
					acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-clientPlayerPos.rotY));
					acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-clientPlayerPos.rotY));
				}
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_S]) {
				acceleration.x += this.getSpeed() * (float) Math.sin(Math.toRadians(-clientPlayerPos.rotY));
				acceleration.z += this.getSpeed() * (float) Math.cos(Math.toRadians(-clientPlayerPos.rotY));
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_A]) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-clientPlayerPos.rotY + 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-clientPlayerPos.rotY + 90));
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_D]) {
				acceleration.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-clientPlayerPos.rotY - 90));
				acceleration.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-clientPlayerPos.rotY - 90));
			}

			if (KeyCallback.keys[GLFW.GLFW_KEY_SPACE]) {
				acceleration.y += this.getSpeed() * 2.0f;
			}

			if (KeyCallback.keys[GLFW.GLFW_KEY_LEFT_SHIFT]) {
				acceleration.y -= this.getSpeed() * 2.0f;
			}

		}

		speed.x += acceleration.x * Time.deltaTime / 1000.0f;
		speed.y += acceleration.y * Time.deltaTime / 1000.0f;
		speed.z += acceleration.z * Time.deltaTime / 1000.0f;

		Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime / 1000.0f, speed.y * Time.deltaTime / 1000.0f,
				speed.z * Time.deltaTime / 1000.0f);

		clientPlayerPos.x += deltaPos.x;
		clientPlayerPos.y += deltaPos.y;
		clientPlayerPos.z += deltaPos.z;

		speed.x *= 0.8f;
		speed.y *= 0.8f;
		speed.z *= 0.8f;

		acceleration.x = acceleration.y = acceleration.z = 0;

		EventPlayerMove moveEvent = new EventPlayerMove(deltaPos.x, deltaPos.y, deltaPos.z, client.getId(),
				logic.getCg().getPlayerStat());
		client.send(Message.generateEventMessage(moveEvent));

		if (display.isMouseDown(0)) {
			picker.onLeftClick();
		}

		if (display.isMouseDown(1)) {
			picker.onRightClick();
		}

		picker.pickTick();

	}

	public float getSpeed() {
		return 80.0f;
	}

	@Override
	public String toString() {
		return "ClientPlayer [client=" + client + ", clientPlayerPos=" + clientPlayerPos + "]";
	}

}
