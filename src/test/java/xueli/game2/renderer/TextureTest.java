package xueli.game2.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game.utils.GLHelper;
import xueli.game2.display.IGameRenderer;
import xueli.game2.renderer.buffer.AttributeBuffer;
import xueli.game2.renderer.buffer.VertexType;
import xueli.game2.renderer.pipeline.RenderSystem;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.shader.Shader;
import xueli.game2.resource.submanager.render.shader.ShaderResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureType;

public class TextureTest extends IGameRenderer {

	private ShaderResourceLocation shaderResourceLocation = new ShaderResourceLocation(
			new ResourceLocation("texturetest", "shaders/shader.vert"),
			new ResourceLocation("texturetest", "shaders/shader.frag")
	);

	private TextureResourceLocation textureResourceLocation = new TextureResourceLocation(new ResourceLocation("texturetest", "images/test.jpg"), TextureType.LEGACY);

	private RenderSystem renderer;
	private AttributeBuffer posBuffer, colorBuffer, uvBuffer;

	public TextureTest() {
		super(800, 600, "Texture Test");
	}

	@Override
	protected void renderInit() {
		GLHelper.checkGLError("Enable");

		renderer = new RenderSystem(shaderResource.preRegister(shaderResourceLocation, false));
		this.posBuffer = renderer.addAttribute(new AttributeBuffer(0, 3, VertexType.FLOAT));
		this.colorBuffer = renderer.addAttribute(new AttributeBuffer(1, 3, VertexType.FLOAT));
		this.uvBuffer = renderer.addAttribute(new AttributeBuffer(2, 2, VertexType.FLOAT));
		renderer.init();

		renderer.setRenderType(GL11.GL_TRIANGLE_STRIP);
		renderer.setTextureId(0, textureResource.preRegister(textureResourceLocation, false));

		GLHelper.checkGLError("Render Init");

		Shader shader = renderer.getShader();
		shader.bind(() -> {
			shader.setInt(shader.getUnifromLocation("textureSampler"), 0);
		});

		GLHelper.checkGLError("Shader");

		posBuffer.submit(new Vector3f(-0.5f, -0.5f, 0));
		colorBuffer.submit(new Vector3f(1,1,1));
		uvBuffer.submit(new Vector2f(0,1));
		posBuffer.submit(new Vector3f(-0.5f, 0.5f, 0));
		colorBuffer.submit(new Vector3f(1,1,1));
		uvBuffer.submit(new Vector2f(0,0));
		posBuffer.submit(new Vector3f(0.5f, -0.5f, 0));
		colorBuffer.submit(new Vector3f(1,1,1));
		uvBuffer.submit(new Vector2f(1,1));
		posBuffer.submit(new Vector3f(0.5f, 0.5f, 0));
		colorBuffer.submit(new Vector3f(1,1,1));
		uvBuffer.submit(new Vector2f(1,0));

	}

	@Override
	protected void render() {
		renderer.tick();
		GLHelper.checkGLError("Render");

	}

	@Override
	protected void renderRelease() {
		posBuffer.clear();
		colorBuffer.clear();

		renderer.release();
	}

	public static void main(String[] args) {
		new TextureTest().run();
	}

}
