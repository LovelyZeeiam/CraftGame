package xueli.craftgame.event;

public class EventChunkMeshShouldRebuilt {

	private int modifiedChunkX, modifiedChunkZ;

	public EventChunkMeshShouldRebuilt(int modifiedChunkX, int modifiedChunkZ) {
		this.modifiedChunkX = modifiedChunkX;
		this.modifiedChunkZ = modifiedChunkZ;
	}

	public int getModifiedChunkX() {
		return modifiedChunkX;
	}

	public int getModifiedChunkZ() {
		return modifiedChunkZ;
	};

}
