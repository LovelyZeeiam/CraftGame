package xueli.linotebook.noteeditor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class NoteEditorPanel extends JPanel {

	private static final long serialVersionUID = 427175429931530793L;
	
	/**
	 * Create the panel.
	 */
	public NoteEditorPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		NoteEditorComponent panel = new NoteEditorComponent();
		scrollPane.setViewportView(panel);
		
	}

}
