package xueli.craftgame.state;

import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import xueli.craftgame.Player;
import xueli.craftgame.init.Blocks;
import xueli.craftgame.renderer.world.WorldRenderer;
import xueli.craftgame.world.Dimension;
import xueli.game.renderer.NVGRenderer;
import xueli.game.utils.NVGColors;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;

import static org.lwjgl.nanovg.NanoVG.*;

public class StateWorld extends NVGRenderer {

	private static StateWorld INSTANCE;

	private static final String FONT_NAME = "Game";

	private TextureAtlas blocksTextureAtlas;
	private Blocks blocks;

	private Player player;

	private Dimension dimension;
	private WorldRenderer worldRenderer;

	private int tex_cross;

	public StateWorld() {
		super();
		INSTANCE = this;

		try {
			this.blocksTextureAtlas = TextureAtlas.generateAtlas("res/textures/blocks.json", "res/textures/blocks/");
		} catch (IOException e) {
			e.printStackTrace();
		}

		nvgCreateFont(nvg, FONT_NAME, "res/fonts/Minecraft-Ascii.ttf");
		this.tex_cross = nvgCreateImage(nvg, "res/textures/hud/cross.png", NVG_IMAGE_NEAREST);

		this.blocks = new Blocks();
		this.blocks.searchForAllBlock();

		this.dimension = new Dimension();
		this.worldRenderer = this.dimension.getRenderer();
		for (int i = -3; i < 3; i++) {
			for (int w = -3; w < 3; w++) {
				for (int k = -3; k < 3; k++) {
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

		worldRenderer.update(player);
		MatrixHelper.calculateFrustumPlane();

		player.pickTick();

		if (game.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE))
			game.getDisplay().setMouseGrabbed(!game.getDisplay().isMouseGrabbed());

	}

	@Override
	public void size() {
		super.size();

		worldRenderer.size();

	}

	@Override
	public void stroke() {
		String posTextString = "Position: " + (int) player.getPos().x + ", " + (int) player.getPos().y + ", "
				+ (int) player.getPos().z;
		String fpsTextString = "FPS: " + Time.fps;

		float fontSize = 15.0f * game.getDisplayScale();
		float measuredTextLength = Math.max(measureTextWidth(fontSize, posTextString),
				measureTextWidth(fontSize, fpsTextString));

		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, 4.0f * game.getDisplayScale() + measuredTextLength,
				6.0f * game.getDisplayScale() + 2 * fontSize);
		nvgFillColor(nvg, NVGColors.TRANSPARENT_BLACK);
		nvgFill(nvg);

		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, FONT_NAME);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgText(nvg, 2.0f * game.getDisplayScale(), 2.0f * game.getDisplayScale(), posTextString);
		nvgText(nvg, 2.0f * game.getDisplayScale(), 4.0f * game.getDisplayScale() + fontSize, fpsTextString);

		nvgImagePattern(nvg, game.getWidth() / 2 - 5.0f * game.getDisplayScale(),
				game.getHeight() / 2 - 5.0f * game.getDisplayScale(), 10.0f * game.getDisplayScale(),
				10.0f * game.getDisplayScale(), 0, tex_cross, 1.0f, paint);
		nvgBeginPath(nvg);
		nvgRect(nvg, game.getWidth() / 2 - 5.0f * game.getDisplayScale(),
				game.getHeight() / 2 - 5.0f * game.getDisplayScale(), 10.0f * game.getDisplayScale(),
				10.0f * game.getDisplayScale());
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

	}

	@Override
	public void render() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		worldRenderer.render(blocksTextureAtlas, player);

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);

		super.render();
	}

	@Override
	public void release() {
		super.release();
		blocksTextureAtlas.release();

		worldRenderer.release();

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
