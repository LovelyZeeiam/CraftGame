package xueli.craftgame.client.camera;

import xueli.craftgame.entity.Player;
import xueli.gamengine.utils.vector.Vector;

public interface CameraState {

	public Vector getCameraViewMatrix(Player player);

}
