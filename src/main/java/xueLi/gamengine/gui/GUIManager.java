package xueLi.gamengine.gui;

import xueLi.gamengine.resource.GuiResource;
import xueLi.gamengine.resource.Options;
import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.Logger;
import xueLi.gamengine.utils.Shader;

import static org.lwjgl.nanovg.NanoVGGL3.*;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUIManager {

	private Display display;
	private Options options;

	// 当前GUI
	private GUI currentGui;
	private NVGLUFramebuffer currentFramebuffer;
	private int currentFBO_tbo;
	// 即将淡入的GUI
	private GUI fadeInGui;
	private NVGLUFramebuffer fadeInFramebuffer;
	private int fadeInFBO_tbo;
	// 淡入指数
	private float fade = 0.0f;
	// 控制淡入淡出的着色器
	private Shader guiShader;
	// 锵 ~ GUIの标准素养 之 全屏展示
	private static int vao,vbo,tpo;
	// GUI的fade时间 让gui有一个逐渐显示的过程
	private long fade_duration;
	
	static long nvg;

	static {
		nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (nvg == 0) {
			Logger.error(new Throwable("[GUI] Emm, You don't want a game without gui, do u?"));
		}
		
		IntBuffer verticesBuffer = BufferUtils.createIntBuffer(8);
		verticesBuffer.put(new int[] {
			1,1,
			1,-1,
			-1,-1,
			-1,1,
		});
		verticesBuffer.flip();
		IntBuffer texCoordBuffer = BufferUtils.createIntBuffer(8);
		texCoordBuffer.put(new int[] {
			1,1,
			1,0,
			0,0,
			0,1,
		});
		texCoordBuffer.flip();
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_INT, false, 0, 0);
		GL20.glEnableVertexAttribArray(0);
		tpo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tpo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_INT, false, 0, 0);
		GL20.glEnableVertexAttribArray(1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		
	}

	public GUIManager(Display display, Options options, Shader guiShader) {
		this.display = display;
		this.guiShader = guiShader;
		this.options = options;
		
		guiShader.use();
		guiShader.setInt(guiShader.getUnifromLocation("texture1"), 0);
		guiShader.setInt(guiShader.getUnifromLocation("texture2"), 1);
		guiShader.unbind();
		
		fade_duration = this.options.get("fade_duration").getAsLong();
		
	}
	
	private void createFrameBuffer() {
		currentFramebuffer = nvgluCreateFramebuffer(nvg, display.getWidth(), display.getHeight(),NVG_IMAGE_NEAREST);
		currentFBO_tbo = currentFramebuffer.texture();
		
		fadeInFramebuffer = nvgluCreateFramebuffer(nvg, display.getWidth(), display.getHeight(),NVG_IMAGE_NEAREST);
		fadeInFBO_tbo = fadeInFramebuffer.texture();
		
	}
	
	private void deleteFrameBuffer() {
		if(currentFramebuffer != null)
			nvgluDeleteFramebuffer(nvg, currentFramebuffer);
		if(fadeInFramebuffer != null)
			nvgluDeleteFramebuffer(nvg, fadeInFramebuffer);
		
	}

	public int loadTexture(String path) {
		return nvgCreateImage(nvg, path, NVG_IMAGE_GENERATE_MIPMAPS);
	}
	
	private GuiResource resource;

	// 字体ID
	int fontID = -1;

	public void setResourceSource(GuiResource resource) {
		this.resource = resource;
		this.fontID = nvgCreateFont(nvg, "simhei", resource.getPathString() + "fonts/simhei.ttf");

	}

	public GUI setGui(String guiName) {
		this.currentGui = resource.getGui(guiName);
		display.setSubtitle(currentGui.titleString);
		return currentGui;
	}
	
	private long fadeStartTime = -1;
	
	public GUI setFadeinGui(String guiName) {
		this.fadeInGui = resource.getGui(guiName);
		this.fade = 0.0f;
		return fadeInGui;
	}

	public void draw() {
		/**
		 * 思路:
		 * 1. 将2个GUI分别渲染到Frame Buffer内部
		 * 2. 向着色器提供mix比例
		 * 
		 */
		if (currentGui != null){
			nvgluBindFramebuffer(nvg, currentFramebuffer);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
			GL11.glClearColor(currentGui.backgroundColor.r(), currentGui.backgroundColor.g(),
					currentGui.backgroundColor.b(), currentGui.backgroundColor.a());
			nvgBeginFrame(nvg, display.getWidth(), display.getHeight(), display.getRatio());
			currentGui.draw(nvg);
			nvgEndFrame(nvg);
			nvgluBindFramebuffer(nvg, null);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL13.glBindTexture(GL13.GL_TEXTURE_2D, currentFBO_tbo);
			
		}
		
		if(fadeInGui != null) {
			nvgluBindFramebuffer(nvg, fadeInFramebuffer);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
			GL11.glClearColor(fadeInGui.backgroundColor.r(), fadeInGui.backgroundColor.g(),
					fadeInGui.backgroundColor.b(), fadeInGui.backgroundColor.a());
			nvgBeginFrame(nvg, display.getWidth(), display.getHeight(), display.getRatio());
			fadeInGui.draw(nvg);
			nvgEndFrame(nvg);
			nvgluBindFramebuffer(nvg, null);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, fadeInFBO_tbo);
			
			if(this.fadeStartTime == -1) {
				this.fadeStartTime = System.currentTimeMillis();
			}
			
			fade = (float)(System.currentTimeMillis() - fadeStartTime) / fade_duration;
			
		}	
		
		guiShader.use();
		GL30.glBindVertexArray(vao);
		guiShader.setFloat(guiShader.getUnifromLocation("mix_value"), fade);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);
		GL30.glBindVertexArray(0);
		guiShader.unbind();
		
		if(fade >= 1) {
			this.currentGui = fadeInGui;
			this.fadeInGui = null;
			this.fadeStartTime = -1;
			this.fade = 0.0f;
			display.setSubtitle(currentGui.titleString);
			
		}
		
	}

	public void size() {
		deleteFrameBuffer();
		createFrameBuffer();

		if (currentGui != null) {
			currentGui.size();
			
		}
		if(fadeInGui != null) {
			fadeInGui.size();
			
		}
		
	}

	public void destroy() {
		nvgDelete(nvg);
		guiShader.release();

	}

}
