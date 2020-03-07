package xueLi.craftGame.entity.renderer;

public class RenderArgs {

	public int offset;
	public int vertCount;

	public float rotX, rotY, rotZ;

	public RenderArgs(int offset, int vertCount, float rotX, float rotY, float rotZ) {
		this.offset = offset;
		this.vertCount = vertCount;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

}
