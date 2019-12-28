package xueLi.craftGame;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.entity.Player;
import xueLi.craftGame.shader.WorldShader;
import xueLi.craftGame.utils.Vector;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.FPSTimer;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.MousePicker;
import xueLi.craftGame.utils.VertexBuffer;
import xueLi.craftGame.world.Chunk;

public class Main {

	private static int width = 800, height = 600;

	private static WorldShader shader;

	private static Player player = new Player(8, 6, 8);
	private static float resistant = 0.000005f;
	private static float sensivity = 0.1f;
	
	private static BlockPos block_select,last_block_select;
	private static long placeTimeCount;
	
	public static void main(String[] args) throws IOException {
		DisplayManager.create(width, height);
		shader = new WorldShader();

		int textureID = GLHelper.bindTexture("res/textures.png");

		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(16777216);
		FloatBuffer texBuffer = BufferUtils.createFloatBuffer(16777216);
		FloatBuffer selectBlockFrameBuffer = BufferUtils.createFloatBuffer(72);

		Chunk chunk = new Chunk();

		Vector3f playerSpeed = new Vector3f(0, 0, 0);
		Mouse.setGrabbed(true);
		while (DisplayManager.isRunning()) {
			GLHelper.clearColor(0.5f, 0.8f, 1.0f, 1.0f);
			
			int vertCount = chunk.draw(vertexBuffer, texBuffer, player.pos);

			vertexBuffer.flip();
			selectBlockFrameBuffer.flip();
			texBuffer.flip();

			shader.use();
			if (DisplayManager.tickResize())
				shader.setProjMatrix(DisplayManager.d_width, DisplayManager.d_height, 70.0f);
			shader.setViewMatrix(player);

			int error = GL11.glGetError();
			if (error != 0) {
				System.out.println(error);
			}
			
			VertexBuffer.send(vertexBuffer, texBuffer, vertCount);
			VertexBuffer.bind();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
			VertexBuffer.draw(GL11.GL_TRIANGLES);
			VertexBuffer.unbind();
			VertexBuffer.clear();
			
			if(block_select != null) {
				VertexBuffer.send(selectBlockFrameBuffer, 24);
				VertexBuffer.bind();
				VertexBuffer.draw(GL11.GL_LINES);
				VertexBuffer.unbind();
			}
			
			shader.unbind();

			selectBlockFrameBuffer.clear();
			vertexBuffer.clear();
			texBuffer.clear();
			VertexBuffer.clear();

			DisplayManager.update();
			
			block_select = null;
			MousePicker.ray(player.pos);
			for(float distance = 0;distance < 8;distance += 0.05f) {
				BlockPos searching_block_pos = MousePicker.getPointOnRay(distance);
				if(chunk.hasBlock(searching_block_pos)) {
					block_select = searching_block_pos;
					chunk.getBlock(block_select).method.getBlockFrame(selectBlockFrameBuffer, block_select);
					break;
				}
				last_block_select = searching_block_pos;
			}
			
			if(DisplayManager.isMouseDown(0) & block_select != null & DisplayManager.currentTime - placeTimeCount > 200) {
				chunk.setBlock(block_select, 0);
				placeTimeCount = DisplayManager.currentTime;
			}
			
			if(DisplayManager.isMouseDown(1) & block_select != null & DisplayManager.currentTime - placeTimeCount > 200) {
				chunk.setBlock(last_block_select, 1);
				placeTimeCount = DisplayManager.currentTime;
			}

			if (DisplayManager.isKeyDown(Keyboard.KEY_ESCAPE)) {
				DisplayManager.postDestroyMessage();
			}

			boolean isKeyMovingLRFBPressed = false, isKeyMovingUDPressed = false;

			Vector playerPos = player.pos;
			if (DisplayManager.isKeyDown(Keyboard.KEY_S)) {
				playerSpeed.x = player.getSpeed() * (float) Math.sin(Math.toRadians(-playerPos.rotY));
				playerSpeed.z = player.getSpeed() * (float) Math.cos(Math.toRadians(-playerPos.rotY));
				player.increasePosition(playerSpeed.x * DisplayManager.deltaTime,
						0,
						playerSpeed.z * DisplayManager.deltaTime,
						0, 0, 0);
				isKeyMovingLRFBPressed = true;
			}
			if (DisplayManager.isKeyDown(Keyboard.KEY_W)) {
				playerSpeed.x = -player.getSpeed() * (float) Math.sin(Math.toRadians(-playerPos.rotY));
				playerSpeed.z = -player.getSpeed() * (float) Math.cos(Math.toRadians(-playerPos.rotY));
				player.increasePosition(playerSpeed.x * DisplayManager.deltaTime,
						0,
						playerSpeed.z * DisplayManager.deltaTime,
						0, 0, 0);
				isKeyMovingLRFBPressed = true;
			}
			if (DisplayManager.isKeyDown(Keyboard.KEY_A)) {
				playerSpeed.x = -player.getSpeed() * (float) Math.sin(Math.toRadians(-playerPos.rotY + 90));
				playerSpeed.z = -player.getSpeed() * (float) Math.cos(Math.toRadians(-playerPos.rotY + 90));
				player.increasePosition(playerSpeed.x * DisplayManager.deltaTime,
						0,
						playerSpeed.z * DisplayManager.deltaTime,
						0, 0, 0);
				isKeyMovingLRFBPressed = true;
			}
			if (DisplayManager.isKeyDown(Keyboard.KEY_D)) {
				playerSpeed.x = player.getSpeed() * (float) Math.sin(Math.toRadians(-playerPos.rotY + 90));
				playerSpeed.z = player.getSpeed() * (float) Math.cos(Math.toRadians(-playerPos.rotY + 90));
				player.increasePosition(playerSpeed.x * DisplayManager.deltaTime,
						0,
						playerSpeed.z * DisplayManager.deltaTime,
						0, 0, 0);
				isKeyMovingLRFBPressed = true;
			}

			if (DisplayManager.isKeyDown(Keyboard.KEY_SPACE)) {
				playerSpeed.y = player.getSpeed();
				player.increasePosition(0,
						playerSpeed.y * DisplayManager.deltaTime,
						0,
						0, 0, 0);
				isKeyMovingUDPressed = true;
			}

			if (DisplayManager.isKeyDown(Keyboard.KEY_LSHIFT)) {
				playerSpeed.y = -player.getSpeed();
				player.increasePosition(0,
						playerSpeed.y * DisplayManager.deltaTime,
						0,
						0, 0, 0);
				isKeyMovingUDPressed = true;
			}

			if (!isKeyMovingLRFBPressed) {
				player.increasePosition(playerSpeed.x * DisplayManager.deltaTime,
						0,
						playerSpeed.z * DisplayManager.deltaTime,
						0, 0, 0);
			}
			if (!isKeyMovingUDPressed) {
				player.increasePosition(0,
						playerSpeed.y * DisplayManager.deltaTime,
						0,
						0, 0, 0);
			}

			player.increasePosition(0, 0, 0, -Mouse.getDY() * sensivity, Mouse.getDX() * sensivity, 0);

			if (playerSpeed.x > 0) {
				playerSpeed.x -= resistant * playerSpeed.x * 1000 * DisplayManager.deltaTime;
				if (playerSpeed.x < 0)
					playerSpeed.x = 0;
			} else if (playerSpeed.x < 0) {
				playerSpeed.x -= resistant * playerSpeed.x * 1000 * DisplayManager.deltaTime;
				if (playerSpeed.x > 0)
					playerSpeed.x = 0;
			}

			if (playerSpeed.y > 0) {
				playerSpeed.y -= resistant * playerSpeed.y * 2000 * DisplayManager.deltaTime;
				if (playerSpeed.y < 0)
					playerSpeed.y = 0;
			} else if (playerSpeed.y < 0) {
				playerSpeed.y -= resistant * playerSpeed.y * 2000 * DisplayManager.deltaTime;
				if (playerSpeed.y > 0)
					playerSpeed.y = 0;
			}

			if (playerSpeed.z > 0) {
				playerSpeed.z -= resistant * playerSpeed.z * 1000 * DisplayManager.deltaTime;
				if (playerSpeed.z < 0)
					playerSpeed.z = 0;
			} else if (playerSpeed.z < 0) {
				playerSpeed.z -= resistant * playerSpeed.z * 1000 * DisplayManager.deltaTime;
				if (playerSpeed.z > 0)
					playerSpeed.z = 0;
			}

			int fps = FPSTimer.getFPS();
		}

		vertexBuffer.clear();
		texBuffer.clear();
		GLHelper.deleteTexture(textureID);
		shader.release();

		DisplayManager.destroy();

	}
}
