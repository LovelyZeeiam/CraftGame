package xueli.craftgame.level.data;

import static org.lwjgl.nanovg.NanoVG.*;

import xueli.utils.io.Log;

public class LevelDataItem {

	private LevelData data;
	private int levelIcon_nvg;
	
	public LevelDataItem(LevelData data, long nvg) {
		this.data = data;
		this.levelIcon_nvg = nvgCreateImage(nvg, data.getPath() + "/" + LevelFile.LEVEL_DATA, NVG_IMAGE_NEAREST);
		
		if(this.levelIcon_nvg == 0) {
			Log.logger.warning("Can't find level icon: " + data.getPath());
			
		}
		
	}
	
	public LevelData getData() {
		return data;
	}
	
	public int getLevelIcon() {
		return levelIcon_nvg;
	}
	

}
