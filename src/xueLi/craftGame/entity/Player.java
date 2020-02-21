package xueLi.craftGame.entity;

import xueLi.craftGame.utils.HitBox;

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
		return 0.006f;
	}

	private HitBox hitbox = new HitBox(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);

	@Override
	public HitBox getOriginHitBox() {
		return super.getHitBox(this.pos, hitbox);
	}

}
