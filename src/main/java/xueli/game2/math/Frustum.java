package xueli.game2.math;

import org.lwjgl.utils.vector.Matrix4f;

public class Frustum {
	
	private final float[][] frustumPlane = new float[6][4];
	
	public Frustum(Matrix4f projMatrix, Matrix4f viewMatrix) {
		this.calcFrustumPlane(Matrix4f.mul(projMatrix, viewMatrix, null));
		
	}
	
	private void calcFrustumPlane(Matrix4f matrix) {
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
	
	public boolean isPointInFrustum(float x, float y, float z) {
		for (int p = 0; p < 6; p++) {
			if (frustumPlane[p][0] * x + frustumPlane[p][1] * y + frustumPlane[p][2] * z + frustumPlane[p][3] <= 0)
				return false;
		}
		return true;
	}

	public boolean isCubeInFrustum(double x1, double y1, double z1, double x2, double y2, double z2) {
		for (int p = 0; p < 6; p++) {
			if (frustumPlane[p][0] * x1 + frustumPlane[p][1] * y1 + frustumPlane[p][2] * z1 + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x2 + frustumPlane[p][1] * y1 + frustumPlane[p][2] * z1 + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x1 + frustumPlane[p][1] * y2 + frustumPlane[p][2] * z1 + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x2 + frustumPlane[p][1] * y2 + frustumPlane[p][2] * z1 + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x1 + frustumPlane[p][1] * y1 + frustumPlane[p][2] * z2 + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x2 + frustumPlane[p][1] * y1 + frustumPlane[p][2] * z2 + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x1 + frustumPlane[p][1] * y2 + frustumPlane[p][2] * z2 + frustumPlane[p][3] > 0)
				continue;
			if (frustumPlane[p][0] * x2 + frustumPlane[p][1] * y2 + frustumPlane[p][2] * z2 + frustumPlane[p][3] > 0)
				continue;
			return false;
		}
		return true;
	}

//	public boolean isChunkInFrustum(int x, int y, int z) {
//		for (int p = 0; p < 6; p++) {
//			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * y * 16 + frustumPlane[p][2] * z * 16
//					+ frustumPlane[p][3] > 0)
//				continue;
//			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * y * 16 + frustumPlane[p][2] * z * 16
//					+ frustumPlane[p][3] > 0)
//				continue;
//			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * (y + 1) * 16 + frustumPlane[p][2] * z * 16
//					+ frustumPlane[p][3] > 0)
//				continue;
//			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * (y + 1) * 16 + frustumPlane[p][2] * z * 16
//					+ frustumPlane[p][3] > 0)
//				continue;
//			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * y * 16 + frustumPlane[p][2] * (z + 1) * 16
//					+ frustumPlane[p][3] > 0)
//				continue;
//			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * y * 16 + frustumPlane[p][2] * (z + 1) * 16
//					+ frustumPlane[p][3] > 0)
//				continue;
//			if (frustumPlane[p][0] * x * 16 + frustumPlane[p][1] * (y + 1) * 16 + frustumPlane[p][2] * (z + 1) * 16
//					+ frustumPlane[p][3] > 0)
//				continue;
//			if (frustumPlane[p][0] * (x + 1) * 16 + frustumPlane[p][1] * (y + 1) * 16
//					+ frustumPlane[p][2] * (z + 1) * 16 + frustumPlane[p][3] > 0)
//				continue;
//			return false;
//		}
//		return true;
//	}
	
}
