package xueLi.craftGame;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.world.Chunk;
import xueLi.craftGame.world.World;
import xueLi.craftGame.world.WorldGLData;
import xueLi.gamengine.resource.TextureAtlas;
import xueLi.gamengine.utils.GLHelper;
import xueLi.gamengine.utils.MatrixHelper;
import xueLi.gamengine.utils.Shader;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.View;

public class WorldLogic implements Runnable {

	private CraftGame cg;
	public boolean running = false;

	private int vao, vbo;
	private ByteBuffer mappedBuffer;
	private int vertexCount = 0;

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
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 67108864, GL15.GL_DYNAMIC_DRAW);

		GL20.glVertexAttribPointer(0, 3, GL11.GL_INT, false, 29, 0);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(1, 1, GL11.GL_BYTE, false, 29, 3 * 4);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(2, 2, GL11.GL_SHORT, false, 29, 3 * 4 + 1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, 29, 4 * 4 + 1);
		GL20.glEnableVertexAttribArray(3);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

	}

	private World world;
	private Player player;

	private TextureAtlas blockTextureAtlas;
	private Shader blockRenderShader;

	public void loadLevel() {
		this.blockTextureAtlas = (TextureAtlas) cg.getTextureManager().getTexture("blocks");
		this.blockRenderShader = cg.getShaderResource().get("world_blocks");

		world = new World();
		player = new Player(8, 8, 8);

	}

	public void closeLevel() {

	}

	public void mouseMove(double dx, double dy) {

	}

	/**
	 * frustum clulling 依照从前到后的顺序排序几何体 顶点处理器基于32位浮点值工作 Fragment Shader使用16位浮点值工作
	 * 
	 */

	public volatile ArrayList<Chunk> chunkThatNeedBeDrew = new ArrayList<Chunk>();

	@Override
	public void run() {
		GUIProgressBar world_loading_progressBar = (GUIProgressBar) (cg.getGuiResource()
				.getGui("world_loading.json").widgets.get("loading_bar"));

		world_loading_progressBar.setProgress(1.0f);

		world_loading_progressBar.waitUtilProgressFull();

		cg.queueRunningInMainThread.add(() -> {
			size();

			cg.getViewManager().setGui((View) null);
		});

		cg.inWorld = true;

		// TODO: Player类里面玩家的刷新已经写好啦~

	}

	public void size() {
		blockRenderShader.use();
		blockRenderShader.setUniformMatrix(blockRenderShader.getUnifromLocation("projMatrix"), MatrixHelper
				.perspecive(cg.getDisplay().getWidth(), cg.getDisplay().getHeight(), 80.0f, 0.01f, 114514.0f));
		blockRenderShader.unbind();
	}

	public void draw() {
		GL11.glClearColor(0.7f, 0.8f, 1.0f, 1.0f);

		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

		GLHelper.checkGLError("World: Pre-render");

		mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_READ_WRITE, 67108864, mappedBuffer);
		mappedBuffer.clear();

		for (Chunk chunk : chunkThatNeedBeDrew) {

		}

		mappedBuffer.putInt(0).putInt(0).putInt(-5);
		mappedBuffer.put((byte) 0);
		mappedBuffer.putShort((short) 1).putShort((short) 1);
		mappedBuffer.putFloat(1.0f).putFloat(1.0f).putFloat(1.0f);

		mappedBuffer.putInt(-1).putInt(0).putInt(-5);
		mappedBuffer.put((byte) 0);
		mappedBuffer.putShort((short) 0).putShort((short) 1);
		mappedBuffer.putFloat(1.0f).putFloat(1.0f).putFloat(1.0f);

		mappedBuffer.putInt(-1).putInt(-1).putInt(-5);
		mappedBuffer.put((byte) 0);
		mappedBuffer.putShort((short) 0).putShort((short) 0);
		mappedBuffer.putFloat(1.0f).putFloat(1.0f).putFloat(1.0f);

		mappedBuffer.putInt(0).putInt(-1).putInt(-5);
		mappedBuffer.put((byte) 0);
		mappedBuffer.putShort((short) 1).putShort((short) 0);
		mappedBuffer.putFloat(1.0f).putFloat(1.0f).putFloat(1.0f);

		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);

		GLHelper.checkGLError("World: Map Buffer");

		blockTextureAtlas.bind();

		GLHelper.checkGLError("World: Bind Texture");

		blockRenderShader.use();

		blockRenderShader.setInt(blockRenderShader.getUnifromLocation("tex_atlas_width"), blockTextureAtlas.width);
		blockRenderShader.setInt(blockRenderShader.getUnifromLocation("tex_atlas_height"), blockTextureAtlas.height);

		GLHelper.checkGLError("World: Bind Shader");

		GL11.glDrawArrays(GL11.GL_POINTS, 0, 4);

		GLHelper.checkGLError("World: Drawer");

		blockTextureAtlas.unbind();
		blockRenderShader.unbind();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		GLHelper.checkGLError("World: After-render");

	}

	public void delete() {
		GL30.glDeleteVertexArrays(vao);
		GL15.glDeleteBuffers(vbo);

	}

}
