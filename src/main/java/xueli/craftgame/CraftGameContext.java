package xueli.craftgame;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.lwjgl.opengl.GL11;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import xueli.craftgame.block.Blocks;
import xueli.craftgame.client.LocalTicker;
import xueli.craftgame.entitytest.item.ItemStack;
import xueli.craftgame.entitytest.item.Items;
import xueli.craftgame.event.EventInventorySlotChosenMove;
import xueli.craftgame.event.EventLoadChunk;
import xueli.craftgame.event.EventRaiseGUI;
import xueli.craftgame.event.EventRemoveChunk;
import xueli.craftgame.event.EventSetBlock;
import xueli.craftgame.player.Inventory;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.player.PlayerInfo;
import xueli.craftgame.renderer.GameViewRenderer;
import xueli.craftgame.renderer.HUDRenderer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.resource.manager.BackwardResourceManager;
import xueli.craftgame.resource.provider.ClassLoaderResourceProvider;
import xueli.craftgame.resource.provider.ResourceProvider;
import xueli.craftgame.resource.render.texture.TextureRenderResource;
import xueli.craftgame.world.World;
import xueli.game.Game;
import xueli.game.input.InputManager;
import xueli.game.resource.ResourceMaster;
import xueli.game.vector.Vector;

public class CraftGameContext extends Game implements Runnable {

	public static CraftGameContext ctx;

	private PlayerInfo playerInfo = new PlayerInfo("LoveliZeeiam");

	// Game Controller Eventbus
	private ExecutorService eventBusExecutor = Executors
			.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("Context-EventBus-pool-%d").build());
	private AsyncEventBus eventBus = new AsyncEventBus(eventBusExecutor);

	private BackwardResourceManager resourceManager;
	private TextureRenderResource renderResource;

	private World world;
	// All the events must be sent to here first
	private LocalTicker ticker;
	private InputManager inputManager;

	private WorldRenderer worldRenderer;
	private HUDRenderer hudRenderer;
	private GameViewRenderer viewRenderer;

	private LocalPlayer player;

	private boolean isClient = true;

	public CraftGameContext() {
		super(800, 600, "Minecraft PE");
		ctx = this;

		eventBus.register(this);

	}

	@Override
	public void onCreate() {
		List<ResourceProvider> resourceProviders = List.of(new ClassLoaderResourceProvider(true));
		this.resourceManager = new BackwardResourceManager(resourceProviders);
		this.renderResource = new TextureRenderResource(resourceManager);

		this.inputManager = new InputManager(this);
		this.ticker = new LocalTicker(this);
		this.world = new World(this);
		this.player = new LocalPlayer(this);
		this.worldRenderer = new WorldRenderer(player, this);
		this.hudRenderer = new HUDRenderer(this);
		this.viewRenderer = new GameViewRenderer(this);

		this.worldRenderer.init();
		this.hudRenderer.init();
		this.viewRenderer.init();

		Blocks.clazzInitCall();
		Items.clazzInitCall();

		Blocks.initCallForRenderer(this);
		Items.initCallForRenderer(this);

		this.world.chunkInit();

		{
			Inventory inventory = player.getInventory();
			inventory.set(0, new ItemStack(Items.blockItems.get(Blocks.BLOCK_STONE), 64));
			inventory.set(1, new ItemStack(Items.blockItems.get(Blocks.BLOCK_BEDROCK), 64));
			inventory.set(2, new ItemStack(Items.blockItems.get(Blocks.BLOCK_DIRT), 64));
			inventory.set(3, new ItemStack(Items.blockItems.get(Blocks.BLOCK_GRASS), 64));

		}

		{
			Vector playerPos = this.player.getPlayer().getPos();
			playerPos.x = 25;
			playerPos.y = 10;
			playerPos.z = 25;
		}

		this.ticker.start();

		getDisplay().setMouseGrabbed(true);

	}

	@Override
	public void onTick() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		this.world.tick();
		this.player.tick();

		glClearColor(0.7f, 0.7f, 0.9f, 1.0f);
		this.worldRenderer.render();
		this.hudRenderer.render();
		this.viewRenderer.render();

		/*
		 * if (getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE)) {
		 * getDisplay().setMouseGrabbed(!getDisplay().isMouseGrabbed()); }
		 */

	}

	@Override
	public void onSize(int width, int height) {
		super.onSize(width, height);

		this.worldRenderer.onSize(width, height);
		this.hudRenderer.onSize(width, height);
		this.viewRenderer.onSize(width, height);

	}

	@Override
	public void onRelease() {
		this.ticker.stop();
		this.inputManager.release();

		this.worldRenderer.release();
		this.hudRenderer.release();

		try {
			if (!eventBusExecutor.awaitTermination(500, TimeUnit.MILLISECONDS)) {
				eventBusExecutor.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		world.release();

	}

	@Subscribe
	public void onSetBlock(EventSetBlock event) {
		world.onSetBlock(event);
		worldRenderer.onMeshShouldRebuilt(event);

	}

	@Subscribe
	public void onNewChunk(EventLoadChunk event) {
		worldRenderer.onNewChunk(event);

	}

	@Subscribe
	public void onRemoveChunk(EventRemoveChunk event) {
		worldRenderer.onRemoveChunk(event);

	}

	@Subscribe
	public void onInventorySlotChosenMove(EventInventorySlotChosenMove event) {
		player.onInventorySlotChosenMove(event);

	}

	@Subscribe
	public void onRaiseView(EventRaiseGUI event) {
		viewRenderer.onRaiseView(event);

	}

	public void submitEventToMainTicker(Object eventObject) {
		ticker.submitEvent(eventObject);
	}

	public void submitEvent(Object eventObject) {
		eventBus.post(eventObject);
	}

	public World getWorld() {
		return world;
	}

	public LocalTicker getTicker() {
		return ticker;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public LocalPlayer getPlayer() {
		return player;
	}

	public WorldRenderer getWorldRenderer() {
		return worldRenderer;
	}

	public GameViewRenderer getViewRenderer() {
		return viewRenderer;
	}

	public HUDRenderer getHudRenderer() {
		return hudRenderer;
	}

	public ResourceMaster getResourceMaster() {
		return null;
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public boolean isClient() {
		return isClient;
	}

}
