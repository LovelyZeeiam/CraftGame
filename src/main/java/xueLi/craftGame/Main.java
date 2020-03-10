package xueLi.craftGame;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;

import xueLi.craftGame.entity.EntityWarma;
import xueLi.craftGame.entity.Player;
import xueLi.craftGame.entity.renderer.EntityRenderer;
import xueLi.craftGame.utils.BilibiliAPI;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.FPSTimer;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.VertexBuffer;
import xueLi.craftGame.world.World;

public class Main {

	private static int width = 1200, height = 680;

	private static Player player = new Player(16, 7, 18, 0, 0, 0);

	public static void main(String[] args) throws IOException {
		BilibiliAPI.startThreadOfRealtimeGetFans();
		
		DisplayManager.create(width, height);

		int textureID = GLHelper.registerTexture("res/textures.png");

		FloatBuffer buffer;

		World w = new World(10, 10);
		w.addEntity(new EntityWarma(16, 7, 16));

		VertexBuffer.init();
		EntityRenderer.init();

		Mouse.setGrabbed(true);
		while (DisplayManager.isRunning()) {
			if (DisplayManager.isKeyDown(Keyboard.KEY_ESCAPE)) {
				DisplayManager.postDestroyMessage();
			}

			player.tick(w);

			FPSTimer.getFPS();

			GLHelper.clearColor(0.5f, 0.8f, 1.0f, 1.0f);

			buffer = VertexBuffer.map();
			int v = w.draw(player, buffer);

			buffer.flip();

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
			GL11.glColor3f(1, 1, 1);
			VertexBuffer.draw(GL11.GL_TRIANGLES, v);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

			buffer.clear();

			EntityRenderer.render();
			
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

			DisplayManager.update();
		}

		GLHelper.deleteTexture(textureID);

		BilibiliAPI.stopThreadOfRealtimeGetFans();
		
		DisplayManager.destroy();

	}
}
