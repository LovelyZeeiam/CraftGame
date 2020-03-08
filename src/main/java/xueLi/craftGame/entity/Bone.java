package xueLi.craftGame.entity;

import xueLi.craftGame.template.entity.TBoneData;

public class Bone {

	public float[] vertices;
	public int parent = -1;
	
	public TBoneData[] rawData;
	public float[][] rawOffset;
	
	public float rotX = 0,rotY = 0,rotZ = 0;
	
}
