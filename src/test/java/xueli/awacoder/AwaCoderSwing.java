package xueli.awacoder;

import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;

public class AwaCoderSwing {

	private JFrame frmAwacoder;

	/**
	 * Create the application.
	 */
	public AwaCoderSwing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAwacoder = new JFrame();
		frmAwacoder.setTitle("AwaCoder");
		frmAwacoder.setBounds(100, 100, 584, 493);
		frmAwacoder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmAwacoder.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmFileNew = new JMenuItem("New");
		mntmFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		mnFile.add(mntmFileNew);

		JMenuItem mntmFileOpen = new JMenuItem("Open");
		mntmFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		mnFile.add(mntmFileOpen);

		JMenuItem mntmFileSave = new JMenuItem("Save");
		mntmFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mnFile.add(mntmFileSave);

		JMenuItem mntmFileClose = new JMenuItem("Close");
		mntmFileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK));
		mnFile.add(mntmFileClose);

		JMenuItem mntmFileQuit = new JMenuItem("Quit");
		mnFile.add(mntmFileQuit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenu mnTool = new JMenu("Tool");
		menuBar.add(mnTool);

		JMenuItem mntmToolOptions = new JMenuItem("Options");
		mnTool.add(mntmToolOptions);

		JMenuItem mntmToolWelcome = new JMenuItem("Welcome");
		mnTool.add(mntmToolWelcome);

		JMenuItem mntmToolPlugins = new JMenuItem("Plugins");
		mnTool.add(mntmToolPlugins);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHelpAboutQT = new JMenuItem("About QT");
		mntmHelpAboutQT.addActionListener(e -> AboutQTDialog.INSTANCE.setVisible(true));
		mnHelp.add(mntmHelpAboutQT);

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AwaCoderSwing window = new AwaCoderSwing();
					window.frmAwacoder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
