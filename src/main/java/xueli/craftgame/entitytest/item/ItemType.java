package xueli.craftgame.entitytest.item;

public class ItemType {

	private String namespace;
	private String name;

	private ItemListener listener = ItemListener.EMPTY;
	private ItemRenderable renderable;

	protected int maxStackCount = 64;

	public ItemType(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}

	public ItemType setListener(ItemListener listener) {
		this.listener = listener;
		return this;
	}

	public ItemType setRenderable(ItemRenderable renderable) {
		this.renderable = renderable;
		return this;
	}

	public ItemRenderable getRenderable() {
		return renderable;
	}

	public ItemListener getListener() {
		return listener;
	}

	public String getName() {
		return name;
	}

	public String getNamespace() {
		return namespace;
	}

	public int getMaxStackCount() {
		return maxStackCount;
	}

}
