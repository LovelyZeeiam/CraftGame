package xueLi.craftGame;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.world.Chunk;
import xueLi.craftGame.world.World;
import xueLi.craftGame.world.WorldGLData;
import xueLi.gamengine.resource.TextureAtlas;
import xueLi.gamengine.utils.GLHelper;
import xueLi.gamengine.utils.MatrixHelper;
import xueLi.gamengine.utils.Shader;
import xueLi.gamengine.utils.callbacks.KeyCallback;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.View;

public class WorldLogic implements Runnable {

	private CraftGame cg;
	public boolean running = false;

	private int vao, vbo;
	private ByteBuffer mappedBuffer;
	private int vertexCount = 0;

	private View gameGui;
	private State state;

	@WorldGLData
	public WorldLogic(CraftGame cg) {
		this.cg = cg;

		/*
		 * 注册vao, vbo
		 */
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 671088640, GL15.GL_DYNAMIC_DRAW);

		// UV
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * 4, 0);
		GL20.glEnableVertexAttribArray(1);
		// 颜色
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * 4, 2 * 4);
		GL20.glEnableVertexAttribArray(2);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * 4, 5 * 4);
		GL20.glEnableVertexAttribArray(0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

	}

	private World world;
	private Player player;

	private TextureAtlas blockTextureAtlas;
	private Shader blockRenderShader;

	public void loadLevel() {
		this.blockTextureAtlas = (TextureAtlas) cg.getTextureManager().getTexture("blocks");
		this.blockRenderShader = cg.getShaderResource().get("world");

		world = new World(this);
		player = new Player(8, 8, 8, 0, 135, 0);

	}

	public void closeLevel() {

	}

	public void mouseMove(double dx, double dy) {
		if (cg.getDisplay().mouseGrabbed) {
			player.pos.rotX -= dy * player.sensivity;
			player.pos.rotY += dx * player.sensivity;
		}
	}

	/**
	 * frustum clulling 依照从前到后的顺序排序几何体 顶点处理器基于32位浮点值工作 Fragment Shader使用16位浮点值工作
	 * 
	 */

	public volatile ArrayList<Chunk> chunkThatNeedBeDrew = new ArrayList<Chunk>();

	private Matrix4f playerMatrix = new Matrix4f();

	@Override
	public void run() {
		GUIProgressBar world_loading_progressBar = (GUIProgressBar) (cg.getGuiResource()
				.getGui("world_loading.json").widgets.get("loading_bar"));

		world.preDraw(blockTextureAtlas, player, 10);

		world_loading_progressBar.setProgress(1.0f);

		cg.queueRunningInMainThread.add(() -> size());

		world.updateVertexBuffer(blockTextureAtlas, player, 10);

		world_loading_progressBar.waitUtilProgressFull();

		cg.queueRunningInMainThread.add(() -> {
			cg.getViewManager().setGui((View) null);
			cg.getDisplay().toggleMouseGrabbed();

		});
		
		this.state = State.INGAME;
		cg.inWorld = true;

	}

	public void size() {
		blockRenderShader.use();
		blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("projMatrix"), MatrixHelper
				.perspecive(cg.getDisplay().getWidth(), cg.getDisplay().getHeight(), 90.0f, 0.01f, 114514.0f));
		blockRenderShader.unbind();
	}

	public void draw() {

		if (KeyCallback.keysOnce[GLFW.GLFW_KEY_ESCAPE]) {
			if (this.state == State.INGAME) {
				cg.getDisplay().toggleMouseGrabbed();
				
				this.state = State.ESC_MENU;
				
			} else if (this.state == State.ESC_MENU) {
				cg.getDisplay().toggleMouseGrabbed();
				
				this.state = State.INGAME;

			}

		}

		player.tick(world);
		playerMatrix = MatrixHelper.player(player.pos);
		MatrixHelper.calculateFrustumPlane();
		player.pickTick(world);

		GL11.glClearColor(0.7f, 0.8f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

		GLHelper.checkGLError("World: Pre-render");

		mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY, mappedBuffer);
		mappedBuffer.clear();
		mappedBuffer.position(0);

		vertexCount = world.draw(blockTextureAtlas, player, mappedBuffer.asFloatBuffer(), 10);

		mappedBuffer.flip();
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);

		GLHelper.checkGLError("World: Map Buffer");

		blockTextureAtlas.bind();
		GLHelper.checkGLError("World: Bind Texture");

		blockRenderShader.use();

		blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("viewMatrix"), playerMatrix);

		GLHelper.checkGLError("World: Bind Shader");

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

		GLHelper.checkGLError("World: Drawer");

		blockTextureAtlas.unbind();
		blockRenderShader.unbind();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GLHelper.checkGLError("World: After-render");

	}

	public void delete() {
		GL30.glDeleteVertexArrays(vao);
		GL15.glDeleteBuffers(vbo);

	}

}
