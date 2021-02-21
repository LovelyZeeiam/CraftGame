package xueli.craftgame.block;

import java.io.Serializable;

import xueli.craftgame.block.model.IModel;
import xueli.gamengine.utils.vector.Vector2s;

public class BlockData implements Serializable {

	private static final long serialVersionUID = -5886039106238613900L;

	private String namespace;
	private String name;
	private IModel model;
	private BlockListener listener = new BlockListener();
	private Vector2s[] textures;

	public BlockData(String namespace, String name, IModel model, Vector2s[] textures) {
		this.namespace = namespace;
		this.name = name;
		this.model = model;
		this.textures = textures;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockData other = (BlockData) obj;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlockData [namespace=" + namespace + ", name=" + name + "]";
	}

}
