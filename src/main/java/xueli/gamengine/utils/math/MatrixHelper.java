package xueli.gamengine.utils.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import xueli.gamengine.utils.vector.Vector;

public class MatrixHelper {

	public static Matrix4f initMatrix = new Matrix4f();

	static {
		initMatrix.setIdentity();

	}

	public static Matrix4f lastTimeProjMatrix, lastTimeViewMatrix;
	public static float[][] frustumPlane = new float[6][4];

	public static Matrix4f perspecive(float width, float height, float fov, float near, float far) {
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

		lastTimeProjMatrix = projectionMatrix;
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
		Vector3f nagativeCamPos = new Vector3f(-camera.x, -camera.y, -camera.z);
		Matrix4f.translate(nagativeCamPos, viewMatrix, viewMatrix);

		lastTimeViewMatrix = viewMatrix;
		return viewMatrix;
	}

	public static void calculateFrustumPlane() {
		Matrix4f matrix = Matrix4f.mul(lastTimeProjMatrix, lastTimeViewMatrix, null);

		double temp;
		frustumPlane[0][0] = matrix.m03 - matrix.m00;
		frustumPlane[0][1] = matrix.m13 - matrix.m10;
		frustumPlane[0][2] = matrix.m23 - matrix.m20;
		frustumPlane[0][3] = matrix.m33 - matrix.m30;
		temp = Math.sqrt(frustumPlane[0][0] * frustumPlane[0][0] + frustumPlane[0][1] * frustumPlane[0][1]
				+ frustumPlane[0][2] * frustumPlane[0][2]);
		frustumPlane[0][0] /= temp;
		frustumPlane[0][1] /= temp;
		frustumPlane[0][2] /= temp;
		frustumPlane[0][3] /= temp;

		frustumPlane[1][0] = matrix.m03 + matrix.m00;
		frustumPlane[1][1] = matrix.m13 + matrix.m10;
		frustumPlane[1][2] = matrix.m23 + matrix.m20;
		frustumPlane[1][3] = matrix.m33 + matrix.m30;
		temp = Math.sqrt(frustumPlane[1][0] * frustumPlane[1][0] + frustumPlane[1][1] * frustumPlane[1][1]
				+ frustumPlane[1][2] * frustumPlane[1][2]);
		frustumPlane[1][0] /= temp;
		frustumPlane[1][1] /= temp;
		frustumPlane[1][2] /= temp;
		frustumPlane[1][3] /= temp;

		frustumPlane[2][0] = matrix.m03 + matrix.m01;
		frustumPlane[2][1] = matrix.m13 + matrix.m11;
		frustumPlane[2][2] = matrix.m23 + matrix.m21;
		frustumPlane[2][3] = matrix.m33 + matrix.m31;
		temp = Math.sqrt(frustumPlane[2][0] * frustumPlane[2][0] + frustumPlane[2][1] * frustumPlane[2][1]
				+ frustumPlane[2][2] * frustumPlane[2][2]);
		frustumPlane[2][0] /= temp;
		frustumPlane[2][1] /= temp;
		frustumPlane[2][2] /= temp;
		frustumPlane[2][3] /= temp;

		frustumPlane[3][0] = matrix.m03 - matrix.m01;
		frustumPlane[3][1] = matrix.m13 - matrix.m11;
		frustumPlane[3][2] = matrix.m23 - matrix.m21;
		frustumPlane[3][3] = matrix.m33 - matrix.m31;
		temp = Math.sqrt(frustumPlane[3][0] * frustumPlane[3][0] + frustumPlane[3][1] * frustumPlane[3][1]
				+ frustumPlane[3][2] * frustumPlane[3][2]);
		frustumPlane[3][0] /= temp;
		frustumPlane[3][1] /= temp;
		frustumPlane[3][2] /= temp;
		frustumPlane[3][3] /= temp;

		frustumPlane[4][0] = matrix.m03 - matrix.m02;
		frustumPlane[4][1] = matrix.m13 - matrix.m12;
		frustumPlane[4][2] = matrix.m23 - matrix.m22;
		frustumPlane[4][3] = matrix.m33 - matrix.m32;
		temp = Math.sqrt(frustumPlane[4][0] * frustumPlane[4][0] + frustumPlane[4][1] * frustumPlane[4][1]
				+ frustumPlane[4][2] * frustumPlane[4][2]);
		frustumPlane[4][0] /= temp;
		frustumPlane[4][1] /= temp;
		frustumPlane[4][2] /= temp;
		frustumPlane[4][3] /= temp;

		frustumPlane[5][0] = matrix.m03 + matrix.m02;
		frustumPlane[5][1] = matrix.m13 + matrix.m12;
		frustumPlane[5][2] = matrix.m23 + matrix.m22;
		frustumPlane[5][3] = matrix.m33 + matrix.m32;
		temp = Math.sqrt(frustumPlane[5][0] * frustumPlane[5][0] + frustumPlane[5][1] * frustumPlane[5][1]
				+ frustumPlane[5][2] * frustumPlane[5][2]);
		frustumPlane[5][0] /= temp;
		frustumPlane[5][1] /= temp;
		frustumPlane[5][2] /= temp;
		frustumPlane[5][3] /= temp;

	}

	public static boolean isPointInFrustum(float x, float y, float z) {
		for (int p = 0; p < 6; p++) {
			if (frustumPlane[p][0] * x + frustumPlane[p][1] * y + frustumPlane[p][2] * z + frustumPlane[p][3] <= 0)
				return false;
		}
		return true;
	}

	public static boolean isBlockInFrustum(int x, int y, int z) {
		for (int p = 0; p < 6; p++) {
			if (frustumPlane[p][0] * x + frustumPlane[p][1] * y + frustumPlane[p][2] * z + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) + frustumPlane[p][1] * y + frustumPlane[p][2] * z + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x + frustumPlane[p][1] * (y + 1) + frustumPlane[p][2] * z + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) + frustumPlane[p][1] * (y + 1) + frustumPlane[p][2] * z
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x + frustumPlane[p][1] * y + frustumPlane[p][2] * (z + 1) + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) + frustumPlane[p][1] * y + frustumPlane[p][2] * (z + 1)
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x + frustumPlane[p][1] * (y + 1) + frustumPlane[p][2] * (z + 1)
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) + frustumPlane[p][1] * (y + 1) + frustumPlane[p][2] * (z + 1)
					+ frustumPlane[p][3] > 0)
				continue;
			return false;
		}
		return true;
	}

	public static boolean isChunkInFrustum(int x, int heightMap, int z) {
		for (int p = 0; p < 6; p++) {
			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * 0 + frustumPlane[p][2] * z * 16
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * 0 + frustumPlane[p][2] * z * 16
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * heightMap + frustumPlane[p][2] * z * 16
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * heightMap + frustumPlane[p][2] * z * 16
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * 0 + frustumPlane[p][2] * (z + 1) * 16
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * 0 + frustumPlane[p][2] * (z + 1) * 16
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * heightMap + frustumPlane[p][2] * (z + 1) * 16
					+ frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * heightMap + frustumPlane[p][2] * (z + 1) * 16
					+ frustumPlane[p][3] > 0)
				continue;
			return false;
		}
		return true;
	}

}
