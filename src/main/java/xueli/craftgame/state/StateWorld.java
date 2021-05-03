package xueli.craftgame.state;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import xueli.craftgame.entity.Player;
import xueli.craftgame.init.Blocks;
import xueli.craftgame.init.Models;
import xueli.craftgame.renderer.world.WorldRenderer;
import xueli.craftgame.world.Dimension;
import xueli.game.renderer.NVGRenderer;
import xueli.game.utils.NVGColors;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;
import xueli.utils.io.Files;

import static org.lwjgl.nanovg.NanoVG.*;

public class StateWorld extends NVGRenderer {

	private static StateWorld INSTANCE;

	private static final String FONT_NAME = "Game";

	private TextureAtlas blocksTextureAtlas;
	private Blocks blocks;
	private Models models;

	private Player player;

	private Dimension dimension;
	private WorldRenderer worldRenderer;

	private int tex_cross;

	public StateWorld() {
		super();
		INSTANCE = this;

		this.blocksTextureAtlas = TextureAtlas.generateAtlas(
				Files.getResourcePackedInJar("textures/blocks.json").getPath(),
				Files.getResourcePackedInJar("textures/blocks/").getPath());

		nvgCreateFont(nvg, FONT_NAME, "res/fonts/Minecraft-Ascii.ttf");
		this.tex_cross = nvgCreateImage(nvg, "res/textures/hud/cross.png", NVG_IMAGE_NEAREST);

		this.models = new Models();
		this.models.init();
		this.blocks = new Blocks();
		this.blocks.init();

		this.dimension = new Dimension(true, blocks);
		this.worldRenderer = this.dimension.getRenderer();

		this.player = new Player(dimension);

		game.getDisplay().setMouseGrabbed(true);

	}

	@Override
	public void update() {
		worldRenderer.update(player);
		MatrixHelper.calculateFrustumPlane();

		player.tick();

		if (game.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE))
			game.getDisplay().setMouseGrabbed(!game.getDisplay().isMouseGrabbed());

		this.dimension.tick(player.getPos());

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
		String currentBlockInformation = "CurrentBlock: " + player.getInventory().getChosenBase().getNamespace();

		float fontSize = 15.0f * game.getDisplayScale();
		float measuredTextLength = Math.max(measureTextWidth(fontSize, posTextString),
				measureTextWidth(fontSize, fpsTextString));
		measuredTextLength = Math.max(measuredTextLength, measureTextWidth(fontSize, currentBlockInformation));

		nvgFillColor(nvg, NVGColors.TRANSPARENT_BLACK);
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, 4.0f * game.getDisplayScale() + measuredTextLength,
				8.0f * game.getDisplayScale() + 3 * fontSize);
		nvgFill(nvg);

		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, FONT_NAME);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		nvgText(nvg, 2.0f * game.getDisplayScale(), 2.0f * game.getDisplayScale(), posTextString);
		nvgText(nvg, 2.0f * game.getDisplayScale(), 4.0f * game.getDisplayScale() + fontSize, fpsTextString);
		nvgText(nvg, 2.0f * game.getDisplayScale(), 6.0f * game.getDisplayScale() + 2 * fontSize,
				currentBlockInformation);

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

		this.dimension.close();
		this.player.close();

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

	public Models getModels() {
		return models;
	}

}
