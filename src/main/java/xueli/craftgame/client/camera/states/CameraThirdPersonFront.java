package xueli.craftgame.client.camera.states;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.client.camera.CameraState;
import xueli.craftgame.entity.Player;
import xueli.gamengine.utils.math.MatrixHelper;
import xueli.gamengine.utils.math.MousePicker;
import xueli.gamengine.utils.vector.Vector;

public class CameraThirdPersonFront implements CameraState {

	@Override
	public Vector getCameraViewMatrix(Player player) {
		// 摄像头的旋转与玩家一样 但是向后后退了几格
		float rotX = player.pos.rotX;
		float rotY = player.pos.rotY;
		float rotZ = player.pos.rotZ;
		
		// 首先刷新ViewMatrix到第一人称视角 因为下面有个方法要用到
		MatrixHelper.player(player.pos);
		MousePicker.ray(player.pos);
		
		Vector3f rayEndVector3f = null;
		for(float i = 0; i < 5.0f; i += 0.3f) {
			Vector3f rayEnd = MousePicker.getPointOnRay(i);
			Vector3i rayEndBlock = new Vector3i(rayEnd);
			if(player.getWorld().hasBlock(rayEndBlock))
				break;
			rayEndVector3f = rayEnd;
			
		}
		
		return new Vector(rayEndVector3f.x, rayEndVector3f.y, rayEndVector3f.z, -rotX, rotY + 180, rotZ);
	}

}
