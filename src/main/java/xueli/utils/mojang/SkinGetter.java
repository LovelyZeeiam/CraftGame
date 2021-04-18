package xueli.utils.mojang;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import xueli.utils.io.Files;

public class SkinGetter {
	
	public static void saveSkin(String playerName, String path) {
		try {
			URL uuidUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
			InputStream uuidIn = uuidUrl.openStream();
			JsonObject uuidObj = new Gson().fromJson(new InputStreamReader(uuidIn), JsonObject.class);
			uuidIn.close();
			if(uuidObj == null) {
				Logger.getLogger(SkinGetter.class.getName()).severe("Found no player named: " + playerName);
				return;
			}
			String uuid = uuidObj.get("id").getAsString();
			
			URL profileUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
			InputStream profileIn = profileUrl.openStream();
			JsonObject profileObj = new Gson().fromJson(new InputStreamReader(profileIn), JsonObject.class);
			profileIn.close();
			String textureValue = profileObj.get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
			String decodedTextureJson = new String(Base64.getDecoder().decode(textureValue));
			
			JsonObject textureJson = new Gson().fromJson(decodedTextureJson, JsonObject.class);
			String textureUrlString = textureJson.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
			URL textureUrl = new URL(textureUrlString);
			InputStream textureIn = textureUrl.openStream();
			byte[] data = new byte[textureIn.available()];
			textureIn.read(data);
			
			Files.fileOutput(path, data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
