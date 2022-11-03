package xueli.utils.download;

/**
 * The <code>Range</code> class indicates the range of the file. What is worth noticing is that the
 * range is closed at the both sides, enabling it to apply to the HTTP request directly but supposed to
 * be processed when it comes to file seek like <code>RandomAccessFile</code>.
 */
class Range {

	private final long from, to;

	public Range(long from, long to) {
		this.from = from;
		this.to = to;
	}

	public long getFrom() {
		return from;
	}

	public long getTo() {
		return to;
	}

	@Override
	public String toString() {
		return "Range{" +
				"from=" + from +
				", to=" + to +
				'}';
	}

}
