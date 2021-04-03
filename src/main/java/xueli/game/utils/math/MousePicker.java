package xueli.game.utils.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import xueli.game.utils.vector.Vector;


public class MousePicker {

	private final Matrix4f projMatrix, viewMatrix;
	private Vector3f ray, camPos;

	public MousePicker(Vector camPos, Matrix4f projMatrix, Matrix4f viewMatrix) {
		this.camPos = new Vector3f(camPos.x, camPos.y, camPos.z);

		this.projMatrix = projMatrix;
		this.viewMatrix = viewMatrix;

		this.ray();

	}

	private void ray() {
		Vector4f clipCoords = new Vector4f(0, 0, -1f, 1f);

		Matrix4f invertedProj = Matrix4f.invert(projMatrix, null);
		Vector4f eyeCoords_origin = Matrix4f.transform(invertedProj, clipCoords, null);
		Vector4f eyeCoords = new Vector4f(eyeCoords_origin.x, eyeCoords_origin.y, -1f, 0f);
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		ray = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		ray.normalise();

	}

	public Vector3f getPointOnRay(float distance) {
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		Vector3f rayEnd = Vector3f.add(camPos, scaledRay, null);
		return new Vector3f(rayEnd.x, rayEnd.y, rayEnd.z);
	}

}
