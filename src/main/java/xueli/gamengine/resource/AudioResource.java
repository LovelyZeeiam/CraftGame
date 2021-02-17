package xueli.gamengine.resource;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;

import xueli.gamengine.utils.resource.SoundManager;
import xueli.utils.Logger;

public class AudioResource extends IResource {

	public AudioResource(String pathString) {
		super(pathString);

	}

	public void load() throws IOException {
		JsonObject json = gson.fromJson(new FileReader(this.pathString + "/sounds/sounds.json"), JsonObject.class);
		json.entrySet().forEach(e -> {
			String namespace = e.getKey();
			String path = e.getValue().getAsString();

			if (path.endsWith(".wav")) {
				try {
					SoundManager.loadWav(namespace, this.pathString + path);
					Logger.info("Sound: Load audio: " + path);

				} catch (IOException ioException) {
					Logger.error("Sound: Load audio " + path + " failed: " + ioException.getMessage());

				}

			}

		});

	}

	public void close() throws IOException {
		SoundManager.release();

	}

}
