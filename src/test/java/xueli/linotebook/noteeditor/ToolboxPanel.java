package xueli.linotebook.noteeditor;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import javax.swing.AbstractListModel;

public class ToolboxPanel extends JDialog {

	private static final long serialVersionUID = 5702037638824766693L;
	
	private final JList<String> list;
	
	/**
	 * Create the panel.
	 */
	public ToolboxPanel() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setSize(300, 200);
		
		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel<>() {
			private static final long serialVersionUID = -8071522849941331520L;
			String[] values = new String[] {"0", "1", "2", "3", "4"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		getContentPane().add(list, BorderLayout.CENTER);
		
		
		
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if(b) {
			list.grabFocus();
			if(list.getModel().getSize() > 0) {
				list.setSelectedIndex(0);
			}
		}
	}
	
}
