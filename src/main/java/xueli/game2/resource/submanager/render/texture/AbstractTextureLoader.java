package xueli.game2.resource.submanager.render.texture;

import xueli.game2.resource.Resource;
import xueli.game2.resource.manager.ResourceManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractTextureLoader implements TextureLoader {

	@Override
	public int registerTexture(TextureResourceLocation res, ResourceManager manager) throws IOException {
		Resource resource = manager.getResource(res.location());
		InputStream in = resource.openInputStream();
		BufferedImage image = ImageIO.read(in);
		return registerTexture(image);
	}

	abstract int registerTexture(BufferedImage image) throws IOException;

}
