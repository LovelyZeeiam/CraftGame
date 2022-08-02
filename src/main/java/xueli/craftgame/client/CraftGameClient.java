package xueli.craftgame.client;

import xueli.craftgame.client.renderer.display.IGameRenderer;

public class CraftGameClient extends IGameRenderer {

	public CraftGameClient() {
		super(800, 600);
	}

	@Override
	protected void renderInit() {
		
	}
		
	@Override
	protected void render() {
		
	}

	@Override
	protected void renderRelease() {
		
	}

	public static void main(String[] args) {
		new Thread(new CraftGameClient()).start();
	}

}
