package xueli.mcremake.client;

import java.util.UUID;

import org.lwjgl.opengl.GL30;

import xueli.game2.camera3d.MovableCamera;
import xueli.game2.display.GameDisplay;
import xueli.game2.ecs.ResourceListImpl;
import xueli.game2.input.DefaultKeyListener;
import xueli.game2.input.DefaultMouseListener;
import xueli.game2.input.KeyBindings;
import xueli.game2.renderer.ui.Gui;
import xueli.game2.resource.ResourceHolder;
import xueli.mcremake.client.gui.universal.UniversalGui;
import xueli.mcremake.client.player.ClientPlayer;
import xueli.mcremake.client.renderer.item.ItemRenderMaster;
import xueli.mcremake.client.renderer.world.WorldRenderer;
import xueli.mcremake.core.entity.PickCollider;
import xueli.mcremake.core.entity.PickResult;
import xueli.mcremake.core.world.WorldDimension;
import xueli.mcremake.network.ServerPlayerInfo;
import xueli.mcremake.registry.GameRegistry;
import xueli.mcremake.registry.ItemRenderTypes;
import xueli.mcremake.registry.BlockIconGenerator;
import xueli.mcremake.registry.BlockRenderTypes;
import xueli.mcremake.registry.TerrainTextureAtlas;
import xueli.utils.events.EventBus;

// TODO: Combine different overlay with different listener because they are "one to one".
/**
 * This is the main class of game client.<br/>
 * <br/>
 * SOME PRINCIPLES:<br/>
 * 1. The render resources should be added to "renderResources" so that it can be reloaded.<br/>
 * 2. Item system and block system follow the same rules: a render type which you can have access
 * to every resource, initialize everything that render needs; a "vertex gatherer" separated by
 * each type of blocks or items so that every item can choose its render type.
 * 
 */
public class CraftGameClient extends GameDisplay {

	public static final ServerPlayerInfo PLAYER_INFO = new ServerPlayerInfo("LovelyZeeiam", UUID.fromString("a5538060-b314-4cb0-90cd-ead6c59f16a7"));
	
	public final KeyBindings keyBindings = new KeyBindings(), mouseBindings = new KeyBindings();
	private final DefaultKeyListener keyListener = new DefaultKeyListener(keyBindings);
	private final DefaultMouseListener mouseListener = new DefaultMouseListener(mouseBindings);
	
	private UniversalGui universalGui;

	public final EventBus worldBus = new EventBus();
//	public final EventBus GuiEventBus = new EventBus();

	private final ResourceListImpl renderResources = new ResourceListImpl();
	
	private WorldDimension world;
	private ListenableBufferedWorldAccessible bufferedWorld;
	private WorldRenderer worldRenderer;
	
	private ItemRenderMaster itemRenderer;
	
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
		
		this.renderResources.add(new TerrainTextureAtlas(this));
		this.renderResources.add(new BlockIconGenerator(this));
		
		this.resourceManager.addResourceHolder(() -> {
			this.renderResources.values().forEach(o -> {
				if(o instanceof ResourceHolder holder) {
					holder.reload();
				}
			});
		});
		
		this.world = new WorldDimension(this);
		this.bufferedWorld = new ListenableBufferedWorldAccessible(this.world, worldBus);
		this.worldRenderer = new WorldRenderer(new BlockRenderTypes(renderResources), this);
		this.resourceManager.addResourceHolder(worldRenderer);
		
		this.itemRenderer = new ItemRenderMaster(new ItemRenderTypes(renderResources), this);
		this.resourceManager.addResourceHolder(itemRenderer);

		this.world.init();

		this.player = new ClientPlayer(this);
		this.player.x = 0;
		this.player.y = 16;
		this.player.z = 0;
		this.picker = new PickCollider(this.bufferedWorld);

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
		
		Gui gui = getGuiManager();
		gui.begin(getWidth(), getHeight());
		itemRenderer.renderUI(GameRegistry.ITEM_BLOCK_STONE, null, 0, 0, 256, 256, gui);
		gui.finish();
		
		
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
		if(this.worldRenderer != null) {
			worldRenderer.release();
		}

	}
	
	public ResourceListImpl getRenderResources() {
		return renderResources;
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
