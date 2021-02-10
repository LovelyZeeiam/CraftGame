package xueli.craftgame.world;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFont;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.ByteBuffer;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;

import xueli.craftgame.CraftGame;
import xueli.craftgame.State;
import xueli.craftgame.block.Blocks;
import xueli.craftgame.entity.Player;
import xueli.craftgame.view.HUDView;
import xueli.craftgame.view.InGameView;
import xueli.craftgame.view.InventoryView;
import xueli.craftgame.world.renderer.Renderer;
import xueli.craftgame.world.renderer.ShadowMapper;
import xueli.gamengine.resource.Texture;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.MatrixHelper;
import xueli.gamengine.utils.Shader;
import xueli.gamengine.utils.callbacks.KeyCallback;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.View;

public class WorldLogic implements Runnable {

	private final CraftGame cg;
	public boolean running = false;
	public View gameGui;
	public State state;
	private ByteBuffer mappedBuffer, anotherMappedBuffer;
	private int vertexCount = 0;
	private World world;
	private Player player;
	private TextureAtlas blockTextureAtlas;
	private Shader blockRenderShader;

	private int drawDistance = 8;

	/**
	 * frustum culling 依照从前到后的顺序排序几何体 顶点处理器基于32位浮点值工作 Fragment Shader使用16位浮点值工作
	 */

	private Matrix4f playerMatrix = new Matrix4f();

	private long nvg;
	private HashMap<String, Integer> nvgTextures = new HashMap<>();

	private InGameView ingameView;

	private Renderer normalRenderer;
	private ShadowMapper shadowMapper;

	@WorldGLData
	public WorldLogic(CraftGame cg) {
		this.cg = cg;

		normalRenderer = new Renderer(cg, this);
		shadowMapper = new ShadowMapper(cg, this);
		this.blockRenderShader = cg.getShaderResource().get("world");
		
		this.shadowMapper.setDepthMap(blockRenderShader);

		// 游戏内gui绘制
		nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (nvg == 0) {
			Logger.error(new Throwable("[GUI] Emm, You don't want a game without gui, do u?"));
		}

		if (nvgCreateFont(nvg, "game", "res/fonts/Minecraft-Ascii.ttf") == -1) {
			Logger.error("[Font] Can't create font!");
		}

		cg.getTextureManager().getTextures().forEach((e, t) -> {
			if(e.startsWith("ingame.")) {
				nvgTextures.put(e, nvglCreateImageFromHandle(nvg, t.id, t.width, t.height, NVG_IMAGE_NEAREST));
			}
		});

		Blocks.init(nvg, cg, (TextureAtlas) cg.getTextureManager().getTexture("blocks"));

		ingameView = new HUDView(this);

	}

	public void loadLevel() {
		this.blockTextureAtlas = (TextureAtlas) cg.getTextureManager().getTexture("blocks");

		// 创建新的世界
		world = new World(this);
		// 安置新的玩家
		player = new Player(8, 10, 8, 0, 135, 0, world);

	}

	public void closeLevel() {
		// 非常草率的关闭世界
		world.close();
		world = null;
		player = null;

	}

	/**
	 * 处理鼠标的移动
	 *
	 * @param dx 鼠标移动x坐标的位移
	 * @param dy 鼠标移动y标的位移
	 */
	public void mouseMove(double dx, double dy) {
		if (cg.getDisplay().mouseGrabbed && player != null) {
			player.pos.rotX -= dy * player.sensivity;
			player.pos.rotY += dx * player.sensivity;
		}
	}

	/**
	 * 这个是世界加载的时候会进行的工作
	 */
	@Override
	public void run() {
		GUIProgressBar world_loading_progressBar = (GUIProgressBar) (cg.getGuiResource()
				.getGui("world_loading.json").widgets.get("loading_bar"));

		loadLevel();

		world_loading_progressBar.setProgress(1.0f);

		cg.queueRunningInMainThread.add(this::size);

		world_loading_progressBar.waitUtilProgressFull();

		this.state = State.INGAME;

		cg.queueRunningInMainThread.add(() -> {
			cg.getViewManager().setGui((View) null);
			cg.getDisplay().toggleMouseGrabbed();

		});

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 转换画面到世界内部
		cg.inWorld = true;

	}

	public void size() {
		Shader.setProjectionMatrix(cg, blockRenderShader);

		if (gameGui != null)
			gameGui.size();

	}

	public Player getClientPlayer() {
		return player;
	}

	public Shader getBlockRenderShader() {
		return blockRenderShader;
	}

	public HashMap<String, Integer> getNvgTextures() {
		return nvgTextures;
	}

	public void toggleGuiExit() {
		cg.getDisplay().toggleMouseGrabbed();
		this.state = State.INGAME;
		this.ingameView = null;
		this.ingameView = new HUDView(this);

	}

	public void toggleSetIngameGui(InGameView view) {
		cg.getDisplay().toggleMouseGrabbed();
		this.state = State.INVENTORY;
		this.ingameView = view;

	}

	public void setNormalViewPort() {
		glViewport(0, 0, cg.getDisplay().getWidth(), cg.getDisplay().getHeight());
		
	}
	
	public void draw() {
		// Blocks.init(nvg, cg, (TextureAtlas)
		// cg.getTextureManager().getTexture("blocks"));

		// 由于这是单纯的本地客户端 所以会有本地的玩家的按键处理
		// 当做成可以多人游戏的版本的时候 这里就似乎得改一改了
		player.tick();
		world.tick(player);
		// 生成玩家视角的视角矩阵
		playerMatrix = MatrixHelper.player(player.pos);
		MatrixHelper.calculateFrustumPlane();

		// 玩家指针指向方块的更新
		player.pickTick();

		setNormalViewPort();

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		{
			// 首先绘制天空 ta担任了清空颜色的重要使命

			// 透明材质
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);

			world.drawSky();

			GL11.glDisable(GL11.GL_BLEND);

		}

