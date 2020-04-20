package xueLi.craftGame.world;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.utils.GLHelper;

public class WorldRenderer {

	private static int texture;
	private static FloatBuffer buffer;

	private static World w = new World(2, 2);
	private static Vector3f skyColor = new Vector3f(0.5f, 0.8f, 1.0f);

	private static Player player = new Player(16, 8, 16, 0, 0, 0);

	public static void init() {
		texture = GLHelper.registerTexture("res/textures.png");
		WorldVertexBinder.init();

		// 上面那两个函数是跟OpenGL有关的
		w.generate();

	}

	public static void render() {
		GL11.glClearColor(skyColor.x,skyColor.y,skyColor.z, 1.0f);

		player.tick(w);
		
		buffer = WorldVertexBinder.map();
		buffer.position(0);
		int v = w.draw(player, buffer);
		buffer.flip();
		WorldVertexBinder.unmap();

		WorldVertexBinder.useShader();
		WorldVertexBinder.processPlayer(player);
		WorldVertexBinder.setSkyColor(skyColor);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		WorldVertexBinder.draw(v);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		WorldVertexBinder.unbindShader();
		
		buffer.clear();

		// EntityRenderer.render();

		int error = GL11.glGetError();
		if (error != 0) {
			System.out.println("[World Renderer] " + GLU.gluErrorString(error));
		}

		player.pickTick(w);

	}

	public static void release() {
		GLHelper.deleteTexture(texture);
		WorldVertexBinder.delete();

	}

}
