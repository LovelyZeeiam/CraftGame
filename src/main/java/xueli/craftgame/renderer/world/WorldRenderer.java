package xueli.craftgame.renderer.world;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL13;
import xueli.game.Game;
import xueli.game.utils.GLHelper;
import xueli.game.utils.Shader;
import xueli.game.utils.VertexPointer;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;
import xueli.game.vector.Vector;
import xueli.craftgame.Player;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.Dimension;

public class WorldRenderer {

	public static int DRAW_DISTANCE = 3;

	private Dimension dimension;

	private VertexPointer pointer;

	private Shader shader;

	public WorldRenderer(Dimension dimension) {
		this.dimension = dimension;
		this.pointer = new VertexPointer();

		this.shader = new Shader("res/shaders/world/vert.txt", "res/shaders/world/frag.txt");

	}

	private int vertCount = 0;

	public void draw(Vector playerPos) {
		int playerInChunkX = (int) playerPos.x >> 4;
		int playerInChunkY = (int) playerPos.y >> 4;
		int playerInChunkZ = (int) playerPos.z >> 4;

		pointer.initDraw();

		FloatBuffer buffer = pointer.mapBuffer().asFloatBuffer();
		vertCount = 0;

		for (int x = playerInChunkX - DRAW_DISTANCE; x < playerInChunkX + DRAW_DISTANCE; x++) {
			for (int y = playerInChunkY - DRAW_DISTANCE; y < playerInChunkY + DRAW_DISTANCE; y++) {
				for (int z = playerInChunkZ - DRAW_DISTANCE; z < playerInChunkZ + DRAW_DISTANCE; z++) {
					if (!MatrixHelper.isChunkInFrustum(x, y, z))
						continue;
					Chunk chunk = dimension.getChunk(x, y, z);
					if (chunk == null)
						continue;
					chunk.updateBuffer();

					chunk.getBuffer().storeInBuffer(buffer);
					vertCount += chunk.getVertCount();

				}
			}
		}

		pointer.unmap();
		pointer.draw(vertCount);
		pointer.postDraw();

	}

	public void render(TextureAtlas atlas, Player player) {
		GLHelper.checkGLError("Pre-renderer");

		shader.use();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		atlas.bind();
		draw(player.getPos());
		atlas.unbind();
		shader.unbind();

		GLHelper.checkGLError("post-renderer");

	}

	public void update(Player player) {
		GLHelper.checkGLError("Pre-update");

		Shader.setViewMatrix(player.getPos(), shader);

		GLHelper.checkGLError("Post-update");

	}

	public void size() {
		Shader.setProjectionMatrix(Game.INSTANCE_GAME, shader);

	}

	public void release() {
		shader.release();

	}

}
