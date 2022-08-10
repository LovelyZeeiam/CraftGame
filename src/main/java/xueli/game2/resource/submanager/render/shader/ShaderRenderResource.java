package xueli.game2.resource.submanager.render.shader;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceHelper;
import xueli.game2.resource.manager.ChainedResourceManager;
import xueli.game2.resource.manager.ResourceManager;
import xueli.game2.resource.submanager.render.RenderResource;

public class ShaderRenderResource extends RenderResource<ShaderResourceLocation, Shader> {
	
	public ShaderRenderResource(ChainedResourceManager superiorManager) {
		super(superiorManager);
	}

	@Override
	protected Shader register(ShaderResourceLocation k, boolean must) {
		ResourceManager manager = getUpperResourceManager();
		try {
			Resource vertResource = manager.getResource(k.vert());
			String vertCode = ResourceHelper.readAllToString(vertResource);
			
			Resource fragResource = manager.getResource(k.frag());
			String fragCode = ResourceHelper.readAllToString(fragResource);
			
			return Shader.getShader(vertCode, fragCode);
		} catch (Exception e) {
			if (must)
				throw new RuntimeException(e);
			else {
				e.printStackTrace();
				return Shader.EMPTY_SHADER;
			}
		}
	}
	
	@Override
	protected void bind(ShaderResourceLocation k, Shader v) {
		v.bind();
	}

	@Override
	protected void close(ShaderResourceLocation k, Shader v) {
		v.release();
	}

}
