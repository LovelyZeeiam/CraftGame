package xueli.utils.mojang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import xueli.utils.io.Files;
import xueli.utils.logger.Logger;

public class SkinGetter {

	private static final Logger LOGGER = new Logger();

	public static boolean saveSkin(String playerName, String path) {
		try {
			URL uuidUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
			InputStream uuidIn = uuidUrl.openStream();
			JsonObject uuidObj = new Gson().fromJson(new InputStreamReader(uuidIn), JsonObject.class);
			uuidIn.close();
			if (uuidObj == null) {
				LOGGER.error("Found no player named: " + playerName);
				return false;
			}
			String uuid = uuidObj.get("id").getAsString();

			URL profileUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
			InputStream profileIn = profileUrl.openStream();
			JsonObject profileObj = new Gson().fromJson(new InputStreamReader(profileIn), JsonObject.class);
			profileIn.close();
			String textureValue = profileObj.get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value")
					.getAsString();
			String decodedTextureJson = new String(Base64.getDecoder().decode(textureValue));

			JsonObject textureJson = new Gson().fromJson(decodedTextureJson, JsonObject.class);
			String textureUrlString = textureJson.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject()
					.get("url").getAsString();
			LOGGER.info("Get player skin path: " + textureUrlString);
			URL textureUrl = new URL(textureUrlString);
			InputStream textureIn = textureUrl.openStream();

			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			int data = 0;
			while ((data = textureIn.read()) != -1) {
				byteOut.write(data);
			}

			Files.fileOutput(path, byteOut.toByteArray());

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {

	}

}
