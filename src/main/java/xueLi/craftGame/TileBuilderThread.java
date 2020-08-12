package xueLi.craftGame;

import xueLi.craftGame.world.Chunk;

public class TileBuilderThread extends MeshBuilderThread {

	public TileBuilderThread() {
		super();

	}

	@Override
	public void run() {
		while (true) {
			if (!running) {
				break;
			}

			for (Chunk chunk : updateChunks) {

			}

		}

	}

}
