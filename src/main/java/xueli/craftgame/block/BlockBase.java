package xueli.craftgame.block;

import java.util.ArrayList;
import java.util.Objects;

import xueli.craftgame.renderer.model.TexturedModel;
import xueli.craftgame.world.Dimension;
import xueli.game.module.Module;
import xueli.game.utils.FloatList;
import xueli.game.utils.Light;

public class BlockBase extends Module {

	// name in language define
	protected String nameInternational;

	protected TexturedModel model;
	protected boolean isAlpha = false;
	protected boolean isComplete = true;

	protected IBlockListener listener = IBlockListener.EMPTY;

	protected ArrayList<String> tags = new ArrayList<>();

	public BlockBase(String namespace) {
		super(namespace);

	}

	public BlockBase(String namespace, String nameInternational, TexturedModel model, boolean isAlpha,
			boolean isComplete, IBlockListener listener) {
		this(namespace);
		this.nameInternational = nameInternational;
		this.model = model;
		this.isAlpha = isAlpha;
		this.isComplete = isComplete;
		this.listener = listener;

	}

	public String getNameInternational() {
		return nameInternational;
	}

	public IBlockListener getListener() {
		return listener;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public boolean isAlpha() {
		return isAlpha;
	}

	public boolean isComplete() {
		return isComplete;
	}

	@Override
	public final boolean checkInvaild() {
		return Objects.nonNull(getNamespace()) && Objects.nonNull(nameInternational) && Objects.nonNull(listener);
	}

	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Dimension dimension) {
		Light light = dimension != null ? dimension.getLight(x, y, z) : Light.FULL_LIGHT;
		return model.getRenderData(x, y, z, face,light, buffer);
	}
	
	public int getRenderModelViewData(FloatList buffer) {
		int v = 0;
		for(byte f = 0; f < 6; f++) {
			v += getRenderCubeData(buffer, 0, 0, 0, f, null);
		}
		return v;
	}

	@Override
	public String toString() {
		return "BlockBase [namespace=" + getNamespace() + ", nameInternational=" + nameInternational + ", model="
				+ model + ", listener=" + listener + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAlpha ? 1231 : 1237);
		result = prime * result + (isComplete ? 1231 : 1237);
		result = prime * result + ((listener == null) ? 0 : listener.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((nameInternational == null) ? 0 : nameInternational.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
		BlockBase other = (BlockBase) obj;
		if (isAlpha != other.isAlpha)
			return false;
		if (isComplete != other.isComplete)
			return false;
		if (listener == null) {
			if (other.listener != null)
				return false;
		} else if (!listener.equals(other.listener))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (nameInternational == null) {
			if (other.nameInternational != null)
				return false;
		} else if (!nameInternational.equals(other.nameInternational))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}

}
