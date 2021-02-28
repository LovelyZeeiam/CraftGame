package xueli.game.modding;

import java.util.HashMap;

import xueli.utils.io.Log;

public class ModManager {

	private HashMap<String, IMod> mods = new HashMap<>();

	public void register(IMod mod) {
		mod.onInit();
		mods.put(mod.name, mod);
		Log.logger.fine("[Mod] Register mod: " + mod.name);

	}

	public void tick() {
		mods.forEach((s, m) -> m.onTick());

	}

	public void release() {
		mods.forEach((s, m) -> m.onRelease());

	}

}
