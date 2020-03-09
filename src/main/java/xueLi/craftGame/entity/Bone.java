package xueLi.craftGame.entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

public class Bone {

	public int id;
	public float[] vertices = new float[24];
	
	public Matrix4f localMatrix = new Matrix4f();
	public Matrix4f matrix = new Matrix4f();
	
	//public float[] 
	public List<Bone> children = new ArrayList<Bone>();
	
	public float rotX = 0,rotY = 0,rotZ = 0;
	
	public void calculateMatrix(Matrix4f parentMatrix) {
		localMatrix.setIdentity();
		//TODO: Calculate the bone matrix
		
		matrix = Matrix4f.mul(parentMatrix, localMatrix, null);
		for(Bone c:children)
			c.calculateMatrix(matrix);
	}
	
	
	
}
