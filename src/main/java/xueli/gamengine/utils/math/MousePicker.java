package xueli.gamengine.utils.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import xueli.gamengine.utils.vector.Vector;

public class MousePicker {

	private static Vector3f ray, camPos;

	public static void ray(Vector pos) {
		camPos = new Vector3f(pos.x, pos.y, pos.z);

		// float MouseX = Mouse.getX();
		// float MouseY = Mouse.getY();
		// Vector2f normalizedMouseCoords = new Vector2f((2f * (MouseX)) /
		// Display.getWidth() - 1f,(2f * (MouseY)) / Display.getHeight() - 1f);
		Vector4f clipCoords = new Vector4f(0, 0, -1f, 1f);

		Matrix4f invertedProj = Matrix4f.invert(MatrixHelper.lastTimeProjMatrix, null);
		Vector4f eyeCoords_origin = Matrix4f.transform(invertedProj, clipCoords, null);
		Vector4f eyeCoords = new Vector4f(eyeCoords_origin.x, eyeCoords_origin.y, -1f, 0f);
		Matrix4f invertedView = Matrix4f.invert(MatrixHelper.lastTimeViewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		ray = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		ray.normalise();
	}

	public static Vector3f getPointOnRay(float distance) {
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		Vector3f rayEnd = Vector3f.add(camPos, scaledRay, null);
		return new Vector3f(rayEnd.x, rayEnd.y, rayEnd.z);
	}

}
