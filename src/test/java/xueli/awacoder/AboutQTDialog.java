package xueli.awacoder;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;

public class AboutQTDialog extends JDialog {
	
	public static final AboutQTDialog INSTANCE = new AboutQTDialog();
	
	private static final long serialVersionUID = 6844474224353883237L;
	
	/**
	 * Create the dialog.
	 */
	public AboutQTDialog() {
		setType(Type.POPUP);
		setTitle("About Qt");
		setResizable(false);
		setPreferredSize(new Dimension(600, 546));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBackground(UIManager.getColor("Button.light"));
		getContentPane().add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(e -> this.setVisible(false));
		btnOK.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelBottom.add(btnOK);
		
		JPanel panelLeft = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelLeft.getLayout();
		flowLayout_1.setVgap(10);
		flowLayout_1.setHgap(10);
		getContentPane().add(panelLeft, BorderLayout.WEST);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(AboutQTDialog.class.getResource("/assets/awacoder/about.png")));
		panelLeft.add(lblIcon);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAboutContent = new JLabel("<html>\r\n<b>About Qt</b>\r\n<p></p>\r\n<p>This program uses Qt version 6.4.0.</p>\r\n<p></p>\r\n<p>Qt is a C++ toolkit for cross-platform application development.</p>\r\n<p></p>\r\n<p>Qt provides single-source portability across all major desktop operating systems. I is also available for embedded Linux and other embedded and mobile operating systems.</p>\r\n<p></p>\r\n<p>Qt is available under multiple licensing options designed to accommodate the needs of our various users.</p>\r\n<p></p>\r\n<p>Qt licensed under our commercial license agreement is appropriate for development of proprietary/commercial software where you do not want to share any source code with third parties or otherwise cannot comply with the terms of GNU (L)GPL.</p>\r\n<p></p>\r\n<p>Qt licensed under GNU (L)GPL is appropriate for the development of Qt applications provided you can comply with the terms and conditions of the respective licenses.</p>\r\n<p></p>\r\n<p>Please see <a>qt.io/licensing</a> for an overview of Qt licensing.</p>\r\n<p></p>\r\n<p>Copyright (C) 2022 The Qt Company Ltd and other contributors.</p>\r\n<p></p>\r\n<p>Qt and the Qt logo are trademarks of the Qt Company Ltd.</p>\r\n<p></p>\r\n<p>Qt is The Qt Company Ltd product developed as an open source project. See <a>qt.io</a> for more information.</p>\r\n</html>");
		lblAboutContent.setVerticalAlignment(SwingConstants.TOP);
		lblAboutContent.setHorizontalAlignment(SwingConstants.LEFT);
		panelCenter.add(lblAboutContent);
		
		this.pack();
		
		
	}

}
