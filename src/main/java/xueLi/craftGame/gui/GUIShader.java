package xueLi.craftGame.gui;

import org.lwjgl.util.vector.Matrix4f;

import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.Shader;

public class GUIShader extends Shader {

	public GUIShader() {
		super("res/shaders/gui.vert", "res/shaders/gui.frag");
	}
	
	private int loc_matrix;

	@Override
	public void prepare() {
		loc_matrix = super.getUnifromLocation("matrix");

	}
	
	public void updateOrthoMatrix() {
		Matrix4f orthoMatrix4f = GLHelper.ortho();
		super.setUniformMatrix(loc_matrix, orthoMatrix4f);
	}

}
