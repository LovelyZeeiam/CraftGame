package xueli.game2.resource.submanager.render.texture;

import java.io.IOException;

import xueli.game2.resource.manager.ResourceManager;

public interface TextureLoader {

	public int createTexture();

	public int registerTexture(TextureResourceLocation res, ResourceManager manager) throws IOException;

	public void applyTexture(int id);

	public void releaseTexture(int id);

}
