package xueli.swingx.component;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

// TODO: is working now
@Deprecated
public class SimpleLayerUI extends LayerUI<Component> {
	
	private static final long serialVersionUID = 2083072094679992579L;
	
//	private float alpha = 1.0f;
	
	public SimpleLayerUI() {
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
//		BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//		Graphics2D gTemp = image.createGraphics();
//		super.paint(gTemp, c);
//		gTemp.dispose();
//		
//		WritableRaster alphaImage = image.getAlphaRaster();
//		image.getRGB(0, 0, 0, 0, null, 0, 0);
		
	}
	
}
