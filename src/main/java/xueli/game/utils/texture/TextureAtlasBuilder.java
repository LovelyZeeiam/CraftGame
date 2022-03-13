package xueli.game.utils.texture;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

import xueli.utils.io.Files;

public class TextureAtlasBuilder {
	
	HashMap<String, String> textureMaps = new HashMap<>();
	
	public TextureAtlasBuilder() {
		
	}
	
	public TextureAtlasBuilder add(String namespace, String path) {
		textureMaps.put(namespace, path);
		return this;
	}
	
	public static TextureAtlasBuilder iterate(File folder) {
		TextureAtlasBuilder builder = new TextureAtlasBuilder();
		
		File[] files = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getPath().endsWith(".png");
			}
		});
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String name = Files.getNameExcludeSuffix(file.getName());
			builder.add(name, file.getPath());
		}
		
		return builder;
	}
	
}
