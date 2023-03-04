package xueli.mcremake.registry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.provider.ClassLoaderResourceProvider;

public class MojanglesFontWidthGenerator {

	public static void main(String[] args) throws IOException {
		ClassLoaderResourceProvider resourceManager = new ClassLoaderResourceProvider();
		Resource resource = resourceManager.getResource(new ResourceLocation("minecraft", "font/default.png"));
		InputStream in = resource.openInputStream();
		BufferedImage image = ImageIO.read(in);
		in.close();
		
		int[] widths = new int[256];
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				one_image_search: for(int k = 7; k >= 0; k--) {
					widths[i * 16 + j] = k;
					for(int l = 0; l < 8; l++) {
						int x = j * 8 + k;
						int y = i * 8 + l;
//						System.out.println(x + ", " + y);
						if(image.getRGB(x, y) != 0) {
							break one_image_search;
						}
					}
				}
			}
		}
		
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				System.out.print((widths[i * 16 + j] + 1) + ", ");
			}
			System.out.println();
		}
		
	}

}
