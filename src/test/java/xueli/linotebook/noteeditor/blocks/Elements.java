package xueli.linotebook.noteeditor.blocks;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.BranchElement;
import javax.swing.text.AbstractDocument.LeafElement;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;

public class Elements {
	
	public static final String NAME_HEADING_1 = "#";
	public static final String NAME_HEADING_2 = "##";
	public static final String NAME_HEADING_3 = "###";
	public static final String NAME_HEADING_4 = "####";
	
	private Elements() {}
	
	public static BranchElement makeBranchElement(AbstractDocument doc, Element parent, String name, AttributeSet a) {
		BranchElement element = doc.new BranchElement(parent, a);
		element.addAttribute(AbstractDocument.ElementNameAttribute, name);
		return element;
	}
	
	public static LeafElement makeLeafElement(AbstractDocument doc, Element parent, String name, AttributeSet a, int offset0, int offset1) {
		LeafElement element = doc.new LeafElement(parent, a, offset0, offset1);
		element.addAttribute(AbstractDocument.ElementNameAttribute, name);
		return element;
	}
	
//	public static BranchElement makeBlock(AbstractDocument doc, Element parent, AttributeSet a) {
//		return makeBranchElement(doc, parent, NAME_BLOCK, a);
//	}
	
}
