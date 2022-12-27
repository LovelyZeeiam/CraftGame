package xueli.mcremake.classic.client;

public class WorldEvents {

	public record NewChunkEvent(int x, int z) {
	}

	public record ModifyBlockEvent(int x, int y, int z) {
	}

	public record UnloadChunkEvent(int x, int z) {
	}

}
