package xueli.craftgame.client;

public abstract class IOAdapter implements AutoCloseable {
	
	private final CraftGameClient ctx;
	
	public IOAdapter(CraftGameClient ctx) {
		this.ctx = ctx;
		
	}
	
	public abstract void start() throws Exception;
	
	public CraftGameClient getContext() {
		return ctx;
	}
	
}
