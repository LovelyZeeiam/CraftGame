package xueli.craftgame.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.Texture;

import xueli.craftgame.CraftGame;
import xueli.game.utils.GLHelper;
import xueli.game.utils.TextureAtlas;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.vector.Vector2i;
import xueli.gamengine.utils.renderer.IRenderer;
import xueli.gamengine.utils.renderer.VertexPointer;
import xueli.gamengine.utils.resource.Shader;

public class WorldRenderer implements IRenderer {

	private final World world;
	private final VertexPointer pointer;

	private final Shader shader;
	private final TextureAtlas blockTextureAtlas;

	private Matrix4f projMatrix, viewMatrix;

	public WorldRenderer(World world) {
		this.world = world;
		this.pointer = new VertexPointer();

		this.shader = CraftGame.INSTANCE_CRAFT_GAME.getShaderResource().get("world");
		this.blockTextureAtlas = (TextureAtlas) CraftGame.INSTANCE_CRAFT_GAME.getTextureManager().getTexture("blocks");

		size();
		init();

	}

	@Override
	public void init() {

	}

	@Override
	public void size() {
		this.projMatrix = Shader.setProjectionMatrix(CraftGame.INSTANCE_CRAFT_GAME, shader);

	}

	private ArrayList<Chunk> getDrawChunks(Vector camPos, int draw_distance) {
		Vector2i chunkPos = World.getChunkPosFromBlock((int) camPos.x, (int) camPos.z);
		// 将要绘制的区块成列表
		ArrayList<Chunk> drawChunk = new ArrayList<>();

		for (int x = chunkPos.x - draw_distance; x <= chunkPos.x + draw_distance; x++) {
			for (int z = chunkPos.y - draw_distance; z <= chunkPos.y + draw_distance; z++) {
				// long key = MathUtils.vert2ToLong(x, z);
				if (MatrixHelper.isChunkInFrustum(x, 16 * Chunk.SUBCHUNK_COUNT, z)) {
					Chunk chunk = world.getChunk(x, z);
					if (chunk != null) {
						drawChunk.add(chunk);
					} else {

					}
				}
			}
		}

		return drawChunk;
	}

	@Override
	public void render(Vector camPos) {
		GL11.glEnable(GL11.GL_CULL_FACE);

		this.viewMatrix = Shader.setViewMatrix(camPos, shader);
		shader.use();
		pointer.initDraw();

		GLHelper.checkGLError("World Renderer: init");

		FloatBuffer mappedBuffer = pointer.mapBuffer().asFloatBuffer();
		int vertcount = 0;

		ArrayList<Chunk> chunksNeedDrawing = getDrawChunks(camPos, World.RENDER_DISTANCE);

		for (Chunk chunk : chunksNeedDrawing) {
			ChunkMeshBuilder meshBuilder = chunk.getMeshBuilder();
			meshBuilder.drawUpdate();

			mappedBuffer.put(meshBuilder.getBuffer().getData());
			vertcount += meshBuilder.getVertCount();

		}

		mappedBuffer.flip();

		pointer.unmap();
		GLHelper.checkGLError("World Renderer: map buffer");

		blockTextureAtlas.bind();
		GLHelper.checkGLError("World Renderer: texture binding");

		pointer.draw(vertcount);

		Texture.unbind();
		pointer.postDraw();
		shader.unbind();

		GLHelper.checkGLError("World Renderer");

		GL11.glDisable(GL11.GL_CULL_FACE);

	}

	public Matrix4f getProjMatrix() {
		return projMatrix;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	@Override
	public void release() {
		this.pointer.release();

	}

}
