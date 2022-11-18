package xueli.mcremake.classic.client.gui;

import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.texture.NvgImageFlag;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureTypeNanoVG;
import xueli.mcremake.classic.client.CraftGameClient;

public class UniversalGui implements ResourceHolder {

	public static final ResourceLocation FONT_RESOURCE_LOCATION = new ResourceLocation("minecraft", "font/default.ttf");
	public static final TextureResourceLocation UNIVERSAL_BACKGROUND_RESOURCE_LOCATION = new TextureResourceLocation(new ResourceLocation("minecraft", "gui/background.png"), new TextureTypeNanoVG(NvgImageFlag.REPEAT_X, NvgImageFlag.REPEAT_Y));

	private int gameFontId;
	private int texUniversalBgId;

	private final CraftGameClient ctx;

	public UniversalGui(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	public void init() {
		this.reload();

	}

	@Override
	public void reload() {
		this.gameFontId = ctx.getFontResource().register(FONT_RESOURCE_LOCATION, true);
		this.texUniversalBgId = ctx.getTextureRenderResource().register(UNIVERSAL_BACKGROUND_RESOURCE_LOCATION, false);

	}

	public void release() {
	}

	public int getGameFontId() {
		return gameFontId;
	}

	public int getTextureUniversalBgId() {
		return texUniversalBgId;
	}

}
