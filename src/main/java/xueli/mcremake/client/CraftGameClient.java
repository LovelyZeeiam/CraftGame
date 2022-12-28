package xueli.mcremake.client;

import org.lwjgl.opengl.GL30;
import xueli.game2.display.GameDisplay;
import xueli.mcremake.client.gui.universal.UniversalGui;
import xueli.mcremake.client.renderer.world.WorldRenderer;
import xueli.mcremake.core.world.BufferedWorldAccessible;
import xueli.mcremake.core.world.WorldDimension;
import xueli.mcremake.network.ServerPlayerInfo;
import xueli.mcremake.registry.GameRegistry;
import xueli.utils.EventBus;

import java.util.UUID;

// TODO: Combine different overlay with different listener because they are "one to one".
public class CraftGameClient extends GameDisplay {

	public static final ServerPlayerInfo PLAYER_INFO = new ServerPlayerInfo("LovelyZeeiam", UUID.fromString("a5538060-b314-4cb0-90cd-ead6c59f16a7"));

	private xueli.mcremake.client.gui.universal.UniversalGui UniversalGui;

	public final EventBus WorldEventBus = new EventBus();
//	public final EventBus GuiEventBus = new EventBus();

	private WorldDimension world;
	private BufferedWorldAccessible bufferedWorld;
	private ClientPlayer player;
	private WorldRenderer worldRenderer;

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");

	}

	@Override
	protected void renderInit() {
		UniversalGui = new UniversalGui(this);
		getResourceManager().addResourceHolder(UniversalGui);

		GameRegistry.callForClazzLoad();

		this.world = new WorldDimension(this);
		this.bufferedWorld = new BufferedWorldAccessible(this.world);
		this.worldRenderer = new WorldRenderer(this);
		getResourceManager().addResourceHolder(worldRenderer);

		this.world.init();

		this.player = new ClientPlayer(this);
		this.player.x = 0;
		this.player.y = 16;
		this.player.z = 0;

		GL30.glEnable(GL30.GL_DEPTH_TEST);
		GL30.glEnable(GL30.GL_CULL_FACE);

	}

	@Override
	protected void render() {
		GL30.glClearColor(0.5f, 0.5f, 0.8f, 1.0f);

		this.player.inputRefresh();

		this.timer.runTick(() -> {
			this.player.tick();
		});
		this.bufferedWorld.flush();

		worldRenderer.render(timer.getRemainProgress());

	}

	@Override
	protected void renderRelease() {
		worldRenderer.release();

	}

	public WorldDimension getUnsafeImmediateWorld() {
		return world;
	}

	public BufferedWorldAccessible getWorld() {
		return bufferedWorld;
	}

	public UniversalGui getUniversalGui() {
		return UniversalGui;
	}

	public ClientPlayer getPlayer() {
		return player;
	}

}
