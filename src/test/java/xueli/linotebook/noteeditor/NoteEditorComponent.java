package xueli.linotebook.noteeditor;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import xueli.swingx.layout.VFlowLayout;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import javax.swing.JEditorPane;
import javax.swing.border.EmptyBorder;

public class NoteEditorComponent extends JPanel {
	
	private static final long serialVersionUID = -2999695590126017406L;
	private JTextField titleInput;
	
	public NoteEditorComponent() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel titleContainer = new JPanel();
		add(titleContainer, BorderLayout.NORTH);
		titleContainer.setLayout(new BorderLayout(0, 0));
		
		titleInput = new JTextField();
		titleInput.setFont(titleInput.getFont().deriveFont(titleInput.getFont().getSize() + 10f));
		titleInput.setBorder(null);
		titleInput.setBackground(UIManager.getColor("control"));
		titleInput.setToolTipText("Title");
		titleContainer.add(titleInput);
		titleInput.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setBorder(new EmptyBorder(10, 0, 0, 0));
		textPane.setBackground(UIManager.getColor("control"));
		add(textPane, BorderLayout.CENTER);
		
		
	}
	
	
}
