package xueli.craftgame.resource.render.texture;

import java.io.IOException;

import xueli.craftgame.resource.manager.ResourceManager;

public interface TextureLoader {

	public int registerTexture(TextureResourceLocation res, ResourceManager manager) throws IOException;

	public void applyTexture(int id);

	public void releaseTexture(int id);

}
