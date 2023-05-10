package xueli.mcremake.core.entity;

import java.util.ArrayList;

import org.lwjgl.utils.vector.Vector2d;
import org.lwjgl.utils.vector.Vector3d;
import org.lwjgl.utils.vector.Vector3i;

import xueli.game2.Vector;
import xueli.game2.math.TriFuncMap;
import xueli.game2.phys.aabb.BoxFace;
import xueli.game2.phys.aabb.NameableAABB;
import xueli.mcremake.client.ListenableBufferedWorldAccessible;
import xueli.mcremake.core.block.BlockCollidable;
import xueli.mcremake.core.block.BlockType;

public class PickCollider {

	public static PickResult pick(Vector camera, double maxReachDistance, ListenableBufferedWorldAccessible world) {
		Vector3d direction = new Vector3d(TriFuncMap.sin(camera.rotY), TriFuncMap.tan(camera.rotX), -TriFuncMap.cos(camera.rotY));
		direction.normalize();

		// If the line goes down, it is impossible to interact with the bottom. Conversely, the line going up can't interact with the top. The thesis is the same with the other 2 axis.
		boolean[] needFaceTest = { true, true, true, true, true, true };
		if(direction.y < 0) {
			needFaceTest[2] = false;
		} else if(direction.y > 0) {
			needFaceTest[3] = false;
		} else {
			needFaceTest[2] = needFaceTest[3] = false;
		}

		if(direction.x < 0) {
			needFaceTest[0] = false;
		} else if(direction.x > 0) {
			needFaceTest[1] = false;
		} else {
			needFaceTest[0] = needFaceTest[1] = false;
		}

		if(direction.z < 0) {
			needFaceTest[4] = false;
		} else if(direction.z > 0) {
			needFaceTest[5] = false;
		} else {
			needFaceTest[4] = needFaceTest[5] = false;
		}

		for (double i = 0; i < maxReachDistance; i += 0.1) {
			double rayEndX = camera.x + i * direction.x;
			double rayEndY = camera.y + i * direction.y;
			double rayEndZ = camera.z + i * direction.z;

			Vector3i blockPos = new Vector3i((int) Math.floor(rayEndX), (int) Math.floor(rayEndY), (int) Math.floor(rayEndZ));
			BlockType block = world.getBlockImmediate(blockPos.x, blockPos.y, blockPos.z);
			BlockCollidable collidable;
			if(block != null && ((collidable = block.collidable()) != null)) {
				ArrayList<NameableAABB> blockBoundingBoxes = new ArrayList<>();
				collidable.getPickTestAABBs(blockPos.x, blockPos.y, blockPos.z, world, blockBoundingBoxes);

				double finalT = maxReachDistance;
				PickResult result = null;

				for (int j = 0; j < blockBoundingBoxes.size(); j++) {
					NameableAABB aabb = blockBoundingBoxes.get(j);
					Vector3d b0 = aabb.getVmin();
					Vector3d b1 = aabb.getVmax();

					// Here we have a math question:
					// Now that there is a line, which expression is:
					//    [  x = x0 + t * dx
					//    {  y = y0 + t * dy
					//    [  z = z0 + t * dz
					// And here is a box, which has 12 faces,
					// We have to interact the line with planes extended from the faces

					// For face x = xb0,
					// t = (xb0 - x0) / dx
					// If t is greater than maxReachDistance, we ignore it
					// Or we calculate y and z to check if they are in a range
					// At last we choose the least "t" as our collision result

					if(needFaceTest[0]) {
						double t = (b0.x - camera.x) / direction.x;
						if(t >= 0 && t < finalT) {
							double thisY = camera.y + t * direction.y;
							double thisZ = camera.z + t * direction.z;
							if(thisY > b0.y && thisY < b1.y && thisZ > b0.z && thisZ < b1.z) {
								finalT = t;
								result = new PickResult(blockPos, aabb, BoxFace.X_MINUS, new Vector2d((thisY - b0.y) / (b1.y - b0.y), (thisZ - b0.z) / (b1.z - b0.z)));
							}
						}
					}

					if(needFaceTest[1]) {
						double t = (b1.x - camera.x) / direction.x;
						if(t >= 0 && t < finalT) {
							double thisY = camera.y + t * direction.y;
							double thisZ = camera.z + t * direction.z;
							if(thisY > b0.y && thisY < b1.y && thisZ > b0.z && thisZ < b1.z) {
								finalT = t;
								result = new PickResult(blockPos, aabb, BoxFace.X_PLUS, new Vector2d((thisY - b0.y) / (b1.y - b0.y), (thisZ - b0.z) / (b1.z - b0.z)));
							}
						}
					}

					if(needFaceTest[2]) {
						double t = (b0.y - camera.y) / direction.y;
						if(t >= 0 && t <= finalT) {
							double thisX = camera.x + t * direction.x;
							double thisZ = camera.z + t * direction.z;
							if(thisX > b0.x && thisX < b1.x && thisZ > b0.z && thisZ < b1.z) {
								finalT = t;
								result = new PickResult(blockPos, aabb, BoxFace.Y_MINUS, new Vector2d((thisX - b0.x) / (b1.x - b0.x), (thisZ - b0.z) / (b1.z - b0.z)));
							}
						}
					}

					if(needFaceTest[3]) {
						double t = (b1.y - camera.y) / direction.y;
						if(t >= 0 && t <= finalT) {
							double thisX = camera.x + t * direction.x;
							double thisZ = camera.z + t * direction.z;
							if(thisX > b0.x && thisX < b1.x && thisZ > b0.z && thisZ < b1.z) {
								finalT = t;
								result = new PickResult(blockPos, aabb, BoxFace.Y_PLUS, new Vector2d((thisX - b0.x) / (b1.x - b0.x), (thisZ - b0.z) / (b1.z - b0.z)));
							}
						}
					}

					if(needFaceTest[4]) {
						double t = (b0.z - camera.z) / direction.z;
						if(t >= 0 && t <= finalT) {
							double thisX = camera.x + t * direction.x;
							double thisY = camera.y + t * direction.y;
							if(thisX > b0.x && thisX < b1.x && thisY > b0.y && thisY < b1.y) {
								finalT = t;
								result = new PickResult(blockPos, aabb, BoxFace.Z_MINUS, new Vector2d((thisX - b0.x) / (b1.x - b0.x), (thisY - b0.y) / (b1.y - b0.y)));
							}
						}
					}

					if(needFaceTest[5]) {
						double t = (b1.z - camera.z) / direction.z;
						if(t >= 0 && t <= finalT) {
							double thisX = camera.x + t * direction.x;
							double thisY = camera.y + t * direction.y;
							if(thisX > b0.x && thisX < b1.x && thisY > b0.y && thisY < b1.y) {
								finalT = t;
								result = new PickResult(blockPos, aabb, BoxFace.Z_PLUS, new Vector2d((thisX - b0.x) / (b1.x - b0.x), (thisY - b0.y) / (b1.y - b0.y)));
							}
						}
					}

				}

				if(result != null)
					return result;

			}

		}

		return null;
	}

}
