package xueLi.craftGame.entity;

public enum EntityType {

	PASSIVE(0),ENEMY(1),EASTEREGG(2333);
	
	public int id;
	EntityType(int id){
		this.id = id;
	}
	
}
