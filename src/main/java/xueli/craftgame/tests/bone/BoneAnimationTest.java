package xueli.craftgame.tests.bone;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import xueli.craftgame.entity.EntityPlayer;
import xueli.craftgame.entity.EntityRenderer;
import xueli.gamengine.IGame;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.callbacks.KeyCallback;
import xueli.gamengine.utils.vector.Vector;
import xueli.utils.Logger;

public class BoneAnimationTest extends IGame {

	private static final float camMoveSpeed = 0.07f;
	private static final float camSensitive = 0.08f;

	private Vector3f backgroundColor;

	private EntityRenderer renderer;
	private Vector camPos = new Vector(0, 0, 60);

	private EntityPlayer player;
	private BoneControlWindow controlWindow;

	public BoneAnimationTest() {
		super("res/");

	}

	@Override
	protected void onCreate() {
		initAll(800, 600, "BoneTest");

		// windows 10可以获得直接有主题色作为背景
		if (System.getProperty("os.name").equals("Windows 10")) {
			long color = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER,
					"Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\History\\Colors", "ColorHistory0");
			float r = (int) ((color >> 16) & 0xFF);
			float g = (int) ((color >> 8) & 0xFF);
			float b = (int) (color & 0xFF);

			Logger.info("Windows 10! Set background color to theme color: " + r + ", " + g + ", " + b);
			backgroundColor = new Vector3f(r / 255 * 0.62f, g / 255 * 0.71f, b / 255 * 0.66f);

		} else {
			backgroundColor = new Vector3f(0.1f, 0.1f, 0.5f);

		}

		this.renderer = new EntityRenderer(this, this.getShaderResource().get("entity"));
		player = new EntityPlayer(new Vector(0, 0, 0));
		this.renderer.addEntity(player);

		this.controlWindow = new BoneControlWindow(player);
		this.controlWindow.setVisible(true);

		showDisplay();
		display.setMouseGrabbed(true);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}

	@Override
	protected void onSized() {
		GL11.glViewport(0, 0, display.getWidth(), display.getHeight());

		if (this.renderer != null) {
			this.renderer.size();

		}

	}

	@Override
	protected void onCursorPos(double dx, double dy) {

	}

	@Override
	protected void onMouseButton(int button) {

	}

	@Override
	protected void onScroll(float scroll) {

	}

	@Override
	protected void onKeyboard(int key) {
		if (key == GLFW.GLFW_KEY_ESCAPE) {
			this.postExit();
		}

	}

	@Override
	protected void onDrawFrame() {
		GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);

		this.renderer.render(camPos);

		{
			// 摄像机移动
			if (KeyCallback.keys[GLFW.GLFW_KEY_W]) {
				camPos.x -= camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
				camPos.z -= camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_S]) {
				camPos.x += camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
				camPos.z += camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_A]) {
				camPos.x -= camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY + 90)) * Time.deltaTime;
				camPos.z -= camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY + 90)) * Time.deltaTime;
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_D]) {
				camPos.x -= camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY - 90)) * Time.deltaTime;
				camPos.z -= camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY - 90)) * Time.deltaTime;
			}
			if (KeyCallback.keys[GLFW.GLFW_KEY_SPACE]) {
				camPos.y += camMoveSpeed * 0.8f * Time.deltaTime;
			}

			if (KeyCallback.keys[GLFW.GLFW_KEY_LEFT_SHIFT]) {
				camPos.y -= camMoveSpeed * 0.8f * Time.deltaTime;
			}

			// 摄像机视角
			float dry = display.getMouseDX() * camSensitive;
			float drx = -display.getMouseDY() * camSensitive;

			camPos.rotX += drx;
			camPos.rotY += dry;

		}

		runQueueList();

	}

	@Override
	protected void onExit() {
		display.setMouseGrabbed(false);
		this.controlWindow.dispose();
		this.renderer.release();
		releaseAll();

	}

	public static void main(String[] args) {
		BoneAnimationTest test = new BoneAnimationTest();
		new Thread(test).start();

	}

}
