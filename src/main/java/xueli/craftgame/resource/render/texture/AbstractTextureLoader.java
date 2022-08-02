package xueli.craftgame.resource.render.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import xueli.craftgame.resource.Resource;
import xueli.craftgame.resource.manager.ResourceManager;

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
