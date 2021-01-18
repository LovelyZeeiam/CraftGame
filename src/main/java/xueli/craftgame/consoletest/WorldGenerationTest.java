package xueli.craftgame.consoletest;

import xueli.craftgame.block.BlockResource;
import xueli.craftgame.world.biome.BiomeResource;
import xueli.gamengine.resource.LangManager;
import xueli.gamengine.utils.Logger;

public class WorldGenerationTest implements Runnable {

	private LangManager lang;

	@Override
	public void run() {
		Logger.info("World generation test.");

		lang = new LangManager("res/");
		lang.loadLang();
		lang.setLang("zh-ch.lang");

		BlockResource blockRes = new BlockResource("res/", lang);
		blockRes.load();
		
		BiomeResource biomeRes = new BiomeResource("res/", lang);
		biomeRes.load();
		
		
		

	}

}
