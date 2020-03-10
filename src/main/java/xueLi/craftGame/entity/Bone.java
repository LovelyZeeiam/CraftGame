package xueLi.craftGame.entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import xueLi.craftGame.entity.renderer.EntityRenderer;
import xueLi.craftGame.entity.renderer.RenderArgs;
import xueLi.craftGame.utils.Vector;

public class Bone {

	public int id;
	public float[] vertices = new float[24];
	public float[] rotPoint;
	
	public Matrix4f localMatrix = new Matrix4f();
	public Matrix4f matrix = new Matrix4f();
	
	//public float[] 
	public List<Bone> children = new ArrayList<Bone>();
	
	public float rotX = 0,rotY = 0,rotZ = 0;
	
	public List<RenderArgs> getDrawArgs(Vector pos){
		List<RenderArgs> args = new ArrayList<RenderArgs>();
		
		RenderArgs a = new RenderArgs();
		a.matrix = matrix;
		
		float[] rawVertices = vertices;
		a.vertices = new float[rawVertices.length];
		for(int m = 0;m < rawVertices.length;m++) {
			if(m % 3 == 0)
				a.vertices[m] = rawVertices[m] + pos.x;
			if(m % 3 == 1)
				a.vertices[m] = rawVertices[m] + pos.y;
			if(m % 3 == 2)
				a.vertices[m] = rawVertices[m] + pos.z;
		}
		args.add(a);
		
		for(Bone c:children) {
			args.addAll(c.getDrawArgs(pos));
		}
		return args;
	}
	
	public void calculateMatrix(Matrix4f parentMatrix,Vector pos) {
		localMatrix.setIdentity();
		//TODO: Calculate the bone matrix
		Vector4f realRotPos = new Vector4f(pos.x + rotPoint[0],pos.y + rotPoint[1],pos.z + rotPoint[2],1f);
		
		Vector4f newRotPoss = Matrix4f.transform(parentMatrix, realRotPos, null);
		Vector3f newRotPos = new Vector3f(newRotPoss.x ,newRotPoss.y,newRotPoss.z);
		Vector3f negativeNewRotPos = new Vector3f(-newRotPos.x, -newRotPos.y, -newRotPos.z);
		
		localMatrix.translate(newRotPos);
		localMatrix.rotate((float) Math.toRadians(rotX), new Vector3f(1,0,0));
		rotX += 1f;
		localMatrix.rotate((float) Math.toRadians(rotY), new Vector3f(0,1,0));
		localMatrix.rotate((float) Math.toRadians(rotZ), new Vector3f(0,0,1));
		localMatrix.translate(negativeNewRotPos);
		
		matrix = Matrix4f.mul(localMatrix, parentMatrix, matrix);
		for(Bone c:children)
			c.calculateMatrix(matrix,pos);
	}
	
	
	
}
