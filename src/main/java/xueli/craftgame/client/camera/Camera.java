package xueli.craftgame.client.camera;

import java.util.ArrayList;

import xueli.craftgame.client.camera.states.CameraFirstPerson;
import xueli.craftgame.client.camera.states.CameraThirdPersonBack;
import xueli.craftgame.client.camera.states.CameraThirdPersonFront;
import xueli.craftgame.entity.Player;
import xueli.gamengine.utils.vector.Vector;

public class Camera {

	private Player player;

	private ArrayList<CameraState> states = new ArrayList<CameraState>();
	private int current_state_id;
	
	public Camera(Player player) {
		this.player = player;
		registerStates();
		
	}
	
	private void registerStates() {
		states.add(new CameraFirstPerson());
		states.add(new CameraThirdPersonBack());
		states.add(new CameraThirdPersonFront());
		
	}
	
	public void toggleCamState() {
		current_state_id++;
		if(current_state_id >= states.size()) {
			current_state_id -= states.size();
		}
		
	}
	
	public Vector getViewVectotVector() {
		return states.get(current_state_id).getCameraViewMatrix(player);
	}

}
