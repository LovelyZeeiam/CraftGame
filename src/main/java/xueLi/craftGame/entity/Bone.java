package xueLi.craftGame.entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.entity.renderer.RenderArgs;
import xueLi.craftGame.utils.Vector;

public class Bone {

	public int id;
	public float[] vertices = new float[24];
	public float[] rotPoint;
	public float[] rawOffset;

	public Matrix4f localMatrix = new Matrix4f();
	public Matrix4f matrix = new Matrix4f();

	// public float[]
	public List<Bone> children = new ArrayList<Bone>();

	public float rotX = 0, rotY = 0, rotZ = 0;

	public List<RenderArgs> getDrawArgs(Vector pos) {
		List<RenderArgs> args = new ArrayList<RenderArgs>();

		RenderArgs a = new RenderArgs();
		a.matrix = matrix;

		float[] rawVertices = vertices;
		a.vertices = new float[rawVertices.length];
		for (int m = 0; m < rawVertices.length; m++) {
			a.vertices[m] = rawVertices[m];
		}
		a.color = new Vector3f(1f / id, 1f / id / 2, 1f / id / 3);
		args.add(a);

		for (Bone c : children) {
			args.addAll(c.getDrawArgs(pos));
		}
		return args;
	}

	public void calculateMatrix(Matrix4f parentMatrix) {
		localMatrix.setIdentity();

		// Matrix4f transMatrix = new Matrix4f();
		// transMatrix.translate(new Vector3f(rawOffset[0],rawOffset[1],rawOffset[2]));

		Matrix4f rotMatrix = new Matrix4f();
		rotMatrix.setIdentity();
		rotX += 1;
		rotMatrix.rotate((float) Math.toRadians(rotX), new Vector3f(1, 0, 0));
		rotMatrix.rotate((float) Math.toRadians(rotY), new Vector3f(0, 1, 0));
		rotMatrix.rotate((float) Math.toRadians(rotZ), new Vector3f(0, 0, 1));

		Matrix4f transMatrix = new Matrix4f();
		transMatrix.setIdentity();
		transMatrix.translate(new Vector3f(rawOffset[0], rawOffset[1], rawOffset[2]));

		localMatrix = Matrix4f.mul(transMatrix, rotMatrix, null);

		matrix = Matrix4f.mul(parentMatrix, localMatrix, null);

		for (Bone c : children)
			c.calculateMatrix(matrix);
	}

}
