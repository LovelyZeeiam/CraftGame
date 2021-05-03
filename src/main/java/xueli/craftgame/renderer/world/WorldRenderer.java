package xueli.craftgame.renderer.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;
import xueli.game.Game;
import xueli.game.utils.GLHelper;
import xueli.game.utils.Shader;
import xueli.game.utils.VertexPointer;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;
import xueli.game.vector.Vector;
import xueli.craftgame.entity.Player;
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
		
		ArrayList<Chunk> chunks = new ArrayList<>();

		for (int x = playerInChunkX - DRAW_DISTANCE; x < playerInChunkX + DRAW_DISTANCE; x++) {
			for (int y = playerInChunkY - DRAW_DISTANCE; y < playerInChunkY + DRAW_DISTANCE; y++) {
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
		Collections.sort(chunks, new Comparator<Chunk>() {
				@Override
				public int compare(Chunk c1, Chunk c2) {
					Vector3f o1 = new Vector3f(c1.getChunkX() * 16 + 8, c1.getChunkY() * 16 + 8, c1.getChunkZ() * 16 + 8);
					Vector3f o2 = new Vector3f(c2.getChunkX() * 16 + 8, c2.getChunkY() * 16 + 8, c2.getChunkZ() * 16 + 8);
					double d1 = Math.sqrt(
								(o1.getX() - playerPos.x) * (o1.getX() - playerPos.x) +
								(o1.getY() - playerPos.y) * (o1.getY() - playerPos.y) +
								(o1.getZ() - playerPos.z) * (o1.getZ() - playerPos.z)
							);
					double d2 = Math.sqrt(
							(o2.getX() - playerPos.x) * (o2.getX() - playerPos.x) +
							(o2.getY() - playerPos.y) * (o2.getY() - playerPos.y) +
							(o2.getZ() - playerPos.z) * (o2.getZ() - playerPos.z)
						);
					return d1 > d2 ? 0 : 1;
				}
			});
		for (Chunk chunk : chunks) {
			chunk.getBuffer().getBufferAlpha().storeInBuffer(buffer);
			vertCount += chunk.getBuffer().getAlphaCount();
		}
		pointer.unmap();
		GLHelper.enableBlend();
		//GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_CULL_FACE);
		pointer.draw(vertCount);
		GL11.glDisable(GL11.GL_CULL_FACE);
		//GL11.glDepthMask(true);
		GLHelper.disableBlend();
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
