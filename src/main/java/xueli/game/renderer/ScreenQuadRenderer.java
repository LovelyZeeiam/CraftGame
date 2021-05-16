package xueli.game.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import xueli.craftgame.renderer.VertexPointer;
import xueli.game.utils.Shader;

public class ScreenQuadRenderer {

	private static final float[] VERTICES = new float[] {
		-1,-1,	0,0,
		-1,1,	0,1,
		1,1,	1,1,
		1,-1,	1,0
	};
	
	private ScreenQuadPointer pointer;
	private Shader shader;
	
	public ScreenQuadRenderer() {
		pointer = new ScreenQuadPointer();
		
		pointer.initDraw();
		pointer.mapBuffer().asFloatBuffer().put(VERTICES);
		pointer.unmap();
		pointer.postDraw();
		
		shader = new Shader("res/shaders/screen_quad/vert.txt", "res/shaders/screen_quad/frag.txt");
		
		shader.use();
		shader.setInt(shader.getUnifromLocation("tex"), 0);
		shader.setInt(shader.getUnifromLocation("depth"), 1);
		shader.unbind();
		
	}
	
	public ScreenQuadRenderer(Shader shader) {
		pointer = new ScreenQuadPointer();
		
		pointer.initDraw();
		pointer.mapBuffer().asFloatBuffer().put(VERTICES);
		pointer.unmap();
		pointer.postDraw();
		
		this.shader = shader;
		
	}
	
	public void render(int textureId) {
		shader.use();
		pointer.initDraw();
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
		pointer.draw(GL30.GL_TRIANGLE_FAN, 0, 4);
		pointer.postDraw();
		shader.unbind();
		
	}
	
	public void render(int textureId, int depthTexID) {
		shader.use();
		pointer.initDraw();
		
		GL30.glActiveTexture(GL30.GL_TEXTURE0);
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
		GL30.glActiveTexture(GL30.GL_TEXTURE1);
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, depthTexID);
		
		pointer.draw(GL30.GL_TRIANGLE_FAN, 0, 4);
		pointer.postDraw();
		shader.unbind();
		
	}
	
	public ScreenQuadPointer getPointer() {
		return pointer;
	}
	
	public Shader getShader() {
		return shader;
	}
	
	public static class ScreenQuadPointer extends VertexPointer {
		
		public ScreenQuadPointer() {
			super(1024, GL30.GL_STATIC_DRAW);
			
		}
		
		@Override
		protected void registerVertex() {
			// UV
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * 4, 2 * 4);
			GL20.glEnableVertexAttribArray(1);
			
			// position
			GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * 4, 0 * 4);
			GL20.glEnableVertexAttribArray(0);
			
		}
		
	}

}
