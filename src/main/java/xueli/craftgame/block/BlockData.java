package xueli.craftgame.block;

import xueli.craftgame.block.model.IModel;
import xueli.gamengine.utils.vector.Vector2s;

public class BlockData {

	private String namespace;
	private String name;
	private IModel model;
	private BlockListener listener = new BlockListener();
	private Vector2s[] textures;

	public BlockData(String namespace, String name, IModel model) {
		this.namespace = namespace;
		this.name = name;
		this.model = model;

	}

	public String getNamespace() {
		return namespace;
	}

	public String getName() {
		return name;
	}

	public IModel getModel() {
		return model;
	}

	public Vector2s[] getTextures() {
		return textures;
	}

	public BlockListener getListener() {
		return listener;
	}

	public void setListener(BlockListener listener) {
		this.listener = listener;
	}

}
