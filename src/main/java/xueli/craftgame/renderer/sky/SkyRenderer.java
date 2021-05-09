package xueli.craftgame.renderer.sky;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.world.Dimension;

import static org.lwjgl.opengl.GL11.*;

public class SkyRenderer {

	@SuppressWarnings("unused")
	private Dimension dimension;

	private Vector3f skyColor;

	public SkyRenderer(Dimension dimension) {
		this.dimension = dimension;
	}

	private void calculateSkyColor() {
		this.skyColor = new Vector3f(0.7f, 0.7f, 0.9f);

	}

	public void render() {
		calculateSkyColor();
		glClearColor(skyColor.x, skyColor.y, skyColor.z, 1.0f);

	}

	public void size() {

	}

	public Vector3f getSkyColor() {
		return skyColor;
	}

}
