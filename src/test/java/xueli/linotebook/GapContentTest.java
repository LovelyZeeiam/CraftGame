package xueli.linotebook;

import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;

public class GapContentTest {
	
	public static void main(String[] args) throws BadLocationException {
		GapContent content = new GapContent(10);
		content.insertString(0, "23333");
		System.out.println(content);
		
	}

}
