package xueLi.craftGame;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.entity.Entity;
import xueLi.craftGame.entity.Player;
import xueLi.craftGame.entity.renderer.EntityRenderer;
import xueLi.craftGame.utils.Vector;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.FPSTimer;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.MousePicker;
import xueLi.craftGame.utils.VertexBuffer;
import xueLi.craftGame.world.World;

public class Main {

	private static int width = 1200, height = 680;

	private static Player player = new Player(8, 8, 8);
	private static float resistant = 0.005f;
	private static float sensivity = 0.1f;

	public static void main(String[] args) throws IOException {
		DisplayManager.create(width, height);

		int textureID = GLHelper.registerTexture("res/textures.png");

		FloatBuffer buffer;

		World w = new World(10, 10);
		Entity.init();
		EntityRenderer.bindWorld(w);
		
		VertexBuffer.init();

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
			VertexBuffer.draw(GL11.GL_TRIANGLES, v);

			buffer.clear();

			int error = GL11.glGetError();
			if (error != 0) {
				System.out.println(error);
			}

			if (DisplayManager.tickResize()) {
				GLHelper.perspecive(DisplayManager.d_width, DisplayManager.d_height, 90.0f, 0.1f, 1000.0f);
			}
			GLHelper.player(player);
			GLHelper.calculateFrustumPlane();
			
			player.pickTick(w);

			buffer.clear();

			DisplayManager.update();

		}

		GLHelper.deleteTexture(textureID);

		DisplayManager.destroy();

	}
}
