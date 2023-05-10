package xueli.linotebook.noteeditor;

import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleContext;

public class NoteDocument extends DefaultStyledDocument {
	
	private static final long serialVersionUID = -5639127919391346108L;

	public NoteDocument() {
	}

	public NoteDocument(StyleContext styles) {
		super(styles);
	}

	public NoteDocument(Content c, StyleContext styles) {
		super(c, styles);
	}
	
	@Override
	protected AbstractElement createDefaultRoot() {
		return super.createDefaultRoot();
	}
	
	@Override
	protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
		super.insertUpdate(chng, attr);
	}
	
	
}
