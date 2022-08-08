package xueli.game2.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceZipEntry implements Resource {

	private ZipEntry entry;
	private ZipFile file;

	public ResourceZipEntry(ZipEntry entry, ZipFile file) {
		this.entry = entry;
		this.file = file;

	}

	@Override
	public InputStream openInputStream() throws IOException {
		return file.getInputStream(entry);
	}

}
