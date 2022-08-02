package xueli.craftgame.entity;

public class EntityBase {
	
	private final Attribute<Float> attrSpeed, attrSpeedUpDown;
	private final Attribute<Float> attrDashScale;
	
	public EntityBase() {
		attrSpeed = new Attribute<Float>(0.2f);
		attrSpeedUpDown = new Attribute<Float>(0.16f);
		attrDashScale = new Attribute<Float>(1.2f);
		
	}

	public Attribute<Float> getAttributeSpeed() {
		return attrSpeed;
	}

	public Attribute<Float> getAttributeSpeedUpDown() {
		return attrSpeedUpDown;
	}

	public Attribute<Float> getAttributeDashScale() {
		return attrDashScale;
	}
	
}
