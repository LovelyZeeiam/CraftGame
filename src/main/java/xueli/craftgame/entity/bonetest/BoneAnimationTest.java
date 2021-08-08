package xueli.craftgame.entity.bonetest;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Vector3f;
import xueli.craftgame.entity.EntityPlayer;
import xueli.craftgame.entity.EntityRenderer;
import xueli.game.Game;
import xueli.game.utils.Time;
import xueli.game.vector.Vector;

public class BoneAnimationTest extends Game {

	private static final float camMoveSpeed = 0.07f;
	private static final float camSensitive = 0.08f;

	private Vector3f backgroundColor = new Vector3f(0.1f, 0.1f, 0.5f);
	;

	private EntityRenderer renderer;
	private Vector camPos = new Vector(0, 0, 60);

	private EntityPlayer player;
	private BoneControlWindow controlWindow;

	public BoneAnimationTest() {
		super(800, 600, "BoneTest");

	}

	@Override
	public void onCreate() {
		this.renderer = new EntityRenderer(this);
		player = new EntityPlayer(new Vector(0, 0, 0), "LoveliZeeiam");
		this.renderer.addEntity(player);

		this.controlWindow = new BoneControlWindow(player);
		this.controlWindow.setVisible(true);

		getDisplay().setMouseGrabbed(true);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_DEPTH_TEST);

	}

	@Override
	public void onSize(int width, int height) {
		super.onSize(width, height);

		if (this.renderer != null) {
			this.renderer.size();

		}

	}

	@Override
	public void onTick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);

		this.renderer.render(camPos);

		if (getDisplay().isMouseGrabbed()) {
			if (getDisplay().isKeyDown(GLFW.GLFW_KEY_W)) {
				camPos.x -= camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
				camPos.z -= camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
			}
			if (getDisplay().isKeyDown(GLFW.GLFW_KEY_S)) {
				camPos.x += camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
				camPos.z += camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY)) * Time.deltaTime;
			}
			if (getDisplay().isKeyDown(GLFW.GLFW_KEY_A)) {
				camPos.x -= camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY + 90)) * Time.deltaTime;
				camPos.z -= camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY + 90)) * Time.deltaTime;
			}
			if (getDisplay().isKeyDown(GLFW.GLFW_KEY_D)) {
				camPos.x -= camMoveSpeed * (float) Math.sin(Math.toRadians(-camPos.rotY - 90)) * Time.deltaTime;
				camPos.z -= camMoveSpeed * (float) Math.cos(Math.toRadians(-camPos.rotY - 90)) * Time.deltaTime;
			}

			if (getDisplay().isKeyDown(GLFW.GLFW_KEY_SPACE)) {
				camPos.y += camMoveSpeed * 0.8f * Time.deltaTime;
			}

			if (getDisplay().isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
				camPos.y -= camMoveSpeed * 0.8f * Time.deltaTime;
			}

			camPos.rotX += getDisplay().getCursor_dy() * camSensitive;
			camPos.rotY += getDisplay().getCursor_dx() * camSensitive;

		}

		if (getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE)) {
			getDisplay().setMouseGrabbed(!getDisplay().isMouseGrabbed());
		}

	}

	@Override
	public void onRelease() {
		getDisplay().setMouseGrabbed(false);
		this.controlWindow.dispose();
		this.renderer.release();

	}

	public static void main(String[] args) {
		BoneAnimationTest test = new BoneAnimationTest();
		new Thread(test).start();

	}

}
