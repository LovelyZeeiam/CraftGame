package xueLi.craftGame.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import xueLi.craftGame.entity.Player;

public class GLHelper {

	public static Matrix4f lastTimeProjMatrix, lastTimeViewMatrix;
	public static float[][] frustumPlane = new float[6][4];

	public static int registerTexture(String path) {
		return registerTexture(new File(path));
	}
	
	public static int registerTexture(File file) {
		try {
			return registerTexture(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int registerTexture(InputStream stream) {
		int[] pixels = null;
		int width = 0,height = 0;
		try {
			BufferedImage image = ImageIO.read(stream);
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch(IOException e) {
			e.printStackTrace();
		}
		int[] data = new int[width * height];
		for(int i = 0;i < width*height;i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 4);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
		GLHelper.printGLError("Texture Register");
		return id;
	}

	public static void deleteTexture(int id) {
		GL11.glDeleteTextures(id);
	}

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

	public static Matrix4f player(Player player) {
		Vector camera = player.pos;
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

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
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
	
	public static float doubleToFloat(double value) {
		return new BigDecimal(String.valueOf(value)).floatValue();
	}

	public static double floatToDouble(float value) {
		return new BigDecimal(String.valueOf(value)).doubleValue();
	}

	public static int floatToInt(float value) {
		return new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_DOWN).intValue();
	}

	public static int floatToInt2(float value) {
		return (int) value;
	}

	public static long vert2ToLong(int x, int z) {
		return (long) x & 4294967295L | ((long) z & 4294967295L) << 32;
	}
	
	public static void printGLError(String state) {
		int error = GL11.glGetError();
		if (error != 0) {
			System.out.println("["+state+"] " + GLU.gluErrorString(error));
		}
	}

}
