package xueli.game2.resource.render.font;

import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgFontFaceId;

import java.nio.ByteBuffer;

import xueli.game2.renderer.ui.NanoVGContext;
import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceHelper;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.ResourceManager;
import xueli.game2.resource.render.BufferUtils;
import xueli.game2.resource.render.RenderResource;

public class FontRenderResource extends RenderResource<ResourceLocation, Integer> {

	public FontRenderResource(ChainedResourceManager superiorManager) {
		super(superiorManager);
	}

	@Override
	protected Integer register(ResourceLocation k, boolean must) {
		ResourceManager manager = getUpperResourceManager();
		long nvg = NanoVGContext.INSTANCE.getNvg();
		
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
	protected void bind(ResourceLocation k, Integer v) {
		nvgFontFaceId(NanoVGContext.INSTANCE.getNvg(), v);
	}

	@Override
	protected void close(ResourceLocation k, Integer v) {
	}

}
