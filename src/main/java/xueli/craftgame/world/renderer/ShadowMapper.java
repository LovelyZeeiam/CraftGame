package xueli.craftgame.world.renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;
import xueli.craftgame.CraftGame;
import xueli.craftgame.world.WorldLogic;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.callbacks.KeyCallback;
import xueli.gamengine.utils.framebuffer.FrameBufferDepth;
import xueli.gamengine.utils.math.MatrixHelper;
import xueli.gamengine.utils.resource.Shader;
import xueli.gamengine.utils.vector.Vector;

import static org.lwjgl.opengl.GL11.*;

public class ShadowMapper {

	private static final int SHADOW_MAPPER_SIZE = 1024;

	private CraftGame cg;
	private WorldLogic logic;
	private FrameBufferDepth buffer;
	private Shader depthShader;

	private int loc_projMatrix, loc_viewMatrix;
	private Matrix4f projMatrix4f,viewMatrix4f;
	
	private float lightDirectionX = 135;
	private float lightDirectionZ = -45;

	public ShadowMapper(CraftGame cg, WorldLogic logic) {
		this.cg = cg;
		this.logic = logic;

		this.buffer = new FrameBufferDepth();
		this.buffer.create(SHADOW_MAPPER_SIZE, SHADOW_MAPPER_SIZE);

		this.depthShader = cg.getShaderResource().get("shadowmapping");
		this.depthShader.use();
		loc_projMatrix = this.depthShader.getUnifromLocation("projMatrix");
		loc_viewMatrix = this.depthShader.getUnifromLocation("viewMatrix");
		
		float lightAngleX = (float) Math.toDegrees(Math.acos(lightDirectionZ));
		float lightAngleY = (float) Math.toDegrees(Math.asin(lightDirectionX));
		float lightAngleZ = 0;
		
		projMatrix4f = MatrixHelper.ortho(-1000.0f, 1000.0f, -1000.0f, 1000.0f, 0.1f, 100000.0f);
		viewMatrix4f = MatrixHelper.player(new Vector(0, 200, 0, lightAngleX, lightAngleY, lightAngleZ));
		depthShader.setUniformMatrix(loc_projMatrix, projMatrix4f);
		depthShader.setUniformMatrix(loc_viewMatrix, viewMatrix4f);
		
		this.depthShader.unbind();
		
	}
	
	public FrameBufferDepth getBuffer() {
		return buffer;
	}
	
	public void bindBuffer() {
		buffer.bind();
		
	}
	
	public void clearBuffer() {
		glClear(GL_DEPTH_BUFFER_BIT);
	
	}
	
	/**
	 * 设置材质槽位为1
	 * 然后在绘制时就将1槽位的材质变为depth buffer的材质id
	 */
	public void setDepthMap(Shader worldShader) {
		worldShader.use();
		worldShader.setInt(worldShader.getUnifromLocation("depthMap"), 1);
		worldShader.setUniformMatrix(worldShader.getUnifromLocation("lightDirectionMatrix"), viewMatrix4f);
		worldShader.setUniformMatrix(worldShader.getUnifromLocation("lightTransMatrix"), viewMatrix4f);
		worldShader.unbind();
		
	}

	public void renderToDepthBuffer(Renderer normalRenderer) {
		glViewport(0, 0, SHADOW_MAPPER_SIZE, SHADOW_MAPPER_SIZE);	

		normalRenderer.initDraw();
		depthShader.use();
		normalRenderer.draw(logic.getVertexCount());
		depthShader.unbind();
		normalRenderer.postDraw();

		GLHelper.checkGLError("World: ShadowMapper");
		
		if(KeyCallback.keysOnce[GLFW.GLFW_KEY_K]) {
			buffer.save("output/save.png");
		}
		

	}
	
	public void unbindBuffer() {
		buffer.unbind();
		
	}

}
