package xueli.mcremake.core.entity;

import org.lwjgl.utils.vector.Vector2d;
import org.lwjgl.utils.vector.Vector3i;
import xueli.game2.phys.aabb.BoxFace;
import xueli.game2.phys.aabb.NameableAABB;

// Make it simple first
public record PickResult(Vector3i blockPos, NameableAABB collisionBox, BoxFace face, Vector2d relativeCollidePos) {
	public Vector3i placePos() {
		return Vector3i.add(blockPos, face.getFaceToVector());
	}
}
