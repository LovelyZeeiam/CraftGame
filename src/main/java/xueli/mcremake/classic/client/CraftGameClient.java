package xueli.mcremake.classic.client;

import xueli.game.vector.Vector2i;
import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.legacy.RenderBuffer;
import xueli.mcremake.classic.client.renderer.RenderTypeSolid;

public class CraftGameClient extends GameDisplay {

	private RenderTypeSolid solidRenderer;

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");
		
	}

	@Override
	protected void renderInit() {
		this.solidRenderer = new RenderTypeSolid();
		this.solidRenderer.init();

		RenderBuffer buf = this.solidRenderer.getRenderBuffer(new Vector2i(0, 0));


	}

	@Override
	protected void render() {
		
		
	}

	@Override
	protected void renderRelease() {
		this.solidRenderer.release();
		
	}

}
