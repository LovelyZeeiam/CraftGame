package xueli.craftgame.world;

import xueli.game.utils.ThreadTask;

public class WorldUpdater extends ThreadTask {

	private Dimension dimension;

	public WorldUpdater(Dimension dimension) {
		super("WorldUpdater");
		this.dimension = dimension;

	}
	
	private static class LightUpdater {
		
		public LightUpdater() {
			
		}
		
	}

}
