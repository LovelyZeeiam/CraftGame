package xueli.craftgame.event;

public class EventLocalPlayerPosSync {

	private double x, y, z;

	public EventLocalPlayerPosSync(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

}
