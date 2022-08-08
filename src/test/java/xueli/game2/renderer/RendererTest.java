package xueli.game2.renderer;

import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game.utils.GLHelper;
import xueli.game2.core.math.TriFuncMap;
import xueli.game2.display.IGameRenderer;
import xueli.game2.renderer.buffer.AttributeBuffer;
import xueli.game2.renderer.buffer.VertexType;
import xueli.game2.renderer.pipeline.RenderSystem;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.render.shader.ShaderResourceLocation;

public class RendererTest extends IGameRenderer {

	private ShaderResourceLocation shaderResourceLocation = new ShaderResourceLocation(
			new ResourceLocation("rendertest", "shaders/shader.vert"),
			new ResourceLocation("rendertest", "shaders/shader.frag")
	);

	private RenderSystem renderer;
	private AttributeBuffer posBuffer, colorBuffer;

	public RendererTest() {
		super(800, 600, "Render Test");
	}

	@Override
	protected void renderInit() {
		renderer = new RenderSystem(shaderResource.preRegister(shaderResourceLocation, true));
		this.posBuffer = renderer.addAttribute(new AttributeBuffer(0, 3, VertexType.FLOAT));
		this.colorBuffer = renderer.addAttribute(new AttributeBuffer(1, 3, VertexType.FLOAT));
		renderer.init();
		
		renderer.setRenderType(GL30.GL_LINE_STRIP);
		
	}
	
	private Random random = new Random();
	
	private float nextFloat() {
		return random.nextFloat() * 0.4f + 0.6f;
	}
	
	private LinkedList<Vector3f> colorValue = new LinkedList<>();
	private LinkedList<Vector3f> posValue = new LinkedList<>();
	
	private static final int cycleTime = 3000;
	
	@Override
	protected void render() {
		int time = (int) (System.currentTimeMillis() % cycleTime);
		float x = (float) (0.5 * TriFuncMap.sin((float)(time * 360.0 / (cycleTime - 1)))) + (random.nextFloat() * 0.05f - 0.025f);
		float y = x * x * 4 - 0.5f + (random.nextFloat() * 0.1f - 0.05f);
		colorValue.add(new Vector3f(nextFloat(),nextFloat(),nextFloat()));
		posValue.add(new Vector3f(x, y, 0));
		
		while(posValue.size() > 400) {
			posValue.removeFirst();
			colorValue.removeFirst();
		}
		
		colorBuffer.clear();
		posBuffer.clear();
		for(int i = 0; i < posValue.size(); i++) {
			Vector3f colorValueVector3f = colorValue.get(i);
			colorValueVector3f.scale(0.96f);
			
			colorBuffer.submit(colorValueVector3f);
			posBuffer.submit(posValue.get(i));
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

	public static void main(String[] args) {
		new RendererTest().run();
	}

}
