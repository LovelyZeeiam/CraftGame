package xueLi.craftGame.entity;

import xueLi.craftGame.utils.HitBox;
import xueLi.craftGame.world.World;

public class EntityWarma extends Entity {

	public EntityWarma(float x, float y, float z) {
		super(x, y, z);
		this.bones = Bones.boneWarma;
	}

	@Override
	public void tick(World world) {

		super.updatePos(world);
	}

	@Override
	public float getSpeed() {
		return 0.001f;
	}

	@Override
	public HitBox getOriginHitBox() {
		return null;
	}

}
