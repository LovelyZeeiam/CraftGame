package xueli.game2.resource.submanager.render.texture;

import java.io.IOException;

import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.manager.ResourceManager;

public interface TextureLoader {

	public Texture registerTexture(ResourceLocation res, ResourceManager manager) throws IOException;

	public void releaseTexture(Texture id);

}
