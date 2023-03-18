package xueli.linotebook.noteeditor;

import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

public class NoteEditorKit extends StyledEditorKit {
	
	private static final long serialVersionUID = -1603330480270304081L;
	
	private static final NoteViewFactory DEFAULT_VIEW_FACTORY = new NoteViewFactory();
	
	@Override
	public void install(JEditorPane c) {
		super.install(c);
	}
	
	@Override
	public Document createDefaultDocument() {
		return new NoteDocument();
	}
	
	@Override
	public ViewFactory getViewFactory() {
		return DEFAULT_VIEW_FACTORY;
	}
	
	@Override
	public void deinstall(JEditorPane c) {
		super.deinstall(c);
	}
	
}
