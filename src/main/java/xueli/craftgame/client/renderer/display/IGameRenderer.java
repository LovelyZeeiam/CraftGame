package xueli.craftgame.client.renderer.display;

import java.io.IOException;
import java.util.List;

import xueli.craftgame.Constants;
import xueli.craftgame.client.LifeCycle;
import xueli.craftgame.resource.manager.BackwardResourceManager;
import xueli.craftgame.resource.manager.ChainedResourceManager;
import xueli.craftgame.resource.manager.ResourceManager;
import xueli.craftgame.resource.provider.ClassLoaderResourceProvider;
import xueli.craftgame.resource.provider.ResourceProvider;
import xueli.craftgame.resource.render.shader.ShaderRenderResource;
import xueli.craftgame.resource.render.texture.TextureRenderResource;

public abstract class IGameRenderer implements LifeCycle, RenderResourceProvider, KeyInputListener, WindowSizeListener {

	protected Display display;

	protected ChainedResourceManager resourceManager;
	protected TextureRenderResource textureResource;
	private ShaderRenderResource shaderResource;

	public IGameRenderer(int initialWidth, int initialHeight) {
		this.display = new Display(initialWidth, initialHeight, Constants.GAME_NAME_FULL);

		List<ResourceProvider> resourceProviders = List.of(new ClassLoaderResourceProvider(true));
		this.resourceManager = new BackwardResourceManager(resourceProviders);
		
		this.textureResource = new TextureRenderResource(resourceManager);
		this.shaderResource = new ShaderRenderResource(resourceManager);
		
	}

	@Override
	public final void init() {
		this.display.create();
		renderInit();
		this.display.show();

	}

	@Override
	public final void tick() {
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
