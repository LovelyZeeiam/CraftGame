package xueli.linotebook.noteeditor;

import javax.swing.JTextPane;

public class NoteEditorComponent extends JTextPane {
	
	private static final long serialVersionUID = -2999695590126017406L;
	
	private final NoteOptions options = new NoteOptions();
	
	public NoteEditorComponent() {
		setEditorKit(new NoteEditorKit(options));
		
	}
	
	
}
