package xueli.craftgame.state;

import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import xueli.craftgame.Player;
import xueli.craftgame.init.Blocks;
import xueli.craftgame.world.Dimension;
import xueli.game.renderer.NVGRenderer;
import xueli.game.utils.NVGColors;
import xueli.game.utils.Shader;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;

import static org.lwjgl.nanovg.NanoVG.*;

public class StateWorld extends NVGRenderer {

	private static StateWorld INSTANCE;
	
	private static final String FONT_NAME = "Game";

	private TextureAtlas blocksTextureAtlas;
	private Blocks blocks;
	private Shader shader;
	
	private Player player;
	
	private Dimension dimension;

	public StateWorld() {
		super();
		INSTANCE = this;

		try {
			this.blocksTextureAtlas = TextureAtlas.generateAtlas("res/textures/blocks.json", "res/textures/blocks/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.shader = new Shader("res/shaders/world/vert.txt", "res/shaders/world/frag.txt");
		
		nvgCreateFont(nvg, FONT_NAME, "res/fonts/Minecraft-Ascii.ttf");
		
		this.blocks = new Blocks();
		this.blocks.searchForAllBlock();
		
		this.dimension = new Dimension();
		for(int i = -3; i < 3; i++) {
			for(int w = -3; w < 3; w++) {
				for(int k = -3; k < 3; k++) {
					dimension.requireGenChunk(i, w, k);
				}
			}
		}
		
		this.player = new Player(dimension);
		
		game.getDisplay().setMouseGrabbed(true);

	}

	@Override
	public void update() {
		player.tick();
		
		Shader.setViewMatrix(player.getPos(), shader);
		MatrixHelper.calculateFrustumPlane();
		
		player.pickTick();
		
		if(game.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE))
			game.getDisplay().setMouseGrabbed(!game.getDisplay().isMouseGrabbed());
		
	}
	
	@Override
	public void size() {
		super.size();
		Shader.setProjectionMatrix(game, shader);
		
	}

	@Override
	public void stroke() {
		String posTextString = "Position: " + (int) player.getPos().x + ", " + (int) player.getPos().y + ", " + (int) player.getPos().z;
		String fpsTextString = "FPS: " + Time.fps;
		
		float fontSize = 15.0f * game.getDisplayScale();
		float measuredTextLength = Math.max(measureTextWidth(fontSize, posTextString), measureTextWidth(fontSize, fpsTextString));
		
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, 4.0f * game.getDisplayScale() + measuredTextLength, 6.0f * game.getDisplayScale() + 2 * fontSize);
		nvgFillColor(nvg, NVGColors.TRANSPARENT_BLACK);
		nvgFill(nvg);
		
		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, FONT_NAME);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgText(nvg, 2.0f * game.getDisplayScale(), 2.0f * game.getDisplayScale(), posTextString);
		nvgText(nvg, 2.0f * game.getDisplayScale(), 4.0f * game.getDisplayScale() + fontSize, fpsTextString);
		

	}

	@Override
	public void render() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		shader.use();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		blocksTextureAtlas.bind();
		dimension.draw(player.getPos());
		blocksTextureAtlas.unbind();
		shader.unbind();
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		super.render();
	}

	@Override
	public void release() {
		super.release();
		blocksTextureAtlas.release();

	}
	
	public static StateWorld getInstance() {
		return INSTANCE;
	}

	public TextureAtlas getBlocksTextureAtlas() {
		return blocksTextureAtlas;
	}
	
	public Blocks getBlocks() {
		return blocks;
	}

}
