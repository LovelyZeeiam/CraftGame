package xueli.game2.display;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import xueli.game2.Timer;
import xueli.game2.display.event.CursorPositionEvent;
import xueli.game2.display.event.WindowKeyEvent;
import xueli.game2.display.event.WindowMouseButtonEvent;
import xueli.game2.display.event.WindowSizedEvent;
import xueli.game2.lifecycle.RunnableLifeCycle;
import xueli.game2.resource.manager.BackwardResourceManager;
import xueli.game2.resource.provider.ClassLoaderResourceProvider;
import xueli.game2.resource.provider.ResourceProvider;
import xueli.game2.resource.submanager.render.shader.ShaderRenderResource;
import xueli.game2.resource.submanager.render.texture.TextureRenderResource;
import xueli.gui.WindowSizeProvider;
import xueli.utils.concurrent.ControllerExecutorService;
import xueli.utils.events.EventBus;
import xueli.utils.exception.CrashReport;
import xueli.utils.logger.Logger;

public abstract class GameDisplay implements RunnableLifeCycle, WindowSizeProvider {

	private static final Logger LOGGER = new Logger();

	protected Display display;

	public final Timer timer = new Timer();
	public final FPSCalculator fps = new FPSCalculator();

	private boolean shouldCrash = false;

	public final BackwardResourceManager resourceManager;
	public final TextureRenderResource textureResource;
	public final ShaderRenderResource shaderResource;

//	public final OverlayManager overlayManager;

	public final EventBus eventbus = new EventBus();

	private final ExecutorService asyncExecutor = Executors.newWorkStealingPool();
	private final ControllerExecutorService mainThreadExecutor = new ControllerExecutorService();

	public GameDisplay(int initialWidth, int initialHeight, String mainTitle) {
		this.display = new Display(initialWidth, initialHeight, mainTitle);

		List<ResourceProvider> resourceProviders = List.of(new ClassLoaderResourceProvider(true));
		this.resourceManager = new BackwardResourceManager(resourceProviders);

		this.textureResource = new TextureRenderResource(resourceManager);
		this.shaderResource = new ShaderRenderResource(resourceManager);
//		this.fontResource = new FontRenderResource(gui, resourceManager);

//		this.overlayManager = new OverlayManager(this);

	}

	@Override
	public void init() {
		this.display.create();
		this.display.addKeyListener(
				(key, scancode, action, mods) -> eventbus.post(new WindowKeyEvent(key, scancode, action, mods)));
		this.display.addWindowSizedListener((w, h) -> eventbus.post(new WindowSizedEvent(w, h)));
		this.display.addMouseInputListener(
				(btn, action, mods) -> eventbus.post(new WindowMouseButtonEvent(btn, action, mods)));
		this.display.addMousePositionListener((x, y) -> eventbus.post(new CursorPositionEvent(x, y)));

//		this.resourceManager.addResourceHolder(this.overlayManager);

		try {
//			this.overlayManager.init();
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
		try {
			mainThreadExecutor.tick();
		} catch (Exception e) {
			this.announceCrash("MainThreadExecutor", e);
		}

		GL30.glViewport(0, 0, display.getWidth(), display.getHeight());
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

//		if(this.overlayManager.hasOverlay()) {
//			getDisplay().setMouseGrabbed(false);
//			this.overlayManager.tick();
//		} else {
//			getDisplay().setMouseGrabbed(true);
//		}

//		try {
		this.render();
//		} catch (Exception e) {
//			this.announceCrash("Render", e);
//		}

		this.display.update();

		this.checkGLError("Post-Render");

	}

	@Override
	public boolean isRunning() {
		return this.display.isRunning() && !shouldCrash;
	}

	@Override
	public void release() {
		this.display.hide();
//		this.overlayManager.release();
		this.renderRelease();

		asyncExecutor.shutdownNow();
		mainThreadExecutor.shutdownNow();

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
		String version = GL11.glGetString(GL11.GL_VERSION);
		LOGGER.warning("[DeviceInfo] OpenGL: " + nameString + ", " + platform + ", " + version);

	}

	public ExecutorService getAsyncExecutor() {
		return asyncExecutor;
	}

	public ExecutorService getMainThreadExecutor() {
		return mainThreadExecutor;
	}

	public Display getDisplay() {
		return display;
	}

	@Override
	public int getWidth() {
		return display.getWidth();
	}

	@Override
	public int getHeight() {
		return display.getHeight();
	}

	public float getDisplayScale() {
		return display.getDisplayScale();
	}

}
