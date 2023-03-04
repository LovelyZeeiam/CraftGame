package xueli.game2.display;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import xueli.game2.Timer;
import xueli.game2.lifecycle.RunnableLifeCycle;
import xueli.game2.renderer.ui.Gui;
import xueli.game2.renderer.ui.OverlayManager;
import xueli.game2.resource.manager.BackwardResourceManager;
import xueli.game2.resource.provider.ClassLoaderResourceProvider;
import xueli.game2.resource.provider.ResourceProvider;
import xueli.game2.resource.submanager.render.shader.ShaderRenderResource;
import xueli.game2.resource.submanager.render.texture.TextureRenderResource;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasTextureRenderResource;
import xueli.game2.worker.GameWorker;
import xueli.utils.exception.CrashReport;
import xueli.utils.logger.Logger;

public abstract class GameDisplay implements RunnableLifeCycle, KeyInputListener, WindowSizeListener, MouseInputListener {
	
	private static final Logger LOGGER = new Logger();
	
	protected Display display;
	public final GameWorker worker = new GameWorker();
	
	public final Timer timer = new Timer();
	public final FPSCalculator fps = new FPSCalculator();

	private boolean shouldCrash = false;

	public final BackwardResourceManager resourceManager;
	public final TextureRenderResource textureResource;
	public final AtlasTextureRenderResource atlasTextureResource;
	public final ShaderRenderResource shaderResource;
	
	public final OverlayManager overlayManager;

	private final Gui gui;
	
	public GameDisplay(int initialWidth, int initialHeight, String mainTitle) {
		this.display = new Display(initialWidth, initialHeight, mainTitle);

		List<ResourceProvider> resourceProviders = List.of(new ClassLoaderResourceProvider(true));
		this.resourceManager = new BackwardResourceManager(resourceProviders);

		this.textureResource = new TextureRenderResource(resourceManager);
		this.atlasTextureResource = new AtlasTextureRenderResource(this.textureResource);
		this.shaderResource = new ShaderRenderResource(resourceManager);
//		this.fontResource = new FontRenderResource(gui, resourceManager);

		this.gui = new Gui();
		this.overlayManager = new OverlayManager(this);
		
		
	}

	@Override
	public void init() {
		this.display.create();
		this.display.addKeyListener(this);
		this.display.addWindowSizedListener(this);
		this.display.addMouseInputListener(this);
		
		this.resourceManager.addResourceHolder(this.gui);
		this.resourceManager.addResourceHolder(this.overlayManager);

		try {
			this.gui.init();
			this.overlayManager.init();
			renderInit();
		} catch (Exception e) {
			this.announceCrash("Init", e);
		}

		this.printDeviceInfo();

		this.display.show();

	}

	@Override
	public void tick() {
		timer.tick();
		fps.tick();

		GL30.glViewport(0, 0, display.getWidth(), display.getHeight());
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

		if(this.overlayManager.hasOverlay()) {
			getDisplay().setMouseGrabbed(false);
			this.overlayManager.tick();
		} else {
			getDisplay().setMouseGrabbed(true);
		}
		this.render();

		this.gui.tick();

		this.display.update();
		
		this.checkGLError("Post-Render");
		
		worker.tickMainThread();
		
	}

	@Override
	public boolean isRunning() {
		return this.display.isRunning() && !shouldCrash;
	}

	@Override
	public void release() {
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
	public void onKey(int key, int scancode, int action, int mods) {
	}

	@Override
	public void onMouseButton(int button, int action, int mods) {
	}
	
	public void announceClose() {
		this.display.setRunning(false);
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
		LOGGER.warning("[DeviceInfo] OpenGL: " + nameString + ", " + platform + ", " + glVersion);

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
	
	public Gui getGuiManager() {
		return gui;
	}

}
