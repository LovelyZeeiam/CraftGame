package xueli.game2.resource.provider;

import xueli.game2.resource.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderResourceProvider extends URLResourceProvider {

	private File folderFile;

	public FolderResourceProvider(File folder) {
		this.folderFile = folder;
	}

	@Override
	protected String toVirtualPath(ResourceLocation location) {
		return folderFile.getPath() + "/" + location.namespace() + "/" + location.location();
	}

	@Override
	protected URL getResourceURL(String virtualPath) throws IOException {
		File file = new File(virtualPath);
		return file.toURI().toURL();
	}

	@Override
	protected List<URL> findResources(String virtualPath) throws IOException {
		File file = new File(virtualPath);
		ArrayList<URL> urls = new ArrayList<>();

		if (file.isFile()) {
			urls.add(file.toURI().toURL());
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			if(files == null)
				return urls;
			List<URL> filesToUrls = Stream.of(files).map(f -> {
				try {
					return f.toURI().toURL();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
			urls.addAll(filesToUrls);
		}

		return urls;
	}

}
