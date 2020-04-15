package xueLi.craftGame.world;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.GLHelper;

public class WorldRenderer {
	
	private static int texture;
	private static FloatBuffer buffer;
	
	private static World w = new World(8,8);
	
	private static Player player = new Player(8, 8, 8, 0, 0, 0);
	
	public static void init() {
		texture = GLHelper.registerTexture("res/textures.png");
		WorldVertexBinder.init();
		
		//上面那两个函数是跟OpenGL有关的
		w.generate();
		
	}
	
	public static void render() {
		GL11.glClearColor(0.5f, 0.8f, 1.0f, 1.0f);
		
		player.tick(w);
		buffer = WorldVertexBinder.map();
		int v = w.draw(player, buffer);

		buffer.flip();

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		//GL11.glColor3f(1, 1, 1);
		WorldVertexBinder.draw(GL11.GL_TRIANGLES, v);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		buffer.clear();

		//EntityRenderer.render();
		
		int error = GL11.glGetError();
		if (error != 0) {
			System.out.println(GLU.gluErrorString(error));
		}

		if (DisplayManager.tickResize()) {
			GLHelper.perspecive(DisplayManager.d_width, DisplayManager.d_height, 90.0f, 0.1f, 1000.0f);
		}
		GLHelper.player(player);
		GLHelper.calculateFrustumPlane();

		player.pickTick(w);
	}
	
	public static void release() {
		GLHelper.deleteTexture(texture);
		
	}

}
