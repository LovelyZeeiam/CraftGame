package xueLi.craftGame.shader;

import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.GLHelper;

public class WorldShader extends Shader {

	public WorldShader() {
		super("res/shader/world.vert", "res/shader/world.frag");
	}

	private int loc_projMatrix;
	private int loc_transMatrix;
	private int loc_viewMatrix;

	@Override
	public void prepare() {
		loc_projMatrix = super.getUnifromLocation("projMatrix");
		loc_transMatrix = super.getUnifromLocation("transMatrix");
		loc_viewMatrix = super.getUnifromLocation("viewMatrix");

		super.use();
		GLHelper.lastTimeProjMatrix = GLHelper.perspecive(DisplayManager.d_width, DisplayManager.d_height, 70.0f, 0.1f, 1000.0f);
		super.setUniformMatrix(loc_projMatrix, GLHelper.lastTimeProjMatrix);
		super.setUniformMatrix(loc_transMatrix,
				GLHelper.createTransformationMatrix(new Vector3f(0,0,0), 0, 0, 0, 1));
		super.unbind();

	}

	public void setProjMatrix(float width, float height, float fov) {
		GLHelper.lastTimeProjMatrix = GLHelper.perspecive(width, height, fov, 0.1f, 1000.0f);
		super.setUniformMatrix(loc_projMatrix, GLHelper.lastTimeProjMatrix);
	}

	public void setViewMatrix(Player cam) {
		GLHelper.lastTimeViewMatrix = GLHelper.player(cam);
		super.setUniformMatrix(loc_viewMatrix, GLHelper.lastTimeViewMatrix);
	}

}
