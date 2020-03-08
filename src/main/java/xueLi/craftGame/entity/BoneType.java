package xueLi.craftGame.entity;

public enum BoneType {

	//Oh come on I should have written "lhand" instead of "lhead" xD
	head(0), body(1), lhand(2), rhand(3), lleg(4), rleg(5);
	
	public int id;
	BoneType(int id){
		this.id = id;
	}
	
	public static final int length = BoneType.values().length;

}
