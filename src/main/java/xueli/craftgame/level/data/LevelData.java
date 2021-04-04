package xueli.craftgame.level.data;

import java.io.File;

public class LevelData {

	private String path;

	private String name;
	private long lastTimePlay;

	private LevelData(String path, String name, long lastTimePlay) {
		this.path = path;
		this.name = name;
		this.lastTimePlay = lastTimePlay;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public long getLastTimePlay() {
		return lastTimePlay;
	}

	@Override
	public String toString() {
		return "LevelData [path=" + path + ", name=" + name + ", lastTimePlay=" + lastTimePlay + "]";
	}

	public static String newLevelName(String name, String path, int tryCount) {
		File file = tryCount == 0 ? new File(path + "/" + name + "/")
				: new File(path + "/" + name + "-" + tryCount + "/");
		if (file.exists())
			return newLevelName(name, path, tryCount + 1);
		return name;
	}

	public static LevelData newLevel(String name, String path) {
		return new LevelData(path + "/" + newLevelName(name, path, 0) + "/", name, System.currentTimeMillis());
	}

	public static LevelData getData(String path) {
		// TODO: LevelData

		return null;
	}

}
