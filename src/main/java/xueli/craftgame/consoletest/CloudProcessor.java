package xueli.craftgame.consoletest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CloudProcessor {

	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("res/textures/environment/clouds.png"));
		
		for(int i = 0; i < image.getWidth();i++) {
			for(int j = 0; j < image.getHeight();j++) {
				int rgb = image.getRGB(i, j);

				int alpha = rgb >> 24;
				System.out.println(alpha);
				
			}
		}

	}

}
