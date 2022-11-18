package xueli.game2.resource.submanager.render.texture;

import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.manager.ResourceManager;

import java.io.IOException;

public interface TextureLoader {

	public int createTexture();

	public int registerTexture(ResourceLocation res, ResourceManager manager) throws IOException;

	public void releaseTexture(int id);

}
