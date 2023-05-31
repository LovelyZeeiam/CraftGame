package xueli.swingx.component;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

public class SimpleLayerUI extends LayerUI<Component> {

	private static final long serialVersionUID = 2083072094679992579L;

	private float alpha = 1.0f;
	private float scale = 1.0f;

	public SimpleLayerUI() {
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		int width = c.getWidth();
		int height = c.getHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D gTemp = image.createGraphics();
		super.paint(gTemp, c);
		gTemp.dispose();

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));

		float realWidth = width * scale;
		float realHeight = height * scale;
		float realX = (width - realWidth) / 2.0f;
		float realY = (height - realHeight) / 2.0f;
		g2d.drawImage(image, (int) realX, (int) realY, (int) realWidth, (int) realHeight, null);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		float oldValue = this.alpha;
		this.alpha = alpha;
		firePropertyChange("alpha", oldValue, this.alpha);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		float oldValue = this.scale;
		this.scale = scale;
		firePropertyChange("scale", oldValue, this.alpha);
	}

}
