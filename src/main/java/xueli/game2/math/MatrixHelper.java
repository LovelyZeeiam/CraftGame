package xueli.game2.math;

import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game2.Vector;

public class MatrixHelper {

	public static Matrix4f perspective(float width, float height, float fov, float near, float far) {
		Matrix4f projectionMatrix = new Matrix4f();

		float ratio = width / height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2F))) * ratio);
		float x_scale = y_scale / ratio;
		float frustum_length = far - near;

		projectionMatrix.setIdentity();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -(far + near) / frustum_length;
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * far * near) / frustum_length);
		projectionMatrix.m33 = 0;

		return projectionMatrix;
	}

	public static Matrix4f ortho(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		matrix.m00 = 2.0f / (right - left);
		matrix.m11 = 2.0f / (top - bottom);
		matrix.m22 = -2.0f / (far - near);
		matrix.m30 = -(right + left) / (right - left);
		matrix.m31 = -(top + bottom) / (top - bottom);
		matrix.m32 = -(far + near) / (far - near);

		return matrix;
	}

	public static Matrix4f rotate(float rotX, float rotY, float rotZ) {
		Matrix4f src = new Matrix4f();
		src.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(rotX), new Vector3f(1, 0, 0), src, src);
		Matrix4f.rotate((float) Math.toRadians(rotY), new Vector3f(0, 1, 0), src, src);
		Matrix4f.rotate((float) Math.toRadians(rotZ), new Vector3f(0, 0, 1), src, src);
		return src;
	}

	public static Matrix4f player(Vector camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.rotX), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.rotY), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.rotZ), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
		Vector3f negativeCamPos = new Vector3f((float) -camera.x, (float) -camera.y, (float) -camera.z);
		Matrix4f.translate(negativeCamPos, viewMatrix, viewMatrix);

		return viewMatrix;
	}

	public static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up) {
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();

		Vector3f f = new Vector3f();
		Vector3f.sub(center, eye, f);
		f.normalise();

		Vector3f s = new Vector3f();
		Vector3f.cross(f, up, s);
		s.normalise();

		Vector3f u = new Vector3f();
		Vector3f.cross(s, f, u);

		mat.m00 = s.x;
		mat.m10 = s.y;
		mat.m20 = s.z;
		mat.m01 = u.x;
		mat.m11 = u.y;
		mat.m21 = u.z;
		mat.m02 = -f.x;
		mat.m12 = -f.y;
		mat.m22 = -f.z;
		mat.m30 = -Vector3f.dot(s, eye);
		mat.m31 = -Vector3f.dot(u, eye);
		mat.m32 = Vector3f.dot(f, eye);

		return mat;
	}

}
