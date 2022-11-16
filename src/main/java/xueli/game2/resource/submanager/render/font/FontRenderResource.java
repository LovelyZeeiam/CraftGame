package xueli.game2.resource.submanager.render.font;

import xueli.game2.renderer.ui.MyGui;
import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceHelper;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.ResourceManager;
import xueli.game2.resource.submanager.render.BufferUtils;
import xueli.game2.resource.submanager.render.RenderResource;

import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;

public class FontRenderResource extends RenderResource<ResourceLocation, Integer> {

	private final MyGui ctx;

	public FontRenderResource(MyGui ctx, ChainedResourceManager superiorManager) {
		super(superiorManager);
		this.ctx = ctx;

	}

	@Override
	protected Integer doRegister(ResourceLocation k, boolean must) {
		ResourceManager manager = getUpperResourceManager();
		long nvg = ctx.getContext();
		
		try {
			Resource res = manager.getResource(k);
			byte[] bytes = ResourceHelper.readAll(res);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
			buffer.put(bytes);
			buffer.flip();
			
			int id = nvgCreateFontMem(nvg, k.location(), buffer, 0);
			
			if(id < 0) {
				throw new Exception("Can't register font: " + k);
			}
			
			return id;
			
		} catch (Exception e) {
			if (must)
				throw new RuntimeException(e);
			else {
				e.printStackTrace();
				return -1;
			}
		}
	}

	@Override
	protected void close(ResourceLocation k, Integer v) {
	}

}
