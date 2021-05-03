package xueli.craftgame.inventory;

import javax.swing.JFrame;

import xueli.craftgame.init.Blocks;

import java.awt.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;

public class InventoryWindow extends JFrame implements ItemListener {

	private static final long serialVersionUID = -8986192781829684039L;

	private Inventory inventory;
	private List list;

	public InventoryWindow(Blocks blocks, Inventory inventory) {
		this.inventory = inventory;

		setTitle("Creative Inventory");
		setBounds(0, 0, 400, 600);

		list = new List();
		blocks.get().forEach(base -> list.add(base.getNamespace()));
		getContentPane().add(list, BorderLayout.CENTER);

		list.addItemListener(this);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		this.inventory.setChosenIndex((int) e.getItem());

	}

}
