package xueli.game2.display;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import xueli.game.utils.FPSCalculator;
import xueli.game2.Timer;
import xueli.game2.lifecycle.RunnableLifeCycle;
import xueli.game2.renderer.ui.MyGui;
import xueli.game2.renderer.ui.OverlayManager;
import xueli.game2.resource.manager.BackwardResourceManager;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.ResourceManager;
import xueli.game2.resource.provider.ClassLoaderResourceProvider;
import xueli.game2.resource.provider.ResourceProvider;
import xueli.game2.resource.submanager.render.font.FontRenderResource;
import xueli.game2.resource.submanager.render.shader.ShaderRenderResource;
import xueli.game2.resource.submanager.render.texture.TextureMissing;
import xueli.game2.resource.submanager.render.texture.TextureRenderResource;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasTextureRenderResource;
import xueli.utils.exception.CrashReport;

import java.io.IOException;
import java.util.List;

public abstract class GameDisplay implements RunnableLifeCycle, RenderResourceProvider, KeyInputListener, WindowSizeListener {

	protected Display display;

	protected Timer timer = new Timer();
	protected FPSCalculator fps = new FPSCalculator();

	private boolean shouldCrash = false;

	protected ChainedResourceManager resourceManager;
	protected TextureRenderResource textureResource;
	protected AtlasTextureRenderResource atlasTextureResource;
	protected ShaderRenderResource shaderResource;
	protected FontRenderResource fontResource;
	
	protected OverlayManager overlayManager;

	private final MyGui gui;
	
	public GameDisplay(int initialWidth, int initialHeight, String mainTitle) {
		this.display = new Display(initialWidth, initialHeight, mainTitle);

		List<ResourceProvider> resourceProviders = List.of(new ClassLoaderResourceProvider(true));
		this.resourceManager = new BackwardResourceManager(resourceProviders);

		this.gui = new MyGui(this);

		this.textureResource = new TextureRenderResource(gui, resourceManager);
		this.atlasTextureResource = new AtlasTextureRenderResource(this.textureResource);
		this.shaderResource = new ShaderRenderResource(resourceManager);
		this.fontResource = new FontRenderResource(gui, resourceManager);

		this.overlayManager = new OverlayManager(this);

		
	}

	@Override
	public final void init() {
		this.display.create();

		// Trigger its loading
		TextureMissing.init();

		this.gui.init();
		this.overlayManager.init();

		try {
			renderInit();
		} catch (Exception e) {
			this.announceCrash("Init", e);
		}

		this.printDeviceInfo();

		this.display.show();

	}

	@Override
	public final void tick() {
		timer.tick();
		fps.tick();

		GL30.glViewport(0, 0, display.getWidth(), display.getHeight());
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

		if(this.overlayManager.hasOverlay()) {
			getDisplay().setMouseGrabbed(false);
			this.overlayManager.tick();
		} else {
			getDisplay().setMouseGrabbed(true);
			this.render();
		}

		this.gui.tick();

		this.display.update();

		this.checkGLError("Post-Render");

	}

	@Override
	public boolean isRunning() {
		return this.display.isRunning() && !shouldCrash;
	}

	@Override
	public final void release() {
		this.display.hide();
		this.gui.release();
		this.overlayManager.release();
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

	public void announceCrash(String state, Throwable t) {
		this.shouldCrash = true;
		new CrashReport(state, t).showCrashReport();

	}

	public void checkGLError(String state) {
		int error = -1;
		while (error != 0) {
			error = GL11.glGetError();
			if (error != 0)
				System.out.println("[" + state + "] OpenGL Error: " + error);
		}
	}

	private void printDeviceInfo() {
		String nameString = GL11.glGetString(GL11.GL_VENDOR);
		String platform = GL11.glGetString(GL11.GL_RENDERER);
		String glVersion = GL11.glGetString(GL11.GL_VERSION);
		System.out.println("[DeviceInfo] OpenGL: " + nameString + ", " + platform + ", " + glVersion);

	}

	public Display getDisplay() {
		return display;
	}

	public int getWidth() {
		return display.getWidth();
	}

	public int getHeight() {
		return display.getHeight();
	}

	public float getDisplayScale() {
		return display.getDisplayScale();
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

	public AtlasTextureRenderResource getAtlasTextureResource() {
		return atlasTextureResource;
	}

	public FontRenderResource getFontResource() {
		return fontResource;
	}

	public MyGui getGuiManager() {
		return gui;
	}

	public OverlayManager getOverlayManager() {
		return overlayManager;
	}

}
