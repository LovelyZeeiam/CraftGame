package xueli.craftgame.block;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.HashMap;

import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import xueli.craftgame.CraftGame;
import xueli.craftgame.world.WorldGLData;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.FrameBuffer;
import xueli.gamengine.utils.Shader;
import xueli.gamengine.utils.Vector;

public class BlockReviewGenerator {

	private static final int size = 64;

	private static HashMap<String, Integer> reviewTextures = new HashMap<>();

	@WorldGLData
	public static void generate(long nvg, CraftGame cg, TextureAtlas blockAtlasTexture) {
		Shader shader = cg.getShaderResource().get("world");
		shader.use();
		Shader.setProjectionMatrix(cg, shader, size, size, 1.0f);
		Shader.setViewMatrix(new Vector(-55f, 56f, -55f, 144.735f, -45.005f, 0), shader);
		shader.unbind();

		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		// UV
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * 4, 0);
		GL20.glEnableVertexAttribArray(1);
		// 颜色
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * 4, 2 * 4);
		GL20.glEnableVertexAttribArray(2);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * 4, 5 * 4);
		GL20.glEnableVertexAttribArray(0);
		BlockResource.blockDatas.forEach((namespace, data) -> {
			int vertCount = 0;
			FloatList list = new FloatList();
			for (byte face = 0; face < 6; face++)
				vertCount += data.getDrawData(list, data, 0, 0, 0, face, blockAtlasTexture, null, null);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, list.getData(), GL15.GL_STATIC_DRAW);

			FrameBuffer buffer = new FrameBuffer();
			buffer.create(size, size);
			buffer.bind();

			// 只有调用了这个方法 opengl才肯正常绘制出来
			glViewport(0, 0, size, size);

			glEnable(GL_DEPTH_TEST);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glClearColor(0, 0, 0, 0);

			shader.use();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, blockAtlasTexture.id);
			glDrawArrays(GL_TRIANGLES, 0, vertCount);
			glBindTexture(GL_TEXTURE_2D, 0);
			shader.unbind();

			buffer.unbind();

			glDisable(GL_DEPTH_BUFFER_BIT);

			reviewTextures.put(namespace,
					NanoVGGL2.nvglCreateImageFromHandle(nvg, buffer.getTbo(), size, size, NanoVG.NVG_IMAGE_NEAREST));

		});

		GL15.glDeleteBuffers(vbo);
		GL30.glDeleteVertexArrays(vao);

		// 在我的电脑上面测试 发现只有上面调用了viewport方法渲染出来的图片才是正常的 同时显示的区域也会有问题
		glViewport(0, 0, cg.getDisplay().getWidth(), cg.getDisplay().getHeight());

	}

	public static int getTexture(String namespace) {
		return reviewTextures.get(namespace);
	}

}
