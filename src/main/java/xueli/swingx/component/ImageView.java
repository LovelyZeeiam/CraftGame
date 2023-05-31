package xueli.swingx.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImageView extends JComponent {

	private static final long serialVersionUID = 3405064847459096540L;

	private BufferedImage image;
	private boolean rounded = false;
	private ImageRenderer renderer;

	private Dimension preferredSize;

	public ImageView(BufferedImage image) {
		this(image, false);
	}

	public ImageView(BufferedImage image, boolean rounded) {
		this(image, rounded, StretchMode.UNIFORM_FILL.renderer);
	}

	public ImageView(BufferedImage image, boolean rounded, StretchMode stretchMode) {
		this(image, rounded, stretchMode.renderer);
	}

	public ImageView(BufferedImage image, boolean rounded, ImageRenderer renderer) {
		this.image = image;
		this.rounded = rounded;
		this.renderer = renderer;
		this.setDoubleBuffered(true);

	}

	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	public void setRounded(boolean rounded) {
		this.rounded = rounded;
		repaint();
	}

	public void setRenderer(ImageRenderer renderer) {
		this.renderer = renderer;
		repaint();
	}

	public void setRenderer(StretchMode mode) {
		this.setRenderer(mode.renderer);
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		this.preferredSize = preferredSize;
//		System.out.println(this.isValid());
	}

	@Override
	public Dimension getPreferredSize() {
		if (this.preferredSize != null)
			return this.preferredSize;
		return this.renderer.getPreferredSize(image, rounded);
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

//		System.out.println(width + ", " + height);

		Graphics2D g2d = (Graphics2D) g;
		this.renderer.drawImage(g2d, image, width, height, rounded);

	}

	public static enum StretchMode {
		// Stretch the image fully to the size
		STRETCH(new ImageRenderer() {

			@Override
			public void drawImage(Graphics2D g2d, BufferedImage image, int parentWidth, int parentHeight,
					boolean round) {
				if (round) {
					g2d.setClip(new Ellipse2D.Double(0, 0, parentWidth, parentHeight));
				}
				g2d.drawImage(image, 0, 0, parentWidth, parentHeight, null);

			}

		}),

		// Fill the image to avoid the empty area in the long side
		FILL(new ImageRenderer() {

			@Override
			public Dimension getPreferredSize(BufferedImage image, boolean round) {
				int max = Math.max(image.getWidth(), image.getHeight());
				return new Dimension(max, max);
			}

			@Override
			public void drawImage(Graphics2D g2d, BufferedImage image, int parentWidth, int parentHeight,
					boolean round) {
				int offsetX = 0, offsetY = 0;

				int imageWidth = image.getWidth();
				int imageHeight = image.getHeight();
				double ratio = (double) imageWidth / imageHeight;

				if (parentWidth >= parentHeight) {
					imageHeight = parentHeight;
					imageWidth = (int) (parentHeight / ratio);
					offsetY = (parentHeight - imageHeight) / 2;
				} else {
					imageHeight = parentHeight;
					imageWidth = (int) (parentHeight * ratio);
					offsetX = (parentWidth - imageWidth) / 2;
				}
				g2d.drawImage(image, offsetX, offsetY, imageWidth, imageHeight, null);
			}

		}),

		// Fill the image to avoid the empty area in the short side
		UNIFORM_FILL(new ImageRenderer() {

			@Override
			public Dimension getPreferredSize(BufferedImage image, boolean round) {
				int max = Math.max(image.getWidth(), image.getHeight());
				return new Dimension(max, max);
			}

			@Override
			public void drawImage(Graphics2D g2d, BufferedImage image, int parentWidth, int parentHeight,
					boolean round) {
				int offsetX = 0, offsetY = 0;

				int imageWidth = image.getWidth();
				int imageHeight = image.getHeight();
				double ratio = (double) imageWidth / imageHeight;

				if (parentWidth >= parentHeight) {
					imageHeight = parentHeight;
					imageWidth = (int) (parentHeight * ratio);
					offsetX = (parentWidth - imageWidth) / 2;
				} else {
					imageWidth = parentWidth;
					imageHeight = (int) (parentWidth / ratio);
					offsetY = (parentHeight - imageHeight) / 2;
				}

				if (round) {
					g2d.setClip(new Ellipse2D.Double(offsetX, offsetY, imageWidth, imageHeight));
				}
				g2d.drawImage(image, offsetX, offsetY, imageWidth, imageHeight, null);

			}

		}),

		CENTER_NO_STRETCH(new ImageRenderer() {

			@Override
			public Dimension getPreferredSize(BufferedImage image, boolean round) {
				int max = Math.max(image.getWidth(), image.getHeight());
				return new Dimension(max, max);
			}

			@Override
			public void drawImage(Graphics2D g2d, BufferedImage image, int parentWidth, int parentHeight,
					boolean round) {
				int imageWidth = image.getWidth();
				int imageHeight = image.getHeight();
				int offsetX = (parentWidth - imageWidth) / 2;
				int offsetY = (parentHeight - imageHeight) / 2;
				if (round) {
					g2d.setClip(new Ellipse2D.Double(offsetX, offsetY, imageWidth, imageHeight));
				}
				g2d.drawImage(image, offsetX, offsetY, imageWidth, imageHeight, null);
			};

		});

		ImageRenderer renderer;

		private StretchMode(ImageRenderer renderer) {
			this.renderer = renderer;
		}

	}

	public static interface ImageRenderer {

		default public Dimension getPreferredSize(BufferedImage image, boolean round) {
			return new Dimension(image.getWidth(), image.getHeight());
		}

		public void drawImage(Graphics2D g2d, BufferedImage image, int parentWidth, int parentHeight, boolean round);

	}

}
