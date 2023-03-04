package xueli.linotebook;

import java.awt.EventQueue;

import javax.swing.JFrame;

import xueli.linotebook.noteeditor.NoteEditorPanel;

import java.awt.BorderLayout;

// LEARN: We can seemingly get clip bound of a Graphic instance to know where we need to repaint
public class LiNotebookUI {

	private JFrame frmLinotepad;

	/**
	 * Create the application.
	 */
	public LiNotebookUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLinotepad = new JFrame();
		frmLinotepad.setTitle("LiNotepad");
		frmLinotepad.setBounds(100, 100, 558, 475);
		frmLinotepad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		NoteEditorPanel panel = new NoteEditorPanel();
		frmLinotepad.getContentPane().add(panel, BorderLayout.CENTER);
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LiNotebookUI window = new LiNotebookUI();
					window.frmLinotepad.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
