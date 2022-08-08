package xueli.game.resource.texture;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import xueli.game2.resource.provider.ResourceProvider;
import xueli.utils.io.Files;
import xueli.utils.logger.MyLogger;

public class TextureAtlasBuilder {

	private static final MyLogger LOGGER = new MyLogger() {
		{
			pushState("TextureAtlasBuilder");
		}
	};

	HashMap<String, URL> textureMaps = new HashMap<>();

	public TextureAtlasBuilder() {

	}

	public TextureAtlasBuilder add(String namespace, String virtualPath, ResourceProvider provider) {
//		URL url = provider.virtualPathToUrl(virtualPath);
//		if(url == null) {
//			LOGGER.warning("Can't find virtual path: " + virtualPath);
//		} else {
//			textureMaps.put(namespace, url);
//		}

		return this;
	}

	public TextureAtlasBuilder add(String namespace, String path) {
		File file = new File(path);
		URL url = null;
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		textureMaps.put(namespace, url);
		return this;
	}

	public TextureAtlasBuilder iterate(String virtualPath, ResourceProvider provider, boolean onlyCurrentModule) {
//		String[] virtualPaths = provider.iterateFile(virtualPath, onlyCurrentModule);
//		// System.out.println(Arrays.toString(virtualPaths));
//		for (String path : virtualPaths) {
//			String name = Files.getNameExcludeSuffix(path);
//			this.add(name, path, provider);
//			// System.out.println(name);
//		}
		return this;
	}

	public TextureAtlasBuilder iterate(File folder) {
		File[] files = folder.listFiles(pathname -> pathname.getPath().endsWith(".png"));
		assert files != null;
		for (File file : files) {
			String name = Files.getNameExcludeSuffix(file.getName());
			this.add(name, file.getPath());
		}

		return this;
	}

	public static TextureAtlasBuilder iterateFiles(String virtualPath, ResourceProvider provider,
			boolean onlyCurrentModule) {
		return new TextureAtlasBuilder().iterate(virtualPath, provider, onlyCurrentModule);
	}

	public static TextureAtlasBuilder single(String namespace, String virtualPath, ResourceProvider provider) {
		return new TextureAtlasBuilder().add(namespace, virtualPath, provider);
	}

	public HashMap<String, URL> getTextureMaps() {
		return textureMaps;
	}

}
