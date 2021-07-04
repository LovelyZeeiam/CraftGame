package xueli.craftgame.world;

import xueli.game.utils.ThreadTask;

public class WorldUpdater extends ThreadTask {

	private Dimension dimension;

	public WorldUpdater(Dimension dimension) {
		super("WorldUpdater");
		this.dimension = dimension;

	}

	public static class LightUpdater implements Runnable {

		private Chunk chunk;

		public LightUpdater(Chunk chunk) {
			this.chunk = chunk;
		}

		@Override
		public void run() {

		}

	}

}
