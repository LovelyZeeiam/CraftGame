package xueli.craftgame.client.camera.states;

import xueli.craftgame.client.camera.CameraState;
import xueli.craftgame.entity.Player;
import xueli.gamengine.utils.vector.Vector;

public class CameraFirstPerson implements CameraState {

	@Override
	public Vector getCameraViewMatrix(Player player) {
		return player.pos;
	}

}
