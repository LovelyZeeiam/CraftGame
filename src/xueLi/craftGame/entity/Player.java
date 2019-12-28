package xueLi.craftGame.entity;

public class Player extends Entity {

	public int gamemode = 1;

	public Player(float x, float y, float z) {
		super(x, y, z);
	}

	public Player(float x, float y, float z, float rotX, float rotY, float rotZ) {
		super(x, y, z, rotX, rotY, rotZ);
	}

	@Override
	public void tick() {

	}

	@Override
	public float getSpeed() {
		return 0.01f;
	}

}
