package xueli.game2.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import xueli.game.utils.GLHelper;
import xueli.game.utils.NVGColors;
import xueli.game2.display.Display;
import xueli.game2.display.IGameRenderer;
import xueli.game2.renderer.buffer.AttributeBuffer;
import xueli.game2.renderer.buffer.VertexType;
import xueli.game2.renderer.pipeline.RenderSystem;
import xueli.game2.renderer.ui.NanoVGContext;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.shader.Shader;
import xueli.game2.resource.submanager.render.shader.ShaderResourceLocation;
import xueli.game2.resource.submanager.render.texture.atlas.AtlasResourceHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.nanovg.NanoVG.*;

public class AtlasTextureTest extends IGameRenderer {

	private ShaderResourceLocation shaderResourceLocation = new ShaderResourceLocation(
			new ResourceLocation("texturetest", "shaders/shader.vert"),
			new ResourceLocation("texturetest", "shaders/shader.frag")
	);

	private ResourceLocation atlasResourceLocation = new ResourceLocation("atlastest", "images/");
	private ResourceLocation fontResourceLocation = new ResourceLocation("fonts/minecraft.ttf");

	private long nvg;

	private RenderSystem renderer;
	private AttributeBuffer posBuffer, colorBuffer, uvBuffer;

	public AtlasTextureTest() {
		super(800, 600, "Atlas Texture Test");
	}

	private List<Map.Entry<String, AtlasResourceHolder>> holders = new ArrayList<>();
	private int holderSize;

	@Override
	protected void renderInit() {
		GLHelper.checkGLError("Enable");

		renderer = new RenderSystem(shaderResource.preRegister(shaderResourceLocation, false));
		this.posBuffer = renderer.addAttribute(new AttributeBuffer(0, 3, VertexType.FLOAT));
		this.colorBuffer = renderer.addAttribute(new AttributeBuffer(1, 3, VertexType.FLOAT));
		this.uvBuffer = renderer.addAttribute(new AttributeBuffer(2, 2, VertexType.FLOAT));
		renderer.init();

		renderer.setRenderType(GL11.GL_TRIANGLE_STRIP);

		GLHelper.checkGLError("Render Init");

		Shader shader = renderer.getShader();
		shader.bind(() -> {
			shader.setInt(shader.getUnifromLocation("textureSampler"), 0);
		});

		GLHelper.checkGLError("Shader");

		atlasTextureResource.findAndRegister(atlasResourceLocation, t -> true);
		Map<String, AtlasResourceHolder> holders = atlasTextureResource.getAllHolders(atlasResourceLocation);
		this.holders.addAll(holders.entrySet());
		this.holderSize = holders.size();

		GLHelper.checkGLError("Atlas");

		this.nvg = NanoVGContext.INSTANCE.getNvg();
		fontResource.preRegister(fontResourceLocation, true);

		GLHelper.checkGLError("NanoVG");

	}

	private Random random = new Random();
	private Map.Entry<String, AtlasResourceHolder> currentEntry = null;

	@Override
	protected void render() {
		timer.runTick(() -> {
			this.currentEntry = holders.get(random.nextInt(holderSize));
		});

		if(currentEntry != null) {
			String name = currentEntry.getKey();
			AtlasResourceHolder value = currentEntry.getValue();

			renderer.setTextureId(0, value.textureId());
			Vector2f leftTop = value.leftTop();
			Vector2f rightBottom = value.rightBottom();

			posBuffer.clear();
			colorBuffer.clear();
			uvBuffer.clear();

			posBuffer.submit(new Vector3f(-0.5f, -0.5f, 0));
			colorBuffer.submit(new Vector3f(1,1,1));
			uvBuffer.submit(new Vector2f(leftTop.x,rightBottom.y));
			posBuffer.submit(new Vector3f(-0.5f, 0.5f, 0));
			colorBuffer.submit(new Vector3f(1,1,1));
			uvBuffer.submit(new Vector2f(leftTop.x,leftTop.y));
			posBuffer.submit(new Vector3f(0.5f, -0.5f, 0));
			colorBuffer.submit(new Vector3f(1,1,1));
			uvBuffer.submit(new Vector2f(rightBottom.x,rightBottom.y));
			posBuffer.submit(new Vector3f(0.5f, 0.5f, 0));
			colorBuffer.submit(new Vector3f(1,1,1));
			uvBuffer.submit(new Vector2f(rightBottom.x,leftTop.y));

			Display display = getDisplay();
			float width = display.getWidth();
			float height = display.getHeight();
			nvgBeginFrame(nvg, width, height, width / height);

			nvgFontSize(nvg, 20.0f);
			fontResource.registerAndBind(fontResourceLocation, true);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
			nvgText(nvg, 0, 0, name);

			nvgEndFrame(nvg);

		}

		renderer.tick();
		GLHelper.checkGLError("Render");

	}

	@Override
	protected void renderRelease() {
		posBuffer.clear();
		colorBuffer.clear();

		renderer.release();
	}

	public static void main(String[] args) throws IOException {
		new AtlasTextureTest().run();
	}

}
