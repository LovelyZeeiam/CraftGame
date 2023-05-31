package xueli.utils.download;

import java.io.File;

class RangeFile extends Range {

	private final File file;

	public RangeFile(long start, long to, File file) {
		super(start, to);
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	@Override
	public String toString() {
		return "RangeFile{" + "from=" + getFrom() + ", to=" + getTo() + ", file=" + file + '}';
	}

}
