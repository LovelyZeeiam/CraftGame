package xueLi.gamengine.view;

import org.lwjgl.opengl.GL11;
import xueLi.craftGame.TileBuilderThread;
import xueLi.craftGame.world.World;

public class WorldRenderer extends View {

	public TileBuilderThread tileBuilderThread;

	public WorldRenderer() {
		super("World Renderer");

		tileBuilderThread = new TileBuilderThread();

	}

	public World world;

	/**
	 * 此时会打开mesh构建线程 本线程即世界更新线程会进行游戏更新并将玩家看得见的区块发送给mesh,
	 * mesh构建线程会访问到世界更新线程发过来的区块然后构建成mesh发给主线程，主线程负责将mesh发给OpenGL
	 * 
	 */
	public void loadLevel() {

	}

	public void closeLevel() {

	}

	@Override
	public void create() {

	}

	@Override
	public void draw(long nvg) {
		GL11.glClearColor(0.7f, 0.8f, 1.0f, 1.0f);

		super.draw(nvg);
	}

}
