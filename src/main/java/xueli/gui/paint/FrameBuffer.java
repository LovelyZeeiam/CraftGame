package xueli.gui.paint;

public interface FrameBuffer {

	public GraphicDriver getGraphicDriver();

	public int getImageId();

	public void resize(int width, int height);

	public void release();
	
	public int getWidth();
	
	public int getHeight();

}
