package xueli.craftgame.state;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFont;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImage;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.glfw.GLFW;

import xueli.craftgame.entity.Player;
import xueli.craftgame.init.Blocks;
import xueli.craftgame.init.Models;
import xueli.craftgame.inventory.InventoryRenderer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.world.Dimension;
import xueli.game.renderer.NVGRenderer;
import xueli.game.utils.NVGColors;
import xueli.game.utils.Time;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;
import xueli.utils.io.Files;

public class StateWorld extends NVGRenderer {

	private static StateWorld INSTANCE;

	private static final String FONT_NAME = "Game";

	private TextureAtlas blocksTextureAtlas;
	private Blocks blocks;
	private Models models;

	private Player player;
	private InventoryRenderer inventoryRenderer;

	private Dimension dimension;
	private WorldRenderer worldRenderer;

	private int tex_cross;

	public static String savePath = ".cg/saves/Test/";

	public StateWorld() {
		super();
		INSTANCE = this;

		this.blocksTextureAtlas = TextureAtlas.generateAtlas(
				Files.getResourcePackedInJar("textures/blocks/atlas_definition.json").getPath(),
				Files.getResourcePackedInJar("textures/blocks/").getPath());

		nvgCreateFont(nvg, FONT_NAME, "res/fonts/Minecraft-Ascii.ttf");
		this.tex_cross = nvgCreateImage(nvg, "res/textures/hud/cross.png", NVG_IMAGE_NEAREST);

		this.models = new Models();
		this.models.init();

		this.blocks = new Blocks();
		this.blocks.init();

		this.dimension = new Dimension(blocks);
		this.worldRenderer = new WorldRenderer(dimension);

		this.player = new Player(dimension);
		this.inventoryRenderer = new InventoryRenderer(this.player.getInventory());

		game.getDisplay().setMouseGrabbed(true);

	}

	@Override
	public void update() {
		worldRenderer.update(player);
		MatrixHelper.calculateFrustumPlane();

		player.tick();
		this.inventoryRenderer.update();

		if (game.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE))
			game.getDisplay().setMouseGrabbed(!game.getDisplay().isMouseGrabbed());

		this.dimension.tick(player.getPos());

	}

	@Override
	public void size() {
		super.size();

		worldRenderer.size();
		inventoryRenderer.size();

	}

	@Override
	public void stroke() {
		String posTextString = "Position: " + (int) Math.floor(player.getPos().x) + ", "
				+ (int) Math.floor(player.getPos().y) + ", " + (int) Math.floor(player.getPos().z);
		String fpsTextString = "FPS: " + Time.fps;

		float fontSize = 15.0f * game.getDisplayScale();
		float measuredTextLength = Math.max(measureTextWidth(fontSize, posTextString),
				measureTextWidth(fontSize, fpsTextString));

		nvgFillColor(nvg, NVGColors.TRANSPARENT_BLACK);
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, 4.0f * game.getDisplayScale() + measuredTextLength,
				8.0f * game.getDisplayScale() + 2 * fontSize);
		nvgFill(nvg);

		nvgFontSize(nvg, fontSize);
		nvgFontFace(nvg, FONT_NAME);
		nvgFillColor(nvg, NVGColors.WHITE);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
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
		worldRenderer.render(blocksTextureAtlas, player);

		super.render();
		inventoryRenderer.render();

	}

	@Override
	public void release() {
		super.release();
		blocksTextureAtlas.release();

		worldRenderer.release();

		this.dimension.close();
		this.player.close();
		inventoryRenderer.release();

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
