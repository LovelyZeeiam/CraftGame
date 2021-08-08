package xueli.craftgame.renderer.sky;

import org.lwjgl.utils.vector.Vector3f;
import xueli.craftgame.entity.Player;
import xueli.craftgame.world.Dimension;

import static org.lwjgl.opengl.GL11.glClearColor;

public class SkyRenderer {

	@SuppressWarnings("unused")
	private Dimension dimension;

	private Vector3f skyColor;
	private Vector3f sunDirection;

	public SkyRenderer(Dimension dimension) {
		this.dimension = dimension;
	}

	private void calculateSkyColor() {
		this.skyColor = new Vector3f(0.7f, 0.7f, 0.9f);

	}

	private void calculateSunDirection() {
		sunDirection = new Vector3f((float) -Math.cos(Math.toRadians(80)), (float) -Math.sin(Math.toRadians(80)), 0);

	}

	public void render(Player player) {
		calculateSkyColor();
		calculateSunDirection();

		glClearColor(skyColor.x, skyColor.y, skyColor.z, 1.0f);

	}

	public void size() {

	}

	public Vector3f getSkyColor() {
		return skyColor;
	}

	public Vector3f getSunDirection() {
		return sunDirection;
	}

}
