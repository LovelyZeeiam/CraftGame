package xueli.mcremake.client;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lwjgl.glfw.GLFW;

import xueli.game2.display.GameDisplay;
import xueli.game2.ecs.ResourceListImpl;
import xueli.game2.resource.ResourceHolder;
import xueli.mcremake.client.renderer.item.ItemRenderMaster;
import xueli.mcremake.client.renderer.world.block.BlockRenderTypes;
import xueli.mcremake.client.systems.GameRenderSystem;
import xueli.mcremake.client.systems.ItemTypeSelectSystem;
import xueli.mcremake.client.systems.KeyBindingUpdateSystem;
import xueli.mcremake.client.systems.PlayerUpdateSystem;
import xueli.mcremake.core.world.WorldDimension;
import xueli.mcremake.network.ServerPlayerInfo;
import xueli.mcremake.registry.GameRegistry;
import xueli.mcremake.registry.TerrainTextureAtlas;
import xueli.mcremake.registry.item.ItemRenderTypes;
import xueli.utils.concurrent.ControllerExecutorService;

/**
 * This is the main class of game client.<br/>
 * <br/>
 * SOME PRINCIPLES:<br/>
 * 1. The render resources should be added to "renderResources" so that it can be reloaded.<br/>
 * 2. Item system and block system follow the same rules: a render type which you can have access
 * to every resource, initialize everything that render needs; a "vertex gatherer" separated by
 * each type of blocks or items so that every item can choose its render type.<br/>
 * 
 * TODOs: <br/>
 * 1. When it comes to infinity world we just use ticket-like mechanism (each time we iterate the chunks
 * and spread the ticket and load it until it got under zero) but at first I should have the chunk generator
 * done.
 * 2. Also set 2 state of a chunk, "LOADING", "DONE", determining whether the chunk is ready to go
 * 3. Separate BlockInfo and BlockRenderer, and in WorldRenderer class, we just attach the certain block renderer to the BlockInfo 
 * 
 */
public class CraftGameClient extends GameDisplay {

	public static final ServerPlayerInfo PLAYER_INFO = new ServerPlayerInfo("LovelyZeeiam", UUID.fromString("a5538060-b314-4cb0-90cd-ead6c59f16a7"));
	
	public final GameState state = new GameState();
	final ResourceListImpl<Object> renderResources = new ResourceListImpl<>();
	final ResourceListImpl<IGameSystem> systems = new ResourceListImpl<>();
	
	private final ExecutorService asyncExecutor = Executors.newWorkStealingPool();
	private final ControllerExecutorService mainThreadExecutor = new ControllerExecutorService();
	
	public CraftGameClient() {
		super(1280, 720, "Minecraft Classic Forever");
		
	}
	
	@Override
	protected void renderInit() {
		GameRegistry.callForClazzLoad();
		
		state.worldDirect = new WorldDimension(this);
		state.world = new ListenableBufferedWorldAccessible(state.worldDirect, eventbus);

		this.renderResources.add(new TerrainTextureAtlas(this));
		this.renderResources.add(new BlockIconGenerator(this));
		this.renderResources.add(new WorldRenderer(new BlockRenderTypes(this), this));
		this.renderResources.add(new ItemRenderMaster(new ItemRenderTypes(renderResources), this));
		
		this.systems.add(new KeyBindingUpdateSystem());
		this.systems.add(new PlayerUpdateSystem());
		this.systems.add(new ItemTypeSelectSystem());
		this.systems.add(new GameRenderSystem());
		
		this.systems.values().forEach(o -> o.start(this));

		state.worldDirect.init();

		this.resourceManager.addResourceHolder(() -> {
			this.renderResources.values().forEach(o -> {
				if(o instanceof ResourceHolder holder) {
					holder.reload();
				}
			});
		});

	}

	@Override
	protected void render() {
		mainThreadExecutor.tick();
		
		for (int i = 0; i < this.timer.getNumShouldTick(); i++) {
			this.systems.values().forEach(o -> o.tick(this));
			this.state.tickCount++;
		}
		this.state.partialTick = this.timer.getRemainProgress();

		state.world.flush();

		this.systems.values().forEach(o -> o.update(this));

		if(this.state.keyBindings.isPressed(GLFW.GLFW_KEY_ESCAPE)) {
			this.announceClose();
		}
		
	}

	@Override
	protected void renderRelease() {
		this.renderResources.values().forEach(o -> {
			if(o instanceof IGameSystem system) {
				system.release(this);
			}
		});
		
		asyncExecutor.shutdownNow();
		mainThreadExecutor.shutdownNow();

	}
	
	public <T> T getRenderResources(Class<T> clazz) {
		return this.renderResources.get(clazz);
	}
	
	public ExecutorService getAsyncExecutor() {
		return asyncExecutor;
	}
	
	public ExecutorService getMainThreadExecutor() {
		return mainThreadExecutor;
	}
	
}
