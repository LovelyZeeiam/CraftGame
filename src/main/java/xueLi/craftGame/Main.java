package xueLi.craftGame;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.entity.renderer.EntityRenderer;
import xueLi.craftGame.utils.BilibiliAPI;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.FPSTimer;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.world.ChunkGenerator;
import xueLi.craftGame.world.World;
import xueLi.craftGame.world.WorldRenderer;
import xueLi.craftGame.world.WorldVertexBinder;

public class Main {

	private static int width = 1200, height = 680;

	public static void main(String[] args) throws IOException {
		BilibiliAPI.startThreadOfRealtimeGetFans();
		
		DisplayManager.create(width, height);
		WorldRenderer.init();

		Mouse.setGrabbed(true);
		while (DisplayManager.isRunning()) {
			if (DisplayManager.isKeyDown(Keyboard.KEY_ESCAPE)) {
				DisplayManager.postDestroyMessage();
			}

			FPSTimer.getFPS();

			GLHelper.clearColor(0.5f, 0.8f, 1.0f, 1.0f);
			
			WorldRenderer.render();
			
			DisplayManager.update();
		}

		BilibiliAPI.stopThreadOfRealtimeGetFans();
		
		WorldRenderer.release();
		DisplayManager.destroy();

	}
}