		{
			GL11.glEnable(GL11.GL_CULL_FACE);

			normalRenderer.initDraw();
			// 将内存地址映射 效率提高了
			mappedBuffer = normalRenderer.mapBuffer();
			// 得到世界的渲染顶点信息
			vertexCount = world.draw(blockTextureAtlas, player, mappedBuffer.asFloatBuffer(), drawDistance);
			// 解除内存地址的映射 OpenGL娘就不会担心在处理时内存数据突然改变而引起的束手无策 （这个API也不允许这样干）
			normalRenderer.unmap();

			GLHelper.checkGLError("World: Map Buffer");

			// 将方块材质加入到OpenGL娘的首个材质槽里面
			blockTextureAtlas.bind();
			
			// 阴影映射加入到第二个材质槽里面
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, shadowMapper.getBuffer().getTbo_depth());
			
			GLHelper.checkGLError("World: Bind Texture");

			// 将着色器放到OpenGL娘的着色器槽里面
			blockRenderShader.use();

			// 设置着色器娘的视角姬参数
			blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("viewMatrix"), playerMatrix);

			GLHelper.checkGLError("World: Bind Shader");

			// 让OpenGL娘去绘制叭~
			normalRenderer.draw(vertexCount);
			
			normalRenderer.postDraw();

			GLHelper.checkGLError("World: Drawer");

			blockRenderShader.unbind();

			GL11.glDisable(GL11.GL_CULL_FACE);
			
		}
		
		shadowMapper.bindBuffer();
		shadowMapper.clearBuffer();
		shadowMapper.renderToDepthBuffer(normalRenderer);
		shadowMapper.unbindBuffer();
		
		setNormalViewPort();

		{
			// 透明材质
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);

			// TODO: 透明物体“两面派”

			normalRenderer.initDraw();
			
			mappedBuffer = normalRenderer.mapBuffer();
			vertexCount = world.drawAlpha(blockTextureAtlas, player, mappedBuffer.asFloatBuffer(), drawDistance);
			normalRenderer.unmap();

			GLHelper.checkGLError("World Alpha: Map Buffer");

			blockTextureAtlas.bind();
			GLHelper.checkGLError("World Alpha: Bind Texture");

			blockRenderShader.use();

			GLHelper.checkGLError("World Alpha: Bind Shader");

			// 让OpenGL娘去绘制叭~
			normalRenderer.draw(vertexCount);

			GLHelper.checkGLError("World Alpha: Drawer");

			blockRenderShader.unbind();
			
			normalRenderer.postDraw();

			// 透明材质
			GL11.glDisable(GL11.GL_BLEND);

		}

		// 接触绑定 (束缚 真)
		Texture.unbind();
		blockRenderShader.unbind();

		normalRenderer.postDraw();

		// 去除背面渲染
		GL11.glDisable(GL11.GL_CULL_FACE);

		// shadowMapper.renderToDepthBuffer(normalRenderer);

		setNormalViewPort();

		if (gameGui != null) {
			cg.getViewManager().draw();
		}

		GLHelper.checkGLError("World: After-render");

		if (ingameView != null) {
			nvgBeginFrame(nvg, Display.currentDisplay.getWidth(), Display.currentDisplay.getHeight(),
					Display.currentDisplay.getRatio());

			ingameView.draw(nvg);

			nvgEndFrame(nvg);

		}

	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void onMouseScroll(float scroll) {
		if (ingameView != null)
			ingameView.onScroll(cg.getDisplay().getMouseX(), cg.getDisplay().getMouseY(), scroll);

	}

	public void onMouseClick(int button) {
		if (ingameView != null)
			ingameView.onClick(cg.getDisplay().getMouseX(), cg.getDisplay().getMouseY(), button);

	}

	public void onKeyboard(int key) {
		// 检查这个按键是否被按下 使用keysOnce是因为只有在游戏在循环过程中第一次发现这个按键被按下时才会让对应的按键的布尔值变为true
		// Nobody wanna see their mouse guichu~
		if (KeyCallback.keysOnce[GLFW.GLFW_KEY_ESCAPE]) {
			if (this.state == State.INGAME) {
				cg.getDisplay().toggleMouseGrabbed();
				gameGui = cg.getGuiResource().getGui("game_esc_menu.json");
				cg.getViewManager().setGui(gameGui);
				this.state = State.ESC_MENU;

			} else if (this.state == State.ESC_MENU) {
				// 从esc界面回到游戏中
				cg.getDisplay().toggleMouseGrabbed();
				cg.getViewManager().setGui((View) null);
				gameGui = null;
				this.state = State.INGAME;

			} else {
				toggleGuiExit();

			}

		}

		if (KeyCallback.keysOnce[GLFW.GLFW_KEY_E]) {
			if (this.state == State.INGAME) {
				cg.getDisplay().toggleMouseGrabbed();
				ingameView = new InventoryView(this, player);
				this.state = State.INVENTORY;
			} else if (this.state == State.INVENTORY) {
				// 从esc界面回到游戏中
				cg.getDisplay().toggleMouseGrabbed();
				this.state = State.INGAME;
			}

		}

		if (this.state == State.INGAME) {
			ingameView = new HUDView(this);

		}

		if (this.state == State.ESC_MENU)
			ingameView = null;

	}

	public CraftGame getCg() {
		return cg;
	}

	public World getWorld() {
		return world;
	}

	private volatile boolean isClosing = false;

	public void delete() {
		try {
			isClosing = true;
			normalRenderer.delete();
			closeLevel();
		} catch (ConcurrentModificationException e) {
		}

	}

}
