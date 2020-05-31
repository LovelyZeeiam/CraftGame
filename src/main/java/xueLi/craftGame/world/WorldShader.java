package xueLi.craftGame.world;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.utils.Display;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.Shader;

public class WorldShader extends Shader {

	public WorldShader() {
		super("res/shaders/world.vert", "res/shaders/world.frag");
	}

	private int loc_projMatrix, loc_viewMatrix, loc_skyColor;

	@Override
	public void prepare() {
		loc_projMatrix = super.getUnifromLocation("projMatrix");
		loc_viewMatrix = super.getUnifromLocation("viewMatrix");
		loc_skyColor = super.getUnifromLocation("skyColor");

		updateProjMatrix();

	}

	public void updateProjMatrix() {
		super.use();
		super.setUniformMatrix(loc_projMatrix,
				GLHelper.perspecive(Display.d_width, Display.d_height, 70.0f, 0.1f, 1000.0f));
		super.unbind();
	}

	public void setProjMatrix(float width, float height, float fov) {
		Matrix4f projMatrix = GLHelper.perspecive(width, height, fov, 0.1f, 1000.0f);
		super.setUniformMatrix(loc_projMatrix, projMatrix);
	}

	public void setViewMatrix(Player cam) {
		Matrix4f viewMatrix = GLHelper.player(cam);
		super.setUniformMatrix(loc_viewMatrix, viewMatrix);
	}

	public void setSkyColor(Vector3f color) {
		super.setUniformVector3(loc_skyColor, color);
	}

}
