package xueli.craftgame.renderer;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.nanovg.NVGPaint;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.entitytest.item.ItemStack;
import xueli.craftgame.player.Inventory;
import xueli.game.renderer.FrameBuffer;
import xueli.game.resource.ImageResourceManager;
import xueli.game.resource.NVGImage;
import xueli.game.resource.ResourceHolder;

public class HUDInventoryRenderer implements IGameRenderer {

	public static final int INVENTORY_IMAGE_SIZE = 256;

	private HUDRenderer masterRenderer;
	private CraftGameContext ctx;
	private Inventory inventory;

	private FrameBuffer[] itemFrames = new FrameBuffer[Inventory.SLOT_COUNT];
	private int[] itemFrameNanoVGImages = new int[itemFrames.length];
	NVGPaint paint = NVGPaint.create();

	private long nvg;
	private ResourceHolder<NVGImage> texTabHolder, texChosenTabHolder;

	public HUDInventoryRenderer(HUDRenderer masterRenderer) {
		this.masterRenderer = masterRenderer;
		this.ctx = masterRenderer.getContext();
		this.inventory = ctx.getPlayer().getInventory();

	}

	@Override
	public void init() {
//		this.nvg = masterRenderer.nvg;
//
//		for (int i = 0; i < itemFrames.length; i++) {
//			itemFrames[i] = new FrameBuffer(INVENTORY_IMAGE_SIZE, INVENTORY_IMAGE_SIZE);
//			itemFrameNanoVGImages[i] = nvglCreateImageFromHandle(nvg, itemFrames[i].getTbo_image(),
//					INVENTORY_IMAGE_SIZE, INVENTORY_IMAGE_SIZE, NVG_IMAGE_NEAREST);
//		}
//
//		ImageResourceManager imageResourceManager = ctx.getResourceMaster().getImageResourceManager();
//
//		try {
//			texTabHolder = imageResourceManager.addToken("hud.inventory.tab",
//					"/assets/images/inventory/inventory_tab.png");
//		} catch (Exception e) {
//			new Exception("Could load image: /assets/images/hud/inventory_tab.png", e).printStackTrace();
//			imageResourceManager.getMissingProvider().onMissing(texTabHolder);
//		}
//
//		try {
//			texChosenTabHolder = imageResourceManager.addToken("hud.inventory.tab",
//					"/assets/images/inventory/inventory_tab_chosen.png");
//		} catch (Exception e) {
//			new Exception("Could load image: /assets/images/hud/inventory_tab_chosen.png", e).printStackTrace();
//			imageResourceManager.getMissingProvider().onMissing(texChosenTabHolder);
//		}

	}

	@Override
	public void render() {
//		if (!ctx.getViewRenderer().hasView()) {
//			for (int i = 0; i < itemFrames.length; i++) {
//				ItemStack stack = inventory.getStack(i);
//				if (stack == null)
//					continue;
//
//				itemFrames[i].use();
//				glClearColor(0, 0, 0, 0);
//				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
//				stack.getType().getRenderable().render(stack, ctx.getPlayer());
//				itemFrames[i].unbind();
//
//			}
//
//			float width = ctx.getWidth();
//			float height = ctx.getHeight();
//			float scale = ctx.getDisplayScale();
//
//			float inv_width = 364.0f * scale;
//			float inv_height = 44.0f * scale;
//			float pointer_offset = 40.0f * scale;
//			float slotSize = 34.0f * scale;
//			float firstSlotOffsetX = 5.0f * scale;
//			float slotOffsetY = 6.0f * scale;
//			float chosenSizeOffsetY = 3.0f * scale;
//
//			float inv_x = (width - inv_width) / 2.0f;
//			float inv_y = height - inv_height - chosenSizeOffsetY;
//
//			nvgBeginFrame(nvg, width, height, width / height);
//
//			nvgImagePattern(nvg, inv_x, inv_y, inv_width, inv_height, 0, texTabHolder.getResult().image(), 1, paint);
//			nvgBeginPath(nvg);
//			nvgRoundedRect(nvg, inv_x, inv_y, inv_width, inv_height, 0);
//			nvgFillPaint(nvg, paint);
//			nvgFill(nvg);
//
//			float inv_chosen_x = inv_x - chosenSizeOffsetY + inventory.getFrontInventoryChosen() * pointer_offset;
//			float inv_chosen_y = inv_y - chosenSizeOffsetY;
//			float inv_chosen_size = inv_height + chosenSizeOffsetY * 2;
//
//			nvgImagePattern(nvg, inv_chosen_x, inv_chosen_y, inv_chosen_size, inv_chosen_size, 0,
//					texChosenTabHolder.getResult().image(), 1, paint);
//			nvgBeginPath(nvg);
//			nvgRoundedRect(nvg, inv_chosen_x, inv_chosen_y, inv_chosen_size, inv_chosen_size, 0);
//			nvgFillPaint(nvg, paint);
//			nvgFill(nvg);
//
//			for (int i = 0; i < Inventory.FRONT_INVENTORY; i++) {
//				int image = itemFrameNanoVGImages[i];
//				float x = inv_x + firstSlotOffsetX + i * pointer_offset;
//				float y = inv_y + slotOffsetY;
//
//				nvgImagePattern(nvg, x, y, slotSize, slotSize, 0, image, 1, paint);
//				nvgBeginPath(nvg);
//				nvgRoundedRect(nvg, x, y, slotSize, slotSize, 0);
//				nvgFillPaint(nvg, paint);
//				nvgFill(nvg);
//
//			}
//
//			nvgEndFrame(nvg);
//
//		}

	}

	@Override
	public void release() {
		for (FrameBuffer itemFrame : itemFrames) {
			itemFrame.delete();
		}

	}

}
