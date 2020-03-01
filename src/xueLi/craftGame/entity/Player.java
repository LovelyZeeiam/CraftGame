package xueLi.craftGame.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.HitBox;
import xueLi.craftGame.world.World;

public class Player extends Entity {

	public int gamemode = 1;
	
	public BlockPos blockPointed;
	
	public float resistant = 0.009f;
	public float sensivity = 0.1f;

	public Player(float x, float y, float z) {
		super(x, y, z);
	}

	public Player(float x, float y, float z, float rotX, float rotY, float rotZ) {
		super(x, y, z, rotX, rotY, rotZ);
	}

	@Override
	public void tick(World world) {
		if (speed.x > 0) {
			speed.x -= resistant * speed.x  * DisplayManager.deltaTime;
			if (speed.x < 0)
				speed.x = 0;
		} else if (speed.x < 0) {
			speed.x -= resistant * speed.x  * DisplayManager.deltaTime;
			if (speed.x > 0)
				speed.x = 0;
		}

		if (speed.y > 0) {
			speed.y -= resistant * speed.y * 1.2f * DisplayManager.deltaTime;
			if (speed.y < 0)
				speed.y = 0;
		} else if (speed.y < 0) {
			speed.y -= resistant * speed.y * 1.2f * DisplayManager.deltaTime;
			if (speed.y > 0)
				speed.y = 0;
		}

		if (speed.z > 0) {
			speed.z -= resistant * speed.z  * DisplayManager.deltaTime;
			if (speed.z < 0)
				speed.z = 0;
		} else if (speed.z < 0) {
			speed.z -= resistant * speed.z  * DisplayManager.deltaTime;
			if (speed.z > 0)
				speed.z = 0;
		}
		
		if (DisplayManager.isKeyDown(Keyboard.KEY_W)) {
			if(DisplayManager.isKeyDown(Keyboard.KEY_R)) {
				speed.x -= this.getSpeed() * 1.8f * (float) Math.sin(Math.toRadians(-pos.rotY));
				speed.z -= this.getSpeed() * 1.8f * (float) Math.cos(Math.toRadians(-pos.rotY));
			}
			else {
				speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
				speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
			}
		}
		if (DisplayManager.isKeyDown(Keyboard.KEY_S)) {
			speed.x += this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY));
			speed.z += this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY));
		}
		if (DisplayManager.isKeyDown(Keyboard.KEY_A)) {
			speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY + 90));
			speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY + 90));
		}
		if (DisplayManager.isKeyDown(Keyboard.KEY_D)) {
			speed.x -= this.getSpeed() * (float) Math.sin(Math.toRadians(-pos.rotY - 90));
			speed.z -= this.getSpeed() * (float) Math.cos(Math.toRadians(-pos.rotY - 90));
		}

		if (DisplayManager.isKeyDown(Keyboard.KEY_SPACE)) {
			speed.y += this.getSpeed() * 0.9f;
		}

		if (DisplayManager.isKeyDown(Keyboard.KEY_LSHIFT)) {
			speed.y -= this.getSpeed() * 0.9f;
		}
		
		pos.rotX -= Mouse.getDY() * sensivity;
		pos.rotY += Mouse.getDX() * sensivity;
		
		
		super.updatePos(world);
	}

	@Override
	public float getSpeed() {
		return 0.001f;
	}

	private final HitBox hitbox = new HitBox(-0.2f, -1.8f, -0.2f, 0.2f, 0.2f, 0.2f);

	@Override
	public HitBox getOriginHitBox() {
		return hitbox;
	}

}
