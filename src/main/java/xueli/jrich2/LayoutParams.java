package xueli.jrich2;

public class LayoutParams {
	
	public static final int JUST_WRAP_CONTENT = -1;
	public static final int FILL_PARENT = -2;
	
	private int width;
	private int height;
	
	public LayoutParams(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public LayoutParams(LayoutParams p) {
		this.width = p.width;
		this.height = p.height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
