package xueli.linotebook.noteeditor;

import java.util.Vector;

import javax.swing.JEditorPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

@SuppressWarnings("unused")
public class NoteEditorKit extends StyledEditorKit {

	private static final long serialVersionUID = -1603330480270304081L;

	private final NoteOptions options;
	private final NoteEditorViewFactory viewFactory;
	private JEditorPane _ctx;

	public NoteEditorKit(NoteOptions options) {
		this.options = options;
		this.viewFactory = new NoteEditorViewFactory(options);

	}

	@Override
	public Document createDefaultDocument() {
		return new NoteEditorDocument();
	}

	@Override
	public void install(JEditorPane c) {
		this._ctx = c;
		super.install(c);

	}

	@Override
	public void deinstall(JEditorPane c) {
		this._ctx = null;
		super.deinstall(c);

	}

	@Override
	public ViewFactory getViewFactory() {
		return this.viewFactory;
	}

}

class NoteEditorDocument extends DefaultStyledDocument {

	private static final long serialVersionUID = 690133246686855290L;

	public NoteEditorDocument() {
		super();

	}

	@Override
	protected AbstractElement createDefaultRoot() {
		writeLock();
		BranchElement root = new SectionElement();
		BranchElement block = new BranchElement(root, null);

		LeafElement brk = new LeafElement(block, null, 0, 1);
		Element[] buff = new Element[1];
		buff[0] = brk;
		block.replace(0, 0, buff);

		buff[0] = block;
		root.replace(0, 0, buff);

		writeUnlock();
		return root;
	}

	@Override
	protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
//		int offset = chng.getOffset();
//		int length = chng.getLength();
//		if (attr == null) {
//			attr = SimpleAttributeSet.EMPTY;
//		}
//
//		int endOffset = offset + length;
//
//		// Paragraph attributes should come from point after insertion.
//		// You really only notice this when inserting at a paragraph
//		// boundary.
//		Element paragraph = getParagraphElement(endOffset);
//		AttributeSet pattr = paragraph.getAttributes();
//		// Character attributes should come from actual insertion point.
//		Element pParagraph = getParagraphElement(offset);
//		Element run = pParagraph.getElement(pParagraph.getElementIndex(offset));
//		AttributeSet cattr = run.getAttributes();
//
//		boolean insertingAtBoundry = (run.getEndOffset() == endOffset);
//		
//		Segment s = new Segment();
//		try {
//			this.getText(offset, length, s);
//			char[] cs = s.array;
//			boolean isLastCharEnter = cs[cs.length - 1] == '\n';
//			
//			Vector<ElementEdit> edits = new Vector<>();
//			int lastTimeEnter = 0;
//			Element lastNewLineElement = null;
//			
//			for(int i = 0; i < cs.length; i++) {
//				switch(cs[i]) {
//				case '\n' -> {
//					int thisLineEnds = i - 1;
//					
//					Element[] removedElements = { pParagraph };
//					Element[] addedElements = new Element[1];
//					if(i != cs.length - 1) {
//						addedElements[0] = new BranchElement(pParagraph.getParentElement(), pattr);
//						
//					}
//					
//					lastTimeEnter = i;
//				}
//				default -> {
//					
//				}
//				}
//				
//			}
//			
//		} catch (BadLocationException bl) {
//			bl.printStackTrace();
//        }
		
		
	}

}

@SuppressWarnings("unused")
class NoteEditorViewFactory implements ViewFactory {
	
	private final NoteOptions options;

	public NoteEditorViewFactory(NoteOptions options) {
		this.options = options;
	}

	@Override
	public View create(Element elem) {
		String kind = elem.getName();
		System.out.println(kind);
		return switch (kind) {
		case AbstractDocument.ContentElementName -> new LabelView(elem);
		case AbstractDocument.ParagraphElementName -> new ParagraphView(elem);
		case AbstractDocument.SectionElementName -> new BoxView(elem, View.Y_AXIS);
		case StyleConstants.ComponentElementName -> new ComponentView(elem);
		case StyleConstants.IconElementName -> new IconView(elem);
		default -> new LabelView(elem);
		};
	}

}

