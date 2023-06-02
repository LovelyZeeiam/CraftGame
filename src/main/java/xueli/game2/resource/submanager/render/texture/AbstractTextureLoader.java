package xueli.game2.resource.submanager.render.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import xueli.game2.resource.Resource;
import xueli.game2.resource.manager.ResourceManager;
import xueli.registry.Identifier;

public abstract class AbstractTextureLoader implements TextureLoader {

	@Override
	public Texture registerTexture(Identifier res, ResourceManager manager) throws IOException {
		Resource resource = manager.getResource(res);
		InputStream in = resource.openInputStream();
		BufferedImage image = ImageIO.read(in);
		return registerTexture(image);
	}

	public abstract Texture registerTexture(BufferedImage image);

}
