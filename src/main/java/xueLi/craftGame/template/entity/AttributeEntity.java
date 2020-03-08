package xueLi.craftGame.template.entity;

import xueLi.craftGame.entity.Bone;
import xueLi.craftGame.entity.BoneType;
import xueLi.craftGame.utils.HitBox;

public class AttributeEntity {
	
	public String name;
	public Bone[] model;
	public HitBox box;
	
	public AttributeEntity() {
		model = new Bone[BoneType.values().length];
		for(int x = 0;x < model.length;x++)
			model[x] = new Bone();
	}

}
