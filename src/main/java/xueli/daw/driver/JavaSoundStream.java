package xueli.daw.driver;

public interface JavaSoundStream {
	
	default public void write(byte[] b) {
		this.write(b, 0, b.length);
	}
	
	public void write(byte[] b, int offset, int length);
	
	public void close();
	
}
