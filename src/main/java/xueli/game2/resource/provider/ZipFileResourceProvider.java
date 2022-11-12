package xueli.game2.resource.provider;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.ResourceZipEntry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileResourceProvider extends AbstractResourceProvider {

	public static final String ROOT_FOLDER = "/assets/";

	private ZipFile zipFile;

	public ZipFileResourceProvider(File file) {
		try {
			this.zipFile = new ZipFile(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected String toVirtualPath(ResourceLocation location) {
		return ROOT_FOLDER + location.namespace() + "/" + location.location();
	}

	@Override
	protected Resource getResource(String virtualPath) throws IOException {
		ZipEntry entry = zipFile.getEntry(virtualPath);
		return new ResourceZipEntry(entry, zipFile);
	}

	@Override
	protected List<Resource> findResources(String virtualPath, Predicate<String> fileNamePredicate) throws IOException {
		List<Resource> resources = new ArrayList<>();

		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry e = entries.nextElement();
			String pathEntry = e.getName();

			if (e.isDirectory())
				continue;
			if (!pathEntry.startsWith(virtualPath))
				continue;

			if (pathEntry.equals(virtualPath)) {
				resources.add(new ResourceZipEntry(e, zipFile));
				break;
			}

			String folderName = virtualPath.endsWith("/") ? virtualPath : virtualPath + "/";
			String fileName = pathEntry.substring(folderName.length());
			if (fileName.contains("/"))
				continue;

			resources.add(new ResourceZipEntry(e, zipFile));

		}

		return resources;
	}

}
