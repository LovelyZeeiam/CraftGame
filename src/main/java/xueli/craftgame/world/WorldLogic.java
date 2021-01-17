package xueli.craftgame.world;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFont;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
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

	private int drawDistance = 5;

	/**
	 * frustum culling 依照从前到后的顺序排序几何体 顶点处理器基于32位浮点值工作 Fragment Shader使用16位浮点值工作
	 */

	private Matrix4f playerMatrix = new Matrix4f();

	private long nvg;

	private InGameView ingameView;

	private Renderer normalRenderer;
	private ShadowMapper shadowMapper;

	@WorldGLData
	public WorldLogic(CraftGame cg) {
		this.cg = cg;

		normalRenderer = new Renderer(cg, this);
		shadowMapper = new ShadowMapper(cg, this);

		nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (nvg == 0) {
			Logger.error(new Throwable("[GUI] Emm, You don't want a game without gui, do u?"));
		}

		if (nvgCreateFont(nvg, "game", "res/fonts/Minecraft-Ascii.ttf") == -1) {
			Logger.error("[Font] Can't create font!");
		}

		Blocks.init(nvg, cg, (TextureAtlas) cg.getTextureManager().getTexture("blocks"));

		ingameView = new HUDView(this, cg);

	}

	public void loadLevel() {
		this.blockTextureAtlas = (TextureAtlas) cg.getTextureManager().getTexture("blocks");
		this.blockRenderShader = cg.getShaderResource().get("world");

		// 创建新的世界
		world = new World(this);
		// 安置新的玩家
		player = new Player(8, 150, 8, 0, 135, 0, world);

	}

	public void closeLevel() {
		world.close();

		// 非常草率的关闭世界
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
		if (cg.getDisplay().mouseGrabbed) {
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

		// 提前先生成好世界的顶点数据 防止第一次绘制的时候 CPU姬突然接收到那么多数据 由于无法按时完成生成任务而无奈的让显卡姬等一等
		// 似乎在Windows系统上面没什么用 ubuntu还好
		world.updateVertexBuffer(blockTextureAtlas, player, drawDistance);

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

	public void toggleGuiExit() {
		cg.getDisplay().toggleMouseGrabbed();
		this.state = State.INGAME;
		this.ingameView = null;
		this.ingameView = new HUDView(this, cg);

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

		glViewport(0, 0, cg.getDisplay().getWidth(), cg.getDisplay().getHeight());

		// 清空颜色
		GL11.glClearColor(0.7f, 0.8f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);

		// 透明材质
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glEnable(GL11.GL_BLEND);

		normalRenderer.initDraw();

		{
			// 将内存地址映射 效率提高了
			mappedBuffer = normalRenderer.mapBuffer();
			// 得到世界的渲染顶点信息
			vertexCount = world.draw(blockTextureAtlas, player, mappedBuffer.asFloatBuffer(), drawDistance);
			// 解除内存地址的映射 OpenGL娘就不会担心在处理时内存数据突然改变而引起的束手无策 （这个API也不允许这样干）
			normalRenderer.unmap();

			GLHelper.checkGLError("World: Map Buffer");

			// 将方块材质加入到OpenGL娘的首个材质槽里面
			blockTextureAtlas.bind();
			GLHelper.checkGLError("World: Bind Texture");

			// 将着色器放到OpenGL娘的着色器槽里面
			blockRenderShader.use();

			// 设置着色器娘的视角姬参数
			blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("viewMatrix"), playerMatrix);

			GLHelper.checkGLError("World: Bind Shader");

			// 让OpenGL娘去绘制叭~
			normalRenderer.draw(vertexCount);

			GLHelper.checkGLError("World: Drawer");

			blockRenderShader.unbind();

		}

		// 接触绑定 (束缚 真)
		blockTextureAtlas.unbind();
		blockRenderShader.unbind();

		normalRenderer.postDraw();

		// 去除背面渲染
		GL11.glDisable(GL11.GL_CULL_FACE);

		// shadowMapper.renderToDepthBuffer(normalRenderer);

		glViewport(0, 0, cg.getDisplay().getWidth(), cg.getDisplay().getHeight());

		GL11.glDisable(GL11.GL_DEPTH_TEST);

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
				ingameView = new InventoryView(this, cg, player);
				this.state = State.INVENTORY;
			} else if (this.state == State.INVENTORY) {
				// 从esc界面回到游戏中
				cg.getDisplay().toggleMouseGrabbed();
				this.state = State.INGAME;
			}

		}

		if (this.state == State.INGAME) {
			ingameView = new HUDView(this, cg);

		}

		if (this.state == State.ESC_MENU)
			ingameView = null;

	}

	public World getWorld() {
		return world;
	}

	public void delete() {
		normalRenderer.delete();
		closeLevel();

	}

}
