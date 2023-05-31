package xueli.mcremake.client.state;

import org.lwjgl.utils.vector.Vector3d;

import xueli.game2.Vector;
import xueli.mcremake.core.entity.PickResult;

public class ClientPlayerState {

	public Vector3d lastTickPosition = new Vector3d();

	public Vector3d position = new Vector3d(0, 100, 0);
	public double rotX = 0.0, rotY = 0.0;
	public Vector3d velocity = new Vector3d(0, 0, 0);

	public PickResult pickResult;

	public Vector positionOnRender = new Vector();

}
