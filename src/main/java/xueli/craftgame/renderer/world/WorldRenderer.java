package xueli.craftgame.renderer.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import xueli.game.Game;
import xueli.game.renderer.FrameBuffer;
import xueli.game.renderer.ScreenQuadRenderer;
import xueli.game.utils.GLHelper;
import xueli.game.utils.Shader;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;
import xueli.game.vector.Vector;
import xueli.craftgame.entity.Player;
import xueli.craftgame.renderer.VertexPointer;
import xueli.craftgame.renderer.sky.SkyRenderer;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.Dimension;

public class WorldRenderer {

	public static int DRAW_DISTANCE = 3;
	public static int DRAW_DISTANCE_Y = 2;

	private Dimension dimension;

	private VertexPointer pointer;
	private Shader shader;
	private SkyRenderer skyRenderer;
	
	private FrameBuffer buffer;
	private ScreenQuadRenderer quadRenderer;

	public WorldRenderer(Dimension dimension) {
		this.dimension = dimension;
		this.pointer = new VertexPointer();

		this.shader = new Shader("res/shaders/world/world.vert", "res/shaders/world/world.frag");
		this.skyRenderer = new SkyRenderer(dimension);
		
		this.buffer = new FrameBuffer();
		this.quadRenderer = new ScreenQuadRenderer();
		
	}

	private int vertCount = 0;
	private ArrayList<Chunk> chunks = new ArrayList<>();

	public void draw(Vector playerPos) {
		int playerInChunkX = (int) playerPos.x >> 4;
		int playerInChunkY = (int) playerPos.y >> 4;
		int playerInChunkZ = (int) playerPos.z >> 4;

		chunks.clear();

		for (int x = playerInChunkX - DRAW_DISTANCE; x < playerInChunkX + DRAW_DISTANCE; x++) {
			for (int y = playerInChunkY - DRAW_DISTANCE_Y; y < playerInChunkY + DRAW_DISTANCE_Y; y++) {
				for (int z = playerInChunkZ - DRAW_DISTANCE; z < playerInChunkZ + DRAW_DISTANCE; z++) {
					if (!MatrixHelper.isChunkInFrustum(x, y, z))
						continue;
					Chunk chunk = dimension.getChunk(x, y, z);
					if (chunk == null)
						continue;
					chunks.add(chunk);
				}
			}
		}

		pointer.initDraw();

		FloatBuffer buffer = pointer.mapBuffer().asFloatBuffer();
		vertCount = 0;
		for (Chunk chunk : chunks) {
			chunk.getBuffer().updateBuffer(playerPos);
			chunk.getBuffer().getBuffer().storeInBuffer(buffer);
			vertCount += chunk.getBuffer().getVertCount();
		}
		pointer.unmap();
		pointer.draw(vertCount);

		buffer = pointer.mapBuffer().asFloatBuffer();
		vertCount = 0;
		for (Chunk chunk : chunks) {
			if(chunk.getBuffer().hasPostRelease()) continue;
			chunk.getBuffer().getBufferAlpha().storeInBuffer(buffer);
			vertCount += chunk.getBuffer().getAlphaCount();
		}
		pointer.unmap();
		GLHelper.enableBlend();
		// GL11.glDepthMask(false);
		// GL11.glDisable(GL11.GL_CULL_FACE);
		pointer.draw(vertCount);
		// GL11.glEnable(GL11.GL_CULL_FACE);
		// GL11.glDepthMask(true);
		GLHelper.disableBlend();
		pointer.postDraw();

	}

	public void render(TextureAtlas atlas, Player player) {
		this.buffer.use();
		{
			GLHelper.checkGLError("World - Pre-render");
			skyRenderer.render(player);
			GLHelper.checkGLError("World - Sky");
			
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			
			shader.use();
			shader.setUniformVector3(shader.getUnifromLocation("skyColor"), skyRenderer.getSkyColor());
			shader.setUniformVector3(shader.getUnifromLocation("sunDirection"), skyRenderer.getSunDirection());
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			atlas.bind();
			draw(player.getPos());
			atlas.unbind();
			shader.unbind();
			
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_CULL_FACE);
	
			GLHelper.checkGLError("World - Post-render");
		
		}
		this.buffer.unbind();
		
		this.quadRenderer.render(this.buffer.getTbo_image());
		
		if(Game.INSTANCE_GAME.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_F4)) {
			this.buffer.save("temp/screenshot.png");
		}
		
	}

	public void update(Player player) {
		GLHelper.checkGLError("Pre-update");

		Shader.setViewMatrix(player.getPos(), shader);

		GLHelper.checkGLError("Post-update");

	}

	public void size() {
		Shader.setProjectionMatrix(Game.INSTANCE_GAME, shader);
		skyRenderer.size();
		this.buffer.resize();

	}

	public void release() {
		this.buffer.delete();
		shader.release();

	}

}
