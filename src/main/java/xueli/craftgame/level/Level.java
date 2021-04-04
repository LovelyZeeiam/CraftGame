package xueli.craftgame.level;

import xueli.craftgame.level.data.LevelData;

public class Level {

	private LevelData data;

	public Level(String pathString) {
		this.data = LevelData.getData(pathString);

	}

	public Level(String name, String path) {
		this.data = LevelData.newLevel(name, path);

	}

	public void runInit() {

	}

	public void runSave() {

	}

}
