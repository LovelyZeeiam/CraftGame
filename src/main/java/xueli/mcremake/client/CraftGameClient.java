package xueli.mcremake.client;

import java.util.UUID;

import org.lwjgl.opengl.GL30;

import xueli.game2.camera3d.MovableCamera;
import xueli.game2.display.GameDisplay;
import xueli.game2.input.DefaultKeyListener;
import xueli.game2.input.DefaultMouseListener;
import xueli.game2.input.KeyBindings;
import xueli.mcremake.client.gui.universal.UniversalGui;
import xueli.mcremake.client.player.ClientPlayer;
import xueli.mcremake.client.renderer.world.WorldRenderer;
import xueli.mcremake.core.entity.PickCollider;
import xueli.mcremake.core.entity.PickResult;
import xueli.mcremake.core.world.WorldDimension;
import xueli.mcremake.network.ServerPlayerInfo;
import xueli.mcremake.registry.GameRegistry;
import xueli.utils.events.EventBus;

// TODO: Combine different overlay with different listener because they are "one to one".
public class CraftGameClient extends GameDisplay {

	public static final ServerPlayerInfo PLAYER_INFO = new ServerPlayerInfo("LovelyZeeiam", UUID.fromString("a5538060-b314-4cb0-90cd-ead6c59f16a7"));
	
	public final KeyBindings keyBindings = new KeyBindings(), mouseBindings = new KeyBindings();
	private final DefaultKeyListener keyListener = new DefaultKeyListener(keyBindings);
	private final DefaultMouseListener mouseListener = new DefaultMouseListener(mouseBindings);
	
	private UniversalGui universalGui;

	public final EventBus WorldEventBus = new EventBus();
//	public final EventBus GuiEventBus = new EventBus();

	private WorldDimension world;
	private ListenableBufferedWorldAccessible bufferedWorld;
	private WorldRenderer worldRenderer;
	
	private ClientPlayer player;
	private PickCollider picker;
	private PickResult pickResult;
	private AttackButtonHandler attackHandler = new AttackButtonHandler(this);
	private UseButtonHandler useHandler = new UseButtonHandler(this);

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");

	}

	
	@Override
	protected void renderInit() {
		universalGui = new UniversalGui(this);
		this.resourceManager.addResourceHolder(universalGui);

		GameRegistry.callForClazzLoad();

		this.world = new WorldDimension(this);
		this.bufferedWorld = new ListenableBufferedWorldAccessible(this.world, WorldEventBus);
		this.worldRenderer = new WorldRenderer(this);
		this.resourceManager.addResourceHolder(worldRenderer);

		this.world.init();

		this.player = new ClientPlayer(this);
		this.player.x = 0;
		this.player.y = 16;
		this.player.z = 0;
		this.picker = new PickCollider(this.bufferedWorld);

		GL30.glEnable(GL30.GL_DEPTH_TEST);
		GL30.glEnable(GL30.GL_CULL_FACE);

	}

	@Override
	protected void render() {
		GL30.glClearColor(0.5f, 0.5f, 0.8f, 1.0f);

		this.player.inputRefresh();
		for (int i = 0; i < timer.getNumShouldTick(); i++) {
			this.player.tick();
			this.bufferedWorld.flush();
		}
		
		this.renderTick();
		while(attackHandler.tick()) {
			this.renderTick();
		}
		while(useHandler.tick()) {
			this.renderTick();
		}
		
		worldRenderer.render();

	}
	
	private void renderTick() {
		MovableCamera camera = this.player.getCamera(timer.getRemainProgress());
		this.pickResult = this.picker.pick(camera, 6.0f);
		worldRenderer.setCamera(camera);
		
	}
	
	@Override
	public void onKey(int key, int scancode, int action, int mods) {
		keyListener.onKey(key, scancode, action, mods);
	}
	
	@Override
	public void onMouseButton(int button, int action, int mods) {
		mouseListener.onMouseButton(button, action, mods);
	}

	@Override
	protected void renderRelease() {
		worldRenderer.release();

	}
	
	public WorldDimension getUnsafeImmediateWorld() {
		return world;
	}

	public ListenableBufferedWorldAccessible getWorld() {
		return bufferedWorld;
	}

	public PickResult getPickResult() {
		return pickResult;
	}

	public UniversalGui getUniversalGui() {
		return universalGui;
	}

	public ClientPlayer getPlayer() {
		return player;
	}

}
