package xueli.game2.resource.provider;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xueli.game2.resource.ResourceIdentifier;

public class ClassLoaderResourceProvider extends URLResourceProvider {

	public static final String ROOT_FOLDER = "/assets/";

	private static URL currentBinPath = ClassLoaderResourceProvider.class.getProtectionDomain().getCodeSource()
			.getLocation();
	private static JarFile currentBinJarFile = null;
	static {
		if (currentBinPath.getProtocol().equalsIgnoreCase("jar")) {
			JarURLConnection connection;
			try {
				connection = (JarURLConnection) currentBinPath.openConnection();
				currentBinJarFile = connection.getJarFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean onlyCurrentModule = true;

	public ClassLoaderResourceProvider() {
	}

	public ClassLoaderResourceProvider(boolean currentModule) {
		this.onlyCurrentModule = currentModule;
	}

	@Override
	protected String toVirtualPath(ResourceIdentifier location) {
		return ROOT_FOLDER + location.namespace() + "/" + location.location();
	}

	@Override
	protected URL getResourceURL(String virtualPath) throws IOException {
//		System.out.println(virtualPath);
		URL url = getClass().getResource(virtualPath);
		if (url == null)
			throw new IOException("Can't find resource: " + virtualPath);
		return url;
	}

	@Override
	protected List<URL> findResources(String virtualPath) throws IOException {
		// When it comes to ClassLoader, a "/" is unnecessary; but it's not in
		// Class.getResource
		while (virtualPath.startsWith("/")) {
			virtualPath = virtualPath.substring(1);
		}
		// Final to get access in lambda
		String finalVirtualPath = virtualPath;

		Enumeration<URL> resources = ClassLoaderResourceProvider.class.getClassLoader().getResources(virtualPath);
		ArrayList<URL> urls = new ArrayList<>();

		resources.asIterator().forEachRemaining(u -> {
			String protocol = u.getProtocol();
			// Theoretically(According to general theory 按普遍理性而言), the protocol equals to
			// only "file" and "jar"
			if (protocol.equalsIgnoreCase("file")) {
				File file = new File(u.getPath());
				if (file.isFile()) {
					urls.add(u);
				} else if (file.isDirectory()) {
					File[] files = file.listFiles();
					List<URL> filesToUrls = Stream.of(files).map(f -> {
						try {
							return f.toURI().toURL();
						} catch (MalformedURLException e) {
							e.printStackTrace();
							return null;
						}
					}).collect(Collectors.toList());
					urls.addAll(filesToUrls);
				}
			} else if (protocol.equalsIgnoreCase("jar")) {
				try {
					JarURLConnection jarConnection = (JarURLConnection) u.openConnection();
					JarFile file = jarConnection.getJarFile();

					if (this.onlyCurrentModule && !file.equals(currentBinJarFile)) {
						return;
					}

					Enumeration<JarEntry> entries = file.entries();
					entries.asIterator().forEachRemaining(e -> {
						String filePathInJar = e.getName();

						// Here a lot of results will be filtered, leaving files only in the folder
						if (e.isDirectory())
							return;
						if (!filePathInJar.startsWith(finalVirtualPath))
							return;

						// A '/' is a must to remove the prefix
						String folderName = finalVirtualPath.endsWith("/") ? finalVirtualPath : finalVirtualPath + "/";
						String fileName = filePathInJar.substring(folderName.length());
						if (fileName.contains("/"))
							return;

						urls.add(u);
					});

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		return urls;
	}

}
