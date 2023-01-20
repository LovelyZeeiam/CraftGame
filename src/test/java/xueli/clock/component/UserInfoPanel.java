package xueli.clock.component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import xueli.clock.ClockFrame;
import xueli.game2.resource.ResourceLocation;
import xueli.swingx.component.ImageView;

public class UserInfoPanel extends JPanel {
	
	private static final long serialVersionUID = -2141828020915382762L;

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public UserInfoPanel() {
//		setBackground(Color.BLACK);
		setOpaque(false);
		
		ImageView lblUserIcon = null;
		try {
			lblUserIcon = new ImageView(ImageIO.read(ClockFrame.RESOURCE_PROVIDER.getResource(new ResourceLocation("clock", "images/user_icon.jpg")).openInputStream()), true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		lblUserIcon.setPreferredSize(new Dimension(30, 30));
		add(lblUserIcon);
		
		JLabel label = new JLabel("LovelyZeeiam");
		add(label);
		
	}

}
