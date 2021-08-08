package xueli.utils.io;

import java.io.File;
import java.io.FileFilter;

public class FolderFilter implements FileFilter {

	public static final FolderFilter INSTANCE = new FolderFilter();

	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory();
	}

}
