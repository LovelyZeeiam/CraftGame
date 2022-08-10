package xueli.game2.display;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL30;

import xueli.craftgame.Constants;
import xueli.game2.Timer;
import xueli.game2.lifecycle.RunnableLifeCycle;
import xueli.game2.renderer.ui.GameUIManager;
import xueli.game2.renderer.ui.NanoVGContext;
import xueli.game2.resource.manager.BackwardResourceManager;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.ResourceManager;
import xueli.game2.resource.provider.ClassLoaderResourceProvider;
import xueli.game2.resource.provider.ResourceProvider;
import xueli.game2.resource.submanager.render.font.FontRenderResource;
import xueli.game2.resource.submanager.render.shader.ShaderRenderResource;
import xueli.game2.resource.submanager.render.texture.TextureRenderResource;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasTextureRenderResource;

public abstract class IGameRenderer implements RunnableLifeCycle, RenderResourceProvider, KeyInputListener, WindowSizeListener {

	protected Display display;
	protected Timer timer = new Timer();

	protected ChainedResourceManager resourceManager;
	protected TextureRenderResource textureResource;
	protected AtlasTextureRenderResource atlasTextureResource;
	protected ShaderRenderResource shaderResource;
	protected FontRenderResource fontResource;
	
	protected GameUIManager uiManager;

	public IGameRenderer(int initialWidth, int initialHeight) {
		this(initialWidth, initialHeight, Constants.GAME_NAME_FULL);
	}
	
	public IGameRenderer(int initialWidth, int initialHeight, String mainTitle) {
		this.display = new Display(initialWidth, initialHeight, mainTitle);

		List<ResourceProvider> resourceProviders = List.of(new ClassLoaderResourceProvider(true));
		this.resourceManager = new BackwardResourceManager(resourceProviders);
		
		this.textureResource = new TextureRenderResource(resourceManager);
		this.atlasTextureResource = new AtlasTextureRenderResource(this.textureResource);
		this.shaderResource = new ShaderRenderResource(resourceManager);
		this.fontResource = new FontRenderResource(resourceManager);
		
		this.uiManager = new GameUIManager(this);
		
	}

	@Override
	public final void init() {
		this.display.create();
		NanoVGContext.INSTANCE.getNvg();
		renderInit();
		this.display.show();

	}

	@Override
	public final void tick() {
		GL30.glViewport(0, 0, display.getWidth(), display.getHeight());
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
		timer.tick();
		this.render();
		this.display.update();
	}

	@Override
	public boolean isRunning() {
		return this.display.isRunning();
	}

	@Override
	public final void release() {
		this.display.hide();
		NanoVGContext.release();
		this.renderRelease();
		
		try {
			this.resourceManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.display.release();

	}

	protected abstract void renderInit();

	protected abstract void render();

	protected abstract void renderRelease();

	@Override
	public void onSize(int width, int height) {
	}
	
	@Override
	public void onInput(int key, int scancode, int action, int mods) {
	}
	
	public Display getDisplay() {
		return display;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public TextureRenderResource getTextureRenderResource() {
		return textureResource;
	}
	
	public ShaderRenderResource getShaderRenderResource() {
		return shaderResource;
	}
	
}
