package xueLi.craftGame.entity;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.entity.renderer.EntityRenderer;
import xueLi.craftGame.entity.renderer.RenderArgs;
import xueLi.craftGame.template.entity.AttributeEntity;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.HitBox;
import xueLi.craftGame.utils.Vector;
import xueLi.craftGame.world.World;

public abstract class Entity {

	public Vector pos;
	public boolean isInLiquid = false;

	// For physical engine
	// public boolean[] collide = new boolean[6];

	// For entity bones
	public AttributeEntity attrib;

	public Entity(float x, float y, float z) {
		pos = new Vector(x, y, z);

	}

	public Entity(float x, float y, float z, float rotX, float rotY, float rotZ) {
		pos = new Vector(x, y, z, rotX, rotY, rotZ);

	}

	public Vector3f force = new Vector3f();
	public Vector3f speed = new Vector3f();

	// this is for real physical engine and I haven't done yet :)
	// I think maybe I can figure out how to correct the position of an entity by
	// the direction of the speed.
	// But now one of the most important things is adding entities,so :} I think it
	// will be done much later.
	public void updatePos(World w) {
		Vector3f deltaPos = new Vector3f(speed.x * DisplayManager.deltaTime, speed.y * DisplayManager.deltaTime,
				speed.z * DisplayManager.deltaTime);

		/**
		 * The player will be stuck when collided with this code
		 */
		// HitBox box = getHitBox();
		// if(w.getHitBoxes(box.move(deltaPos.x + deltaPos.x > 0 ? 0.005f : -0.005f,
		// deltaPos.y + deltaPos.y > 0 ? 0.005f : -0.005f, deltaPos.z + deltaPos.z > 0 ?
		// 0.005f : -0.005f), w.wlimit_long).size() == 0) {
		pos.x += deltaPos.x;
		pos.y += deltaPos.y;
		pos.z += deltaPos.z;
		// pos.y = new
		// BigDecimal(pos.y).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		// }

		/**
		 * So I can only write a false engine code like this
		 */
		if (pos.y + this.getOriginHitBox().y1 < 5)
			pos.y = 5 - this.getOriginHitBox().y1;

		// Physical? No no no it will be muuuuuuuuuch later :}
		force.set(0, 0, 0);
		// At least moving had become smoother :)

	}
	
	protected List<RenderArgs> defaultRender(FloatBuffer buffer) {
		List<RenderArgs> args = new ArrayList<RenderArgs>();
		for(int a = 0;a < BoneType.length;a++) {
			Bone b = this.attrib.model[a];
			if(b == null)
				continue;

			float temp = 0;
			for(int m = 0;m < b.vertices.length;m++) {
				if(m % 3 == 1)
					temp = b.vertices[m] + pos.x;
				else if(m % 3 == 2)
					temp = b.vertices[m] + pos.y;
				else if(m % 3 == 0)
					temp = b.vertices[m] + pos.z;
				buffer.put(temp);
			}
			
			int vertCount = b.vertices.length / 3;
			RenderArgs arg = new RenderArgs(EntityRenderer.offset,vertCount,b.rotX,b.rotY,b.rotZ);
			if(b.parent != -1) {
				Bone p = this.attrib.model[b.parent];
				arg.parentRot[0] = p.rotX;
				arg.parentRot[1] = p.rotY;
				arg.parentRot[2] = p.rotZ;
				//在渲染的时候会先将模型挪到这个地方来
				arg.rotOffset[0] = b.rawOffset[0];
				arg.rotOffset[1] = b.rawOffset[1];
				arg.rotOffset[2] = b.rawOffset[2];
				//然后手和脚的最上方一点会挪到原点，进行旋转
			}
			args.add(arg);
			
			//Maybe this is wrong xD
			EntityRenderer.offset += vertCount;
		}
		return args;
	}
	
	public abstract List<RenderArgs> render(FloatBuffer buffer);

	public abstract void tick(World world);

	public abstract float getSpeed();

	public abstract HitBox getOriginHitBox();

	protected HitBox getHitBox() {
		return getOriginHitBox().move(pos.x, pos.y, pos.z);
	}

}
