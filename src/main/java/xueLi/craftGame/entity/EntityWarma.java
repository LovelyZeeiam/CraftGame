package xueLi.craftGame.entity;

import java.util.List;

import xueLi.craftGame.database.Entities;
import xueLi.craftGame.entity.renderer.RenderArgs;
import xueLi.craftGame.world.World;

public class EntityWarma extends Entity {

	public EntityWarma(float x, float y, float z) {
		super(x, y, z);
		this.attrib = Entities.mWarma;
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

	@Override
	public List<RenderArgs> render() {
		return super.defaultRender();
	}

}
