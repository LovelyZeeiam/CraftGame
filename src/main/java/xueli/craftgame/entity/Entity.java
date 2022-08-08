package xueli.craftgame.entity;

import java.util.Objects;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game.vector.Vector;
import xueli.game2.core.math.TriFuncMap;

public class Entity {
	
	private EntityBase base;
	
	private VirtualKeyboard keyboard = new VirtualKeyboard();
	
	private Vector3f acceleration;
	private Vector3f speed = new Vector3f(0, 0, 0);
	private final Vector position;
	private boolean onGround = false;
	
	public Entity(EntityBase base) {
		this.base = Objects.requireNonNull(base);
		this.position = new Vector(0, 0, 0);
		
	}
	
	public void tick() {
		this.acceleration = getAccelerationScale(this.position);
		Vector3f.add(acceleration, speed, speed);
		
		this.speed.x *= 0.9f;
		this.speed.y *= 0.8f;
		this.speed.z *= 0.9f;
		
		this.position.x += this.speed.x;
		this.position.y += this.speed.y;
		this.position.z += this.speed.z;
		
	}
	
	private Vector3f getAccelerationScale(Vector position) {
		// In Player Axis
		float forward = 0, rightSide = 0, up = 0;
		if(keyboard.virtualForward) {
			forward++;
			if(keyboard.virtualDash) {
				forward *= base.getAttributeDashScale().getValue();
			}
		}
		if(keyboard.virtualBack)
			forward--;
		if(keyboard.virtualLeft)
			rightSide--;
		if(keyboard.virtualRight)
			rightSide++;
		if(onGround) {
			if(keyboard.virtualJump)
				up++;
			if(keyboard.virtualDown)
				up--;
		}
			
		Vector2f planeVector2f = new Vector2f(rightSide, forward);
		planeVector2f.normalise();
		
		// To World Axis
		Vector2f worldPlaneVector2f = new Vector2f(0, 0);
		worldPlaneVector2f.x -= planeVector2f.y * TriFuncMap.sin(-position.rotY);
		worldPlaneVector2f.y -= planeVector2f.x * TriFuncMap.cos(-position.rotY);
		
		Vector3f speedScaleVector3f = new Vector3f(worldPlaneVector2f.x, up, worldPlaneVector2f.y);
		float speedValue = base.getAttributeSpeed().getValue(), speedUpDownValue = base.getAttributeSpeedUpDown().getValue();
		return new Vector3f(speedScaleVector3f.x * speedValue, speedScaleVector3f.y * speedUpDownValue, speedScaleVector3f.z * speedValue);
	}
	
	public Vector getPosition() {
		return position;
	}
	
}
