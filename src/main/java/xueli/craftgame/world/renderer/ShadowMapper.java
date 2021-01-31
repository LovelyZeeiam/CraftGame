package xueli.craftgame.world.renderer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import xueli.craftgame.CraftGame;
import xueli.craftgame.world.WorldLogic;
import xueli.gamengine.utils.FrameBuffer;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.Shader;

/**
 * Later...
 */
@Deprecated
public class ShadowMapper {

	private static final int SHADOW_MAPPER_SIZE = 4096;

	private CraftGame cg;
	private WorldLogic logic;
	private FrameBuffer buffer;
	private Shader depthShader;

	private float lightDirectionX = 50;
	private float lightDirectionZ = 50;

	public ShadowMapper(CraftGame cg, WorldLogic logic) {
		this.cg = cg;
		this.logic = logic;

		this.buffer = new FrameBuffer();
		this.buffer.create(SHADOW_MAPPER_SIZE, SHADOW_MAPPER_SIZE);
		this.buffer.setOnlyDepthBuffer();

		this.depthShader = cg.getShaderResource().get("shadowmapping");

	}

	public void renderToDepthBuffer(Renderer normalRenderer) {
		buffer.bind();
		glViewport(0, 0, SHADOW_MAPPER_SIZE, SHADOW_MAPPER_SIZE);
		glClear(GL_DEPTH_BUFFER_BIT);

		float lightAngleX = (float) Math.toDegrees(Math.acos(lightDirectionZ));
		float lightAngleY = (float) Math.toDegrees(Math.asin(lightDirectionX));
		float lightAngleZ = 0;

		normalRenderer.initDraw();
		depthShader.use();
		normalRenderer.draw(logic.getVertexCount());
		depthShader.unbind();

		buffer.unbind();

		GLHelper.checkGLError("World: ShadowMapper");

	}

}
