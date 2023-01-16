package xueli.clock.component;

import javax.swing.JPanel;

import xueli.clock.ClockFrame;
import xueli.game2.resource.ResourceLocation;
import xueli.swingx.component.ImageView;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

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
		
		JLabel lblUsername = new JLabel("LovelyZeeiam");
		lblUsername.setFont(new Font("Sitka Display", Font.PLAIN, 12));
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblUsername);
		
	}

}
