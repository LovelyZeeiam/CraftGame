package xueli.game2.resource.submanager.render.texture;

import java.io.IOException;

import xueli.game2.resource.manager.ResourceManager;
import xueli.registry.Identifier;

public interface TextureLoader {

	public Texture registerTexture(Identifier res, ResourceManager manager) throws IOException;

	public void releaseTexture(Texture id);

}
