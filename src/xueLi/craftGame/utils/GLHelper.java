package xueLi.craftGame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import xueLi.craftGame.entity.Player;

public class GLHelper {

	public static Matrix4f lastTimeProjMatrix, lastTimeViewMatrix;

	public static void clearColor(float r, float g, float b, float a) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(r, g, b, a);
	}

	public static int bindTexture(String path) {
		Texture t = null;
		try {
			t = TextureLoader.getTexture("png", new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 4);
		return t.getTextureID();
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

	public static float doubleToFloat(double value) {
		return new BigDecimal(String.valueOf(value)).floatValue();
	}

	public static double floatToDouble(float value) {
		return new BigDecimal(String.valueOf(value)).doubleValue();
	}

	public static int floatToInt(float value) {
		return new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_DOWN).intValue();
	}

	public static long vert2ToLong(int x, int z) {
		return (long) x & 4294967295L | ((long) z & 4294967295L) << 32;
	}

}
