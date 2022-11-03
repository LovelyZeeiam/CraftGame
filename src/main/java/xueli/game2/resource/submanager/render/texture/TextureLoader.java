package xueli.game2.resource.submanager.render.texture;

import xueli.game2.resource.manager.ResourceManager;

import java.io.IOException;

public interface TextureLoader {

	public int createTexture();

	public int registerTexture(TextureResourceLocation res, ResourceManager manager) throws IOException;

	public void applyTexture(int id);

	public void releaseTexture(int id);

}
