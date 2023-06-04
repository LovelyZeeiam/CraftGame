package xueli.clock.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.event.SwingPropertyChangeSupport;

public class BatteryInfoPanel extends JPanel {

	private static final long serialVersionUID = -438587967007006520L;

	private BatteryInfoBean bean = new BatteryInfoBean();

	/**
	 * Create the panel.
	 */
	public BatteryInfoPanel() {
		setLayout(new BorderLayout(0, 0));

		BatteryIconPanel iconPanel = new BatteryIconPanel(bean);
		iconPanel.setFont(new Font("Cascadia Code", Font.PLAIN, 12));
		add(iconPanel, BorderLayout.CENTER);

	}

	public void setBean(boolean isCharging, double remainPercentage) {
		this.bean.setPowerCharging(isCharging);
		this.bean.setPowerRemain(remainPercentage);
		this.revalidate();
		this.repaint();

	}

	public void setColor(Color color) {
		this.bean.setColor(color);
		this.revalidate();
		this.repaint();

	}

}

class BatteryInfoBean {

	private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this, true);

	private boolean isPowerCharging = true;
	private double powerRemain = 100.0;

	private Color color = new Color(0.6f, 0.6f, 0.6f);

	public void setPowerCharging(boolean isPowerCharging) {
		pcs.firePropertyChange("charge", this.isPowerCharging, isPowerCharging);
		this.isPowerCharging = isPowerCharging;
	}

	public boolean isPowerCharging() {
		return isPowerCharging;
	}

	public void setPowerRemain(double powerRemain) {
		pcs.firePropertyChange("remain", this.powerRemain, powerRemain);
		this.powerRemain = powerRemain;
	}

	public double getPowerRemain() {
		return powerRemain;
	}

	public void setColor(Color color) {
		pcs.firePropertyChange("color", this.color, color);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

}

class BatteryIconPanel extends Component {

	private static final long serialVersionUID = 5139235281076605426L;
//	private static final double magicNumber = Math.log(2);

	private final BatteryInfoBean bean;

	public BatteryIconPanel(BatteryInfoBean bean) {
		this.bean = bean;

	}

	@Override
	public void paint(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();

		int min = Math.min(width, height);
		int borderSize = (int) Math.log(min);
		int borderSpace = (int) (borderSize);

		int fontSize = (int) (0.6 * min);
		String contentString = String.format("%d%%", (int) bean.getPowerRemain());
		if (bean.isPowerCharging()) {
			contentString = contentString + " ⚡";
		}

		Font font = getFont().deriveFont((float) fontSize);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle2D strBounds = metrics.getStringBounds(contentString, g);
		int fontHeight = (int) strBounds.getHeight();
		int fontWidth = (int) strBounds.getWidth();
		int baseLineYOffset = metrics.getAscent();

//		System.out.println(fontSize + ", " + fontHeight);

		g.setColor(bean.getColor());
		if (width > height) {
			if (borderSize > 0) {
				int insideBarStart = borderSpace + borderSize;
				int insideBarHeight = height - insideBarStart * 2;
				int insideBarWidth = (int) ((width - borderSize - insideBarStart * 2) * bean.getPowerRemain() / 100.0);

				int temp, temp2;
				g.fillRect(0, 0, temp = width - 2 * borderSize, borderSize);
				g.fillRect(temp, 0, borderSize, temp2 = height - borderSize);
				g.fillRect(borderSize, temp2, temp, borderSize);
				g.fillRect(0, borderSize, borderSize, temp2);

				int rightLittleThingHeight = (int) Math.ceil(height * 0.314);
				int rightLittleThingSpace;
				do {
					rightLittleThingHeight--;
					rightLittleThingSpace = (height - rightLittleThingHeight);
				} while (rightLittleThingSpace % 2 != 0);
				int rightLittleThingYStart = rightLittleThingSpace / 2;
				g.fillRect(width - borderSize, rightLittleThingYStart, borderSize, rightLittleThingHeight);

				g.fillRect(insideBarStart, insideBarStart, insideBarWidth, insideBarHeight);

				g.setColor(bean.getColor().brighter());
				int fontStartX = (width - borderSize - fontWidth) / 2;
				int fontStartY = (height - fontHeight) / 2 + baseLineYOffset;
				g.drawString(contentString, fontStartX, fontStartY);

			} else {
				int insideBarWidth = (int) (width * bean.getPowerRemain() / 100.0);
				g.fillRect(0, 0, insideBarWidth, height);
			}
		} else {
			if (borderSize > 0) {
				int insideBarStart = borderSpace + borderSize;
				int insideBarWidth = width - insideBarStart * 2;
				int insideBarHeight = (int) ((height - borderSize - insideBarStart * 2) * bean.getPowerRemain()
						/ 100.0);

				int temp, temp2;
				g.fillRect(0, borderSize, temp = width - borderSize, borderSize);
				g.fillRect(temp, borderSize, borderSize, temp2 = height - borderSize * 2);
				g.fillRect(borderSize, height - borderSize, temp, borderSize);
				g.fillRect(0, borderSize * 2, borderSize, temp2);

				int topLittleThingWidth = (int) Math.ceil(width * 0.314);
				int rightLittleThingSpace;
				do {
					topLittleThingWidth--;
					rightLittleThingSpace = (width - topLittleThingWidth);
				} while (rightLittleThingSpace % 2 != 0);
				int rightLittleThingXStart = rightLittleThingSpace / 2;
				g.fillRect(rightLittleThingXStart, 0, topLittleThingWidth, borderSize);

				g.fillRect(insideBarStart, insideBarStart + borderSize, insideBarWidth, insideBarHeight);

				g.setColor(bean.getColor().brighter());
				int fontStartX = (width - fontWidth) / 2;
				int fontStartY = (height + borderSize - fontHeight) / 2 + baseLineYOffset;
				g.drawString(contentString, fontStartX, fontStartY);

			} else {
				int insideBarHeight = (int) (height * bean.getPowerRemain() / 100.0);
				g.fillRect(0, 0, width, insideBarHeight);
			}
		}

	}

}
