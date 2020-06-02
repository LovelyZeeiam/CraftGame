package xueLi.craftGame.initer;

import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.utils.AudioManager;

public class Audios {

	public static void init() {
		AudioManager.init();

		// This sound is used for menu click, recorded my classmates shouting and edited by myself xD
		AudioManager.loadOggSound(0, "res/audios/menu/click.ogg", new Vector3f(0, 0, 0), new Vector3f(0, 1, 0), 1.0f,
				1.0f);
		

	}

	public static void close() {
		AudioManager.close();

	}

}
