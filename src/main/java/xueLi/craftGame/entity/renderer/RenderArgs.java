package xueLi.craftGame.entity.renderer;

public class RenderArgs {

	public int offset;
	public int vertCount;

	public float rotX, rotY, rotZ;
	
	public float[] rotOffset = new float[3];
	public float[] parentRot = new float[3];

	public RenderArgs(int offset, int vertCount, float rotX, float rotY, float rotZ) {
		this.offset = offset;
		this.vertCount = vertCount;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

}
