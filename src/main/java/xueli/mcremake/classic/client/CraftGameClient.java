package xueli.mcremake.classic.client;

import xueli.game2.display.GameDisplay;
import xueli.mcremake.classic.client.gui.universal.UniversalGui;
import xueli.mcremake.classic.client.renderer.world.WorldRenderer;
import xueli.mcremake.classic.core.world.WorldDimension;
import xueli.mcremake.classic.network.ServerPlayerInfo;
import xueli.utils.EventBus;

import java.util.UUID;

// TODO: Combine different overlay with different listener because they are "one to one".
public class CraftGameClient extends GameDisplay {

	public static final ServerPlayerInfo PLAYER_INFO = new ServerPlayerInfo("LovelyZeeiam", UUID.fromString("a5538060-b314-4cb0-90cd-ead6c59f16a7"));

	private UniversalGui UniversalGui;

	public final EventBus WorldEventBus = new EventBus();
//	public final EventBus GuiEventBus = new EventBus();

	private WorldDimension world;
	private WorldRenderer worldRenderer;

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");

	}

	@Override
	protected void renderInit() {
		UniversalGui = new UniversalGui(this);
		getResourceManager().addResourceHolder(UniversalGui);

		this.world = new WorldDimension(this);
		this.worldRenderer = new WorldRenderer(this);

		this.world.init();

	}

	@Override
	protected void render() {
		worldRenderer.render();

	}

	@Override
	protected void renderRelease() {
		worldRenderer.release();

	}

	public WorldDimension getWorld() {
		return world;
	}

	public UniversalGui getUniversalGui() {
		return UniversalGui;
	}

}
