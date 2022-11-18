package xueli.game2.resource.submanager.render.texture;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.manager.ResourceManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractTextureLoader implements TextureLoader {

	@Override
	public int registerTexture(ResourceLocation res, ResourceManager manager) throws IOException {
		Resource resource = manager.getResource(res);
		InputStream in = resource.openInputStream();
		BufferedImage image = ImageIO.read(in);
		return registerTexture(image);
	}

	public abstract int registerTexture(BufferedImage image) throws IOException;

}
