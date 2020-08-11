package xueLi.craftGame;

public class Tile {

	public BlockData data;
	private BlockListener listener;
	
	private int x,y,z;
	
	public Tile(int id) {
		
	}
	
	public Tile(BlockData data) {
		this.data = data;
		this.listener = data.getListener();
		
		this.listener.onCreate();
		
	}

	public BlockListener getListener() {
		return listener;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
	

}
