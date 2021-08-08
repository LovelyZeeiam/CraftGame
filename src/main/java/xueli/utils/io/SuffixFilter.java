package xueli.utils.io;

import java.io.File;
import java.io.FileFilter;

public class SuffixFilter implements FileFilter {

	private String suffix;

	public SuffixFilter(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public boolean accept(File pathname) {
		return pathname.getName().endsWith("." + suffix);
	}

}
